package com.crm.core.workflow;

import com.crm.core.domain.OpportunityState;

public class StandardSalesWorkflow implements WorkflowStrategy {
    @Override
    public OpportunityState getNextState(OpportunityState currentState, String action) {
        switch (currentState) {
            case NEW:
                if ("qualify".equals(action)) return OpportunityState.QUALIFIED;
                if ("reject".equals(action)) return OpportunityState.LOST;
                break;
            case QUALIFIED:
                if ("send_proposal".equals(action)) return OpportunityState.PROPOSAL_SENT;
                if ("reject".equals(action)) return OpportunityState.LOST;
                break;
            case PROPOSAL_SENT:
                if ("negotiate".equals(action)) return OpportunityState.NEGOTIATION;
                if ("reject".equals(action)) return OpportunityState.LOST;
                break;
            case NEGOTIATION:
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
            case NEW: return to == OpportunityState.QUALIFIED || to == OpportunityState.LOST;
            case QUALIFIED: return to == OpportunityState.PROPOSAL_SENT || to == OpportunityState.LOST;
            case PROPOSAL_SENT: return to == OpportunityState.NEGOTIATION || to == OpportunityState.LOST;
            case NEGOTIATION: return to == OpportunityState.WON || to == OpportunityState.LOST;
            case WON:
            case LOST: return to == OpportunityState.CLOSED;
            default: return false;
        }
    }

    @Override
    public String getWorkflowName() { return "Standard Sales Workflow"; }
}
