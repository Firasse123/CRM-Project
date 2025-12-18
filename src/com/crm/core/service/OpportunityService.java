package com.crm.core.service;

import com.crm.core.domain.Opportunity;
import com.crm.core.domain.OpportunityState;
import com.crm.core.events.OpportunityEventListener;
import com.crm.core.repository.OpportunityRepository;
import com.crm.core.workflow.WorkflowStrategy;
import java.util.*;

public class OpportunityService {
    private final OpportunityRepository repository;
    private final WorkflowStrategy workflowStrategy;
    private final List<OpportunityEventListener> listeners;

    public OpportunityService(OpportunityRepository repository, WorkflowStrategy workflowStrategy) {
        this.repository = repository;
        this.workflowStrategy = workflowStrategy;
        this.listeners = new ArrayList<>();
    }

    public void addListener(OpportunityEventListener listener) {
        listeners.add(listener);
    }

    public Opportunity createOpportunity(String id, String title, String clientName, double amount) {
        Opportunity opportunity = new Opportunity(id, title, clientName, amount);
        repository.save(opportunity);
        System.out.println("\n=== Opportunité créée ===");
        System.out.println(opportunity);
        System.out.println("Workflow: " + workflowStrategy.getWorkflowName());
        return opportunity;
    }

    public boolean executeAction(String opportunityId, String action) {
        return repository.findById(opportunityId).map(opportunity -> {
            OpportunityState currentState = opportunity.getState();
            OpportunityState nextState = workflowStrategy.getNextState(currentState, action);
            
            if (nextState != currentState && workflowStrategy.canTransition(currentState, nextState)) {
                System.out.println("\n=== Exécution action: " + action + " ===");
                OpportunityState oldState = currentState;
                opportunity.setState(nextState);
                repository.save(opportunity);
                notifyStateChanged(opportunity, oldState, nextState);
                return true;
            }
            System.out.println("Action '" + action + "' non valide pour l'état " + currentState);
            return false;
        }).orElse(false);
    }

    public boolean updateAmount(String opportunityId, double newAmount) {
        return repository.findById(opportunityId).map(opportunity -> {
            double oldAmount = opportunity.getAmount();
            opportunity.updateAmount(newAmount);
            repository.save(opportunity);
            notifyAmountChanged(opportunity, oldAmount, newAmount);
            return true;
        }).orElse(false);
    }

    private void notifyStateChanged(Opportunity opp, OpportunityState oldState, OpportunityState newState) {
        for (OpportunityEventListener listener : listeners) {
            listener.onStateChanged(opp, oldState, newState);
        }
    }

    private void notifyAmountChanged(Opportunity opp, double oldAmount, double newAmount) {
        for (OpportunityEventListener listener : listeners) {
            listener.onAmountChanged(opp, oldAmount, newAmount);
        }
    }
}
