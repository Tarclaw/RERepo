package web.example.realestate.services.implementation;

import org.springframework.stereotype.Service;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ClientCommandToClient;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.domain.building.*;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.repositories.*;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.FacilityService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final RealEstateAgentRepository agentRepository;

    private final ClientCommandToClient toClient;
    private final ClientToClientCommand toClientCommand;

    private final FacilityService facilityService;

    public ClientServiceImpl(ClientRepository clientRepository, RealEstateAgentRepository agentRepository,
                             ClientCommandToClient toClient, ClientToClientCommand toClientCommand,
                             FacilityService facilityService) {
        this.clientRepository = clientRepository;
        this.agentRepository = agentRepository;
        this.toClient = toClient;
        this.toClientCommand = toClientCommand;
        this.facilityService = facilityService;
    }

    @Override
    public Client getById(final Long id) {
        return clientRepository.findClientByIdWithFacilitiesAndAgents(id)
                         .orElseThrow(
                                      () -> new RuntimeException("We don't have client with id=" + id)
                         );
    }

    @Override
    public Set<Client> getClients() {
        Set<Client> clients = new HashSet<>();
        clientRepository.findAll().iterator().forEachRemaining(clients :: add);
        return clients;
    }

    @Override
    @Transactional
    public ClientCommand findCommandById(Long id) {
        return toClientCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public ClientCommand saveAttached(ClientCommand command) {
        Client detachedClient = toClient.convert(command);
        Client savedClient = clientRepository.save(detachedClient);
        System.out.println("Save client with id=" + savedClient.getId());
        return toClientCommand.convert(savedClient);
    }

    @Override
    @Transactional
    public ClientCommand saveDetached(final ClientCommand clientCommand, final FacilityCommand facilityCommand) {
        Facility facility = facilityService.saveRawFacility(facilityCommand);
        RealEstateAgent agent = agentRepository.findById(clientCommand.getAgentId()).get();

        Client detachedClient = toClient.convert(clientCommand);
        detachedClient.addAgent(agent);
        detachedClient.addFacility(facility);

        Client savedClient = clientRepository.save(detachedClient);

        System.out.println("Save client with id="+savedClient.getId());
        return toClientCommand.convert(savedClient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Client client = clientRepository.findClientByIdWithFacilitiesAndAgents(id).get();

        List<Facility> facilities = new ArrayList<>(client.getFacilities());
        facilities.forEach(client::removeFacility);

        List<RealEstateAgent> agents = new ArrayList<>(client.getRealEstateAgents());
        agents.forEach(client::removeAgent);

        clientRepository.deleteById(id);
    }
}
