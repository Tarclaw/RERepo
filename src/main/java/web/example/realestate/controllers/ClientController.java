package web.example.realestate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.ClientCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.commands.MappingCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.RealEstateAgentService;

import javax.validation.Valid;

@Controller
public class ClientController {

    private final ClientService clientService;
    private final RealEstateAgentService agentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    public ClientController(ClientService clientService, RealEstateAgentService agentService) {
        this.clientService = clientService;
        this.agentService = agentService;
        LOGGER.info("New instance of ClientController created.");
    }

    @GetMapping("/client/{id}/show")
    public String getClientById(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'getClientById(@PathVariable String id, Model model)' method");

        model.addAttribute("client", clientService.getById(Long.valueOf(id)));
        LOGGER.debug("add client attribute to model");

        LOGGER.trace("'getClientById(@PathVariable String id, Model model)' executed successfully.");
        return "client/show";
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        LOGGER.trace("Enter in 'getAllClients(Model model)' method");

        model.addAttribute("clients", clientService.getClients());
        LOGGER.debug("add clients attribute to model");

        LOGGER.trace("'getAllClients(Model model)' executed successfully.");
        return "client";
    }

    @GetMapping("/client/new")
    public String newClient(Model model) {
        LOGGER.trace("Enter in 'newClient(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("facility", new FacilityCommand());
        LOGGER.debug("add facility attribute to model");

        model.addAttribute("address", new AddressCommand());
        LOGGER.debug("add address attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClient(Model model)' executed successfully.");
        return "client/clientEmptyForm";
    }

    @GetMapping("/client/{id}/update")
    public String updateClient(@PathVariable String id, Model model) {
        LOGGER.trace("Enter in 'updateClient(@PathVariable String id, Model model)' method");

        model.addAttribute("client", clientService.findCommandById(Long.valueOf(id)));
        LOGGER.debug("add client attribute to model");

        LOGGER.trace("'updateClient(@PathVariable String id, Model model)' executed successfully.");
        return "client/clientForm";
    }

    @PostMapping("/client/save")
    public String saveNew(@Valid @ModelAttribute("client") ClientCommand clientCommand, BindingResult clientBindingResult,
                          @Valid @ModelAttribute("facility") FacilityCommand facilityCommand, BindingResult facilityBindingResult,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBindingResult, Model model) {
        LOGGER.trace("Enter in 'saveNew(clientCommand, clientBindingResult, facilityCommand, facilityBindingResult, " +
                     "addressCommand, addressBindingResult, Model model)' method");

        if (clientBindingResult.hasErrors() || facilityBindingResult.hasErrors() || addressBindingResult.hasErrors()) {
            clientBindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            facilityBindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));
            addressBindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("client", clientCommand);
            LOGGER.debug("add client attribute to model");

            model.addAttribute("facility", facilityCommand);
            LOGGER.debug("add facility attribute to model");

            model.addAttribute("address", addressCommand);
            LOGGER.debug("add address attribute to model");

            model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
            LOGGER.debug("add realEstateAgents attribute to model");

            return "client/clientEmptyForm";
        }

        clientService.saveDetached(clientCommand, facilityCommand);

        LOGGER.trace("'saveNew(clientCommand, clientBindingResult, facilityCommand, facilityBindingResult, " +
                     "addressCommand, addressBindingResult, Model model)' executed successfully.");
        return "redirect:/clients";
    }

    @PostMapping("/client/update")
    public String updateExisting(@Valid @ModelAttribute("client") ClientCommand clientCommand,
                                 BindingResult bindingResult, Model model) {
        LOGGER.trace("Enter in 'updateExisting(ClientCommand clientCommand, BindingResult bindingResult, Model model)' method");

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            ClientCommand clientWithAgentsAndFacilities = clientService.findCommandById(clientCommand.getId());
            clientCommand.setRealEstateAgentCommands(clientWithAgentsAndFacilities.getRealEstateAgentCommands());
            clientCommand.setFacilityCommands(clientWithAgentsAndFacilities.getFacilityCommands());

            model.addAttribute("client", clientCommand);
            LOGGER.debug("add client attribute to model");

            return "client/clientForm";
        }

        ClientCommand savedCommand = clientService.saveAttached(clientCommand);

        LOGGER.trace("'updateExisting(ClientCommand clientCommand, BindingResult bindingResult, Model model)' executed successfully.");
        return "redirect:/client/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/client/{id}/delete")
    public String deleteById(@PathVariable String id) {
        LOGGER.trace("Enter in 'deleteById(@PathVariable String id)' method");

        clientService.deleteById(Long.valueOf(id));

        LOGGER.trace("'deleteById(@PathVariable String id)' executed successfully.");
        return "redirect:/clients";
    }

    @GetMapping("/apartment/client/new")
    public String newClientForApartment(Model model) {
        LOGGER.trace("Enter in 'newClientForApartment(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("mapping", new MappingCommand("apartment"));
        LOGGER.debug("add mapping attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClientForApartment(Model model)' executed successfully.");
        return "client/clientForFacilityForm";
    }

    @GetMapping("/basement/client/new")
    public String newClientForBasement(Model model) {
        LOGGER.trace("Enter in 'newClientForBasement(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("mapping", new MappingCommand("basement"));
        LOGGER.debug("add mapping attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClientForBasement(Model model)' executed successfully.");
        return "client/clientForFacilityForm";
    }

    @GetMapping("/garage/client/new")
    public String newClientForGarage(Model model) {
        LOGGER.trace("Enter in 'newClientForGarage(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("mapping", new MappingCommand("garage"));
        LOGGER.debug("add mapping attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClientForGarage(Model model)' executed successfully.");
        return "client/clientForFacilityForm";
    }

    @GetMapping("/house/client/new")
    public String newClientForHouse(Model model) {
        LOGGER.trace("Enter in 'newClientForHouse(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("mapping", new MappingCommand("house"));
        LOGGER.debug("add mapping attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClientForHouse(Model model)' executed successfully.");
        return "client/clientForFacilityForm";
    }

    @GetMapping("/storage/client/new")
    public String newClientForStorage(Model model) {
        LOGGER.trace("Enter in 'newClientForStorage(Model model)' method");

        model.addAttribute("client", new ClientCommand());
        LOGGER.debug("add client attribute to model");

        model.addAttribute("mapping", new MappingCommand("storage"));
        LOGGER.debug("add mapping attribute to model");

        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        LOGGER.debug("add realEstateAgents attribute to model");

        LOGGER.trace("'newClientForStorage(Model model)' executed successfully.");
        return "client/clientForFacilityForm";
    }

    @PostMapping("/client/facility/save")
    public String saveForFacility(@Valid @ModelAttribute("client")
                                  ClientCommand clientCommand, BindingResult bindingResult,
                                  @ModelAttribute MappingCommand mappingCommand, Model model) {
        LOGGER.trace("Enter in 'saveForFacility(clientCommand, bindingResult, mappingCommand, model)' method");

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> LOGGER.warn(objectError.toString()));

            model.addAttribute("client", clientCommand);
            LOGGER.debug("add client attribute to model");

            model.addAttribute("mapping", mappingCommand);
            LOGGER.debug("add mapping attribute to model");

            model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
            LOGGER.debug("add realEstateAgents attribute to model");

            return "client/clientForFacilityForm";
        }
        clientService.saveAttached(clientCommand);

        LOGGER.trace("'saveForFacility(clientCommand, bindingResult, mappingCommand, model)' executed successfully.");
        return "redirect:/" + mappingCommand.getPageName() + "/new";
    }
}
