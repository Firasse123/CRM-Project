package com.crm.core.repository;

import com.crm.core.domain.Opportunity;
import java.util.*;

public interface OpportunityRepository {
    void save(Opportunity opportunity);
    Optional<Opportunity> findById(String id);
    List<Opportunity> findAll();
    void delete(String id);
}
