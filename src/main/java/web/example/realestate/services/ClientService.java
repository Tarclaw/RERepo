package web.example.realestate.services;

import web.example.realestate.domain.people.Client;

import java.util.Set;

public interface ClientService {
    Client getById(Long id);
    Set<Client> getClients();
}
