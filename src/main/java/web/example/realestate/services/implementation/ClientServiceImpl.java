package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.converters.ClientCommandToClient;
import web.example.realestate.converters.ClientToClientCommand;
import web.example.realestate.domain.building.*;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.exceptions.NotFoundException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);

    public ClientServiceImpl(ClientRepository clientRepository, RealEstateAgentRepository agentRepository,
                             ClientCommandToClient toClient, ClientToClientCommand toClientCommand,
                             FacilityService facilityService) {
        this.clientRepository = clientRepository;
        this.agentRepository = agentRepository;
        this.toClient = toClient;
        this.toClientCommand = toClientCommand;
        this.facilityService = facilityService;
        LOGGER.info("New instance of ClientServiceImpl created");
    }

    @Override
    public Client getById(final Long id) {
        LOGGER.trace("Enter and execute 'ClientServiceImpl.getById(final Long id)' method");
        return clientRepository.findClientByIdWithFacilitiesAndAgents(id)
                         .orElseThrow(
                                      () -> {
                                          LOGGER.warn("We don't have Client with id= " + id);
                                          return new NotFoundException("Please chose different Client.");
                                      }
                         );
    }

    @Override
    public Set<Client> getClients() {
        LOGGER.trace("Enter in 'ClientServiceImpl.getClients()' method");

        Set<Client> clients = new HashSet<>();
        clientRepository.findAll().iterator().forEachRemaining(clients :: add);
        LOGGER.debug("Find all Clients from DB and add them to HashSet");

        LOGGER.trace("'ClientServiceImpl.getClients()' executed successfully.");
        return clients;
    }

    @Override
    @Transactional
    public ClientCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'ClientServiceImpl.findCommandById(final Long id)' method");
        return toClientCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public ClientCommand saveDetached(final ClientCommand clientCommand, final FacilityCommand facilityCommand) {
        LOGGER.trace("Enter in 'ClientServiceImpl.saveDetached(final ClientCommand clientCommand, final FacilityCommand facilityCommand)' method");

        Facility facility = facilityService.saveRawFacility(facilityCommand);
        RealEstateAgent agent = agentRepository.findById(clientCommand.getAgentId()).orElseThrow(() -> {
            LOGGER.warn("We don't have Agent with id= " + clientCommand.getAgentId());
            return new NotFoundException("We don't have this Agent. Please choose another one.");
        });

        Client detachedClient = toClient.convert(clientCommand);
        detachedClient.addAgent(agent);
        detachedClient.addFacility(facility);

        Client savedClient = clientRepository.save(detachedClient);

        LOGGER.debug("We save Client with id= " + savedClient.getId());
        LOGGER.trace("'ClientServiceImpl.saveDetached(final ClientCommand clientCommand, final FacilityCommand facilityCommand)' executed successfully.");
        return toClientCommand.convert(savedClient);
    }

    @Override
    @Transactional
    public ClientCommand saveAttached(final ClientCommand command) {
        LOGGER.trace("Enter in 'ClientServiceImpl.saveAttached(final ClientCommand command)' method");

        Client detachedClient = toClient.convert(command);
        Client savedClient = clientRepository.save(detachedClient);

        LOGGER.debug("We save Client with id= " + savedClient.getId());
        LOGGER.trace("'ClientServiceImpl.saveAttached(final ClientCommand command)' executed successfully.");
        return toClientCommand.convert(savedClient);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        LOGGER.trace("Enter in 'ClientServiceImpl.deleteById(final Long id)' method");

        Client client = clientRepository.findClientByIdWithFacilitiesAndAgents(id).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + id);
            return new NotFoundException("We don't have this Client. Please choose another one.");
        });

        List<Facility> facilities = new ArrayList<>(client.getFacilities());
        facilities.forEach(client::removeFacility);
        LOGGER.debug("Remove all Facilities from Client to delete.");

        List<RealEstateAgent> agents = new ArrayList<>(client.getRealEstateAgents());
        agents.forEach(client::removeAgent);
        LOGGER.debug("Remove all Agents from Client to delete.");

        clientRepository.deleteById(id);
        LOGGER.debug("Delete Client with id= " + id);

        LOGGER.trace("'ClientServiceImpl.deleteById(final Long id)' executed successfully.");
    }
}
