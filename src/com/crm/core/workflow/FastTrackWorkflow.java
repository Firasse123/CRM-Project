package com.crm.core.workflow;

import com.crm.core.domain.OpportunityState;

public class FastTrackWorkflow implements WorkflowStrategy {
    @Override
    public OpportunityState getNextState(OpportunityState currentState, String action) {
        switch (currentState) {
            case NEW:
                if ("qualify".equals(action)) return OpportunityState.QUALIFIED;
                if ("quick_win".equals(action)) return OpportunityState.WON;
                if ("reject".equals(action)) return OpportunityState.LOST;
                break;
            case QUALIFIED:
                if ("win".equals(action)) return OpportunityState.WON;
                if ("lose".equals(action)) return OpportunityState.LOST;
                break;
            case WON:
            case LOST:
                if ("close".equals(action)) return OpportunityState.CLOSED;
                break;
        }
        return currentState;
    }

    @Override
    public boolean canTransition(OpportunityState from, OpportunityState to) {
        switch (from) {
            case NEW: return to == OpportunityState.QUALIFIED || to == OpportunityState.WON || to == OpportunityState.LOST;
            case QUALIFIED: return to == OpportunityState.WON || to == OpportunityState.LOST;
            case WON:
            case LOST: return to == OpportunityState.CLOSED;
            default: return false;
        }
    }

    @Override
    public String getWorkflowName() { return "Fast Track Workflow"; }
}
