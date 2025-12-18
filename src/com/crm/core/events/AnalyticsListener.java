package com.crm.core.events;

import com.crm.core.domain.Opportunity;
import com.crm.core.domain.OpportunityState;

public class AnalyticsListener implements OpportunityEventListener {
    private int transitionCount = 0;
    private double totalValueChange = 0;
    
    @Override
    public void onStateChanged(Opportunity opportunity, OpportunityState oldState, OpportunityState newState) {
        transitionCount++;
        System.out.println("[ANALYTICS] Total transitions: " + transitionCount);
        if (newState == OpportunityState.WON) {
            System.out.println("[ANALYTICS] Opportunité gagnée! Valeur: " + opportunity.getAmount());
        }
    }

    @Override
    public void onAmountChanged(Opportunity opportunity, double oldAmount, double newAmount) {
        totalValueChange += Math.abs(newAmount - oldAmount);
        System.out.println("[ANALYTICS] Changements cumulés: " + totalValueChange);
    }
    
    public int getTransitionCount() { return transitionCount; }
    public double getTotalValueChange() { return totalValueChange; }
}
