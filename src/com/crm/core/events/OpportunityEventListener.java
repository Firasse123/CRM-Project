package com.crm.core.events;

import com.crm.core.domain.Opportunity;
import com.crm.core.domain.OpportunityState;

public interface OpportunityEventListener {
    void onStateChanged(Opportunity opportunity, OpportunityState oldState, OpportunityState newState);
    void onAmountChanged(Opportunity opportunity, double oldAmount, double newAmount);
}
