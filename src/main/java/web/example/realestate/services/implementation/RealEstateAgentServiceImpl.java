package web.example.realestate.services.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import web.example.realestate.commands.RealEstateAgentCommand;
import web.example.realestate.converters.RealEstateAgentCommandToRealEstateAgent;
import web.example.realestate.converters.RealEstateAgentToRealEstateAgentCommand;
import web.example.realestate.domain.people.Client;
import web.example.realestate.domain.people.RealEstateAgent;
import web.example.realestate.exceptions.NotFoundException;
import web.example.realestate.repositories.ClientRepository;
import web.example.realestate.repositories.RealEstateAgentRepository;
import web.example.realestate.services.RealEstateAgentService;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class RealEstateAgentServiceImpl implements RealEstateAgentService {

    private final RealEstateAgentRepository agentRepository;
    private final ClientRepository clientRepository;
    private final RealEstateAgentCommandToRealEstateAgent toAgent;
    private final RealEstateAgentToRealEstateAgentCommand toAgentCommand;

    private static final Logger LOGGER = LoggerFactory.getLogger(RealEstateAgentServiceImpl.class);

    public RealEstateAgentServiceImpl(RealEstateAgentRepository agentRepository, ClientRepository clientRepository,
                                      RealEstateAgentCommandToRealEstateAgent toAgent,
                                      RealEstateAgentToRealEstateAgentCommand toAgentCommand) {
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.toAgent = toAgent;
        this.toAgentCommand = toAgentCommand;
        LOGGER.info("New instance of RealEstateAgentServiceImpl created");
    }

    @Override
    public RealEstateAgent getById(final Long id) {
        LOGGER.trace("Enter and execute 'RealEstateAgentServiceImpl.getById(final Long id)' method");
        return agentRepository.findRealEstateAgentsByIdWithEntities(id)
                         .orElseThrow(
                                      () -> {
                                          LOGGER.warn("We don't have Agent with id= " + id);
                                          return new NotFoundException("Please chose different Agent");
                                      }
                         );
    }

    @Override
    public Set<RealEstateAgent> getRealEstateAgents() {
        LOGGER.trace("Enter in 'RealEstateAgentServiceImpl.getRealEstateAgents()' method");

        Set<RealEstateAgent> agents = new HashSet<>();
        agentRepository.findAll().iterator().forEachRemaining(agents :: add);
        LOGGER.debug("Find all Agents from DB and add them to HashSet");

        LOGGER.trace("'RealEstateAgentServiceImpl.getRealEstateAgents()' executed successfully.");
        return agents;
    }

    @Override
    @Transactional
    public RealEstateAgentCommand findCommandById(final Long id) {
        LOGGER.trace("Enter and execute 'RealEstateAgentServiceImpl.findCommandById(final Long id)' method");
        return toAgentCommand.convert(getById(id));
    }

    @Override
    @Transactional
    public RealEstateAgentCommand saveRealEstateAgentCommand(RealEstateAgentCommand command) {
        LOGGER.trace("Enter in 'RealEstateAgentServiceImpl.saveRealEstateAgentCommand(RealEstateAgentCommand command)' method");

        Set<Client> clients = new HashSet<>();
        command.getClientIds().forEach(id -> clients.add(clientRepository.findById(id).orElseThrow(() -> {
            LOGGER.warn("We don't have Client with id= " + id);
            return new NotFoundException("We don't have this Client. Please choose another one.");
        })));

        RealEstateAgent detachedAgent = toAgent.convert(command);
        detachedAgent.setClients(clients);

        RealEstateAgent savedAgent = agentRepository.save(detachedAgent);

        LOGGER.debug("We save Agent with id= " + savedAgent.getId());
        LOGGER.trace("'RealEstateAgentServiceImpl.saveRealEstateAgentCommand(RealEstateAgentCommand command)' executed successfully.");
        return toAgentCommand.convert(savedAgent);
    }

    @Override
    public void deleteById(final Long id) {
        LOGGER.trace("Enter and execute 'RealEstateAgentServiceImpl.deleteById(final Long id)' method");

        agentRepository.deleteById(id);
        LOGGER.debug("Delete Agent with id= " + id);

        LOGGER.trace("'RealEstateAgentServiceImpl.deleteById(final Long id)' executed successfully.");
    }
}
