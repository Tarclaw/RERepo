package web.example.realestate.services;

import web.example.realestate.domain.building.Basement;
import java.util.Set;

public interface BasementService {
    Basement getById(Long id);
    Set<Basement> getBasements();
}
