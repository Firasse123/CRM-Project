package com.crm.core.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Opportunity {
    private String id;
    private String title;
    private String clientName;
    private double amount;
    private OpportunityState state;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> history;

    public Opportunity(String id, String title, String clientName, double amount) {
        this.id = id;
        this.title = title;
        this.clientName = clientName;
        this.amount = amount;
        this.state = OpportunityState.NEW;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.history = new ArrayList<>();
        addToHistory("Opportunité créée");
    }

    public void setState(OpportunityState newState) {
        OpportunityState oldState = this.state;
        this.state = newState;
        this.updatedAt = LocalDateTime.now();
        addToHistory("État changé: " + oldState + " -> " + newState);
    }

    public void updateAmount(double newAmount) {
        this.amount = newAmount;
        this.updatedAt = LocalDateTime.now();
        addToHistory("Montant mis à jour: " + newAmount);
    }

    private void addToHistory(String event) {
        history.add(LocalDateTime.now() + " - " + event);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getClientName() { return clientName; }
    public double getAmount() { return amount; }
    public OpportunityState getState() { return state; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<String> getHistory() { return new ArrayList<>(history); }

    @Override
    public String toString() {
        return String.format("Opportunity[id=%s, title=%s, client=%s, amount=%.2f, state=%s]",
                id, title, clientName, amount, state);
    }
}
