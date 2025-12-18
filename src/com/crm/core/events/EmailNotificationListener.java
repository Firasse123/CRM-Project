package com.crm.core.events;

import com.crm.core.domain.Opportunity;
import com.crm.core.domain.OpportunityState;

public class EmailNotificationListener implements OpportunityEventListener {
    @Override
    public void onStateChanged(Opportunity opportunity, OpportunityState oldState, OpportunityState newState) {
        System.out.printf("[EMAIL] Opportunité '%s' (%s): État changé de %s à %s%n",
            opportunity.getTitle(), opportunity.getId(), oldState, newState);
        
        if (newState == OpportunityState.WON) {
            System.out.println("  → Email de félicitations envoyé au commercial");
        } else if (newState == OpportunityState.LOST) {
            System.out.println("  → Email de suivi envoyé au manager");
        }
    }

    @Override
    public void onAmountChanged(Opportunity opportunity, double oldAmount, double newAmount) {
        if (Math.abs(newAmount - oldAmount) > 10000) {
            System.out.printf("[EMAIL] Changement important pour '%s': %.2f → %.2f%n",
                opportunity.getTitle(), oldAmount, newAmount);
        }
    }
}
