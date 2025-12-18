package com.crm.core.repository;

import com.crm.core.domain.Opportunity;
import java.util.*;

public class InMemoryOpportunityRepository implements OpportunityRepository {
    private final Map<String, Opportunity> storage = new HashMap<>();

    @Override
    public void save(Opportunity opportunity) {
        storage.put(opportunity.getId(), opportunity);
        System.out.println("[REPOSITORY] Opportunité sauvegardée: " + opportunity.getId());
    }

    @Override
    public Optional<Opportunity> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Opportunity> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void delete(String id) {
        storage.remove(id);
        System.out.println("[REPOSITORY] Opportunité supprimée: " + id);
    }
}
