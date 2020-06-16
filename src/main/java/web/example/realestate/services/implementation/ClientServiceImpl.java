package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.converters.ClientCommandToClient;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.domain.building.Facility;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.services.ClientService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientCommandToClient toClient;
    private final ClientToClientCommand toClientCommand;

    public ClientServiceImpl(ClientRepository repository,
                             ClientCommandToClient toClient,
                             ClientToClientCommand toClientCommand) {
        this.repository = repository;
        this.toClient = toClient;
        this.toClientCommand = toClientCommand;
    }

    @Override
    public Client getById(final Long id) {
        return repository.findClientByIdWithFacilitiesAndAgents(id)
                         .orElseThrow(
                                      () -> new RuntimeException("We don't have client with id=" + id)
                         );
    }

    @Override
    public Set<Client> getClients() {
        Set<Client> clients = new HashSet<>();
        repository.findAll().iterator().forEachRemaining(clients :: add);
        return clients;
    }

    @Override
    @Transactional
    public ClientCommand findCommandById(Long id) {
        return toClientCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public ClientCommand saveClientCommand(ClientCommand command) {
        Client detachedClient = toClient.convert(command);
        Client savedClient = repository.save(detachedClient);
        System.out.println("Save client with id="+savedClient.getId());
        return toClientCommand.convert(savedClient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Client client = repository.findClientByIdWithFacilitiesAndAgents(id).get();

        List<Facility> facilities = new ArrayList<>(client.getFacilities());
        facilities.forEach(facility -> client.removeFacility(facility));

        List<RealEstateAgent> agents = new ArrayList<>(client.getRealEstateAgents());
        agents.forEach(agent -> client.removeAgent(agent));

        repository.deleteById(id);
    }
}
