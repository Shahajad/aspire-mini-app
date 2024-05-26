package com.aspire.demo.service;

import com.aspire.demo.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class LoanService {
    private final Map<String, List<LoanRequest>> userLoanStore = new HashMap<>();
    private final Map<Long, Repayment> repaymentStore = new HashMap<>();
    private final AtomicLong loanIdCounter = new AtomicLong();
    private final AtomicLong repaymentIdCounter = new AtomicLong();

    public void approveLoan(Long loanId, User user) {
        if(!user.getRoles().contains(Role.ADMIN)){
            throw new RuntimeException("ADMIN ONLY ACCESS. Please Login as ADMIN User");

        }
        for(List<LoanRequest> loanRequests: userLoanStore.values()){
            for(LoanRequest loanRequest : loanRequests){
                if (loanRequest != null && loanRequest.getId().equals(loanId)) {
                    loanRequest.setStatus(Status.APPROVED);
                    return;
                }
            }
        }
        throw new RuntimeException("loan not found with id "+ loanId);

    }

    public LoanRequest getLoan(User user, Long loanId) {
        if(user.getRoles().contains(Role.ADMIN)){
            for(List<LoanRequest> loanRequests: userLoanStore.values()){
                for(LoanRequest loanRequest : loanRequests){
                    if(loanRequest.getId().equals(loanId))
                        return loanRequest;
                }
            }
            return null;
        }
        String userId= user.getId();
        if(userLoanStore.containsKey(userId)){
            for(LoanRequest loanRequest : userLoanStore.get(userId)){
                if(loanRequest.getId().equals(loanId))
                    return loanRequest;
            }
        }
        return null;
    }
    public LoanRequest createRepayment(LoanRequest loanRequest){
        double amount = loanRequest.getAmount();
        int term = loanRequest.getTerm();
        List<Repayment> repayments = new ArrayList<>();
        double weeklyAmount = Math.round((amount / term) * 100.0) / 100.0;

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < term; i++) {
            Long repaymentId = repaymentIdCounter.incrementAndGet();
            cal.add(Calendar.WEEK_OF_YEAR, 1);

            Repayment repayment = new Repayment();
            repayment.setId(repaymentId);
            repayment.setAmount(weeklyAmount);
            repayment.setStatus("PENDING");
            repayment.setDueDate(cal.getTime());
            repayment.setLoanId(loanRequest.getId());

            repaymentStore.put(repaymentId, repayment);
            repayments.add(repayment);
        }
        String userId = loanRequest.getUser().getId();
        loanRequest.setRepayments(repayments);
        if(!userLoanStore.containsKey(loanRequest.getUser().getId())){
            userLoanStore.put(userId, new ArrayList<>());
        }
        userLoanStore.get(userId).add(loanRequest);
        return loanRequest;
    }

    public void addRepayment(LoanRequest request, User user) {
        String userId = user.getUsername();
        Long loanId = request.getId();
        if(!userLoanStore.containsKey(userId)){
            throw new RuntimeException("loan not found");
        }
        LoanRequest loanRequest = getLoanRequest(userLoanStore.get(userId), loanId);

        if(loanRequest == null){
            throw new RuntimeException("loan not found");
        }
        double amount = request.getAmount();

        List<Repayment> repayments = loanRequest.getRepayments();
        for (Repayment repayment : repayments) {
            if (repayment.getStatus().equals("PENDING")) {
                if (amount >= repayment.getAmount()) {
                    repayment.setStatus("PAID");
                    amount -= repayment.getAmount();
                } else {
                    break;
                }
            }
        }

        boolean allPaid = repayments.stream().allMatch(r -> r.getStatus().equals("PAID"));
        if (allPaid) {
            loanRequest.setStatus(Status.PAID);
        }
    }

    public Long getNextLoanId(){
        return loanIdCounter.incrementAndGet();
    }
    private LoanRequest getLoanRequest(List<LoanRequest> loanRequests, Long loanId){
        for(LoanRequest loanRequest: loanRequests){
            if(loanRequest.getId().equals(loanId)){
                return loanRequest;
            }
        }
        return null;
    }
}
