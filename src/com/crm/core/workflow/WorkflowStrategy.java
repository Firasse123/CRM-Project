package com.crm.core.workflow;

import com.crm.core.domain.OpportunityState;

public interface WorkflowStrategy {
    OpportunityState getNextState(OpportunityState currentState, String action);
    boolean canTransition(OpportunityState from, OpportunityState to);
    String getWorkflowName();
}
