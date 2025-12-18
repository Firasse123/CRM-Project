package com.crm;

import com.crm.core.domain.Opportunity;
import com.crm.core.events.*;
import com.crm.core.repository.*;
import com.crm.core.service.OpportunityService;
import com.crm.core.workflow.*;

public class CRMPrototypeDemo {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  PROTOTYPE CRM - DÉMONSTRATION PATTERNS ET PRINCIPES     ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        System.out.println("\n▶ DÉMO 1: WORKFLOW STANDARD");
        System.out.println("═══════════════════════════════════");
        demo1_StandardWorkflow();
        
        System.out.println("\n\n▶ DÉMO 2: WORKFLOW FAST TRACK");
        System.out.println("═══════════════════════════════════");
        demo2_FastTrackWorkflow();
        
        System.out.println("\n\n▶ DÉMO 3: OBSERVERS MULTIPLES");
        System.out.println("═══════════════════════════════════");
        demo3_MultipleObservers();
    }
    
    private static void demo1_StandardWorkflow() {
        OpportunityService service = new OpportunityService(
            new InMemoryOpportunityRepository(),
            new StandardSalesWorkflow()
        );
        service.addListener(new EmailNotificationListener());
        service.addListener(new AuditLogListener());
        
        Opportunity opp = service.createOpportunity("OPP-001", "Migration Cloud", "TechCorp", 50000);
        service.executeAction(opp.getId(), "qualify");
        service.executeAction(opp.getId(), "send_proposal");
        service.executeAction(opp.getId(), "negotiate");
        service.updateAmount(opp.getId(), 65000);
        service.executeAction(opp.getId(), "win");
        service.executeAction(opp.getId(), "close");
        
        System.out.println("\n--- Historique ---");
        opp.getHistory().forEach(System.out::println);
    }
    
    private static void demo2_FastTrackWorkflow() {
        OpportunityService service = new OpportunityService(
            new InMemoryOpportunityRepository(),
            new FastTrackWorkflow()
        );
        service.addListener(new EmailNotificationListener());
        
        Opportunity opp = service.createOpportunity("OPP-002", "Renouvellement", "ClientFidèle", 15000);
        service.executeAction(opp.getId(), "quick_win");
        service.executeAction(opp.getId(), "close");
    }
    
    private static void demo3_MultipleObservers() {
        OpportunityService service = new OpportunityService(
            new InMemoryOpportunityRepository(),
            new StandardSalesWorkflow()
        );
        service.addListener(new EmailNotificationListener());
        service.addListener(new AuditLogListener());
        AnalyticsListener analytics = new AnalyticsListener();
        service.addListener(analytics);
        
        Opportunity opp = service.createOpportunity("OPP-003", "Consultation", "InnovStart", 30000);
        service.executeAction(opp.getId(), "qualify");
        service.updateAmount(opp.getId(), 45000);
        service.executeAction(opp.getId(), "send_proposal");
        service.executeAction(opp.getId(), "negotiate");
        service.executeAction(opp.getId(), "lose");
        
        System.out.println("\n--- Stats Analytics ---");
        System.out.println("Transitions: " + analytics.getTransitionCount());
        System.out.println("Changements valeur: " + analytics.getTotalValueChange());
    }
}
