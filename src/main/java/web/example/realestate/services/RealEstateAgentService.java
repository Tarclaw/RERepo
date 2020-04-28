package web.example.realestate.services;

import web.example.realestate.domain.people.RealEstateAgent;

import java.util.Set;

public interface RealEstateAgentService {
    RealEstateAgent getById(Long id);
    Set<RealEstateAgent> getRealEstateAgents();
}
