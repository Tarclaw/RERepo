package web.example.realestate.services;

import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.domain.people.Client;

import java.util.Set;

public interface ClientService {

    Client getById(Long id);

    Set<Client> getClients();

    ClientCommand findCommandById(Long id);

    ClientCommand saveAttached(ClientCommand command);

    ClientCommand saveDetached(ClientCommand clientCommand, FacilityCommand facilityCommand);

    void deleteById(Long id);

}
