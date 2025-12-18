package com.crm.core.events;

import com.crm.core.domain.Opportunity;
import com.crm.core.domain.OpportunityState;

public class AuditLogListener implements OpportunityEventListener {
    @Override
    public void onStateChanged(Opportunity opportunity, OpportunityState oldState, OpportunityState newState) {
        System.out.printf("[AUDIT] %s | Opp: %s | %s → %s | Montant: %.2f%n",
            java.time.LocalDateTime.now(), opportunity.getId(), oldState, newState, opportunity.getAmount());
    }

    @Override
    public void onAmountChanged(Opportunity opportunity, double oldAmount, double newAmount) {
        System.out.printf("[AUDIT] %s | Opp: %s | Montant: %.2f → %.2f%n",
            java.time.LocalDateTime.now(), opportunity.getId(), oldAmount, newAmount);
    }
}
