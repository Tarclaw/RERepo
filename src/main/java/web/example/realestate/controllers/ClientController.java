package web.example.realestate.controllers;

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

    public ClientController(ClientService clientService, RealEstateAgentService agentService) {
        this.clientService = clientService;
        this.agentService = agentService;
    }

    @GetMapping("/client/{id}/show")
    public String getClientById(@PathVariable String id, Model model) {
        model.addAttribute("client", clientService.getById(Long.valueOf(id)));
        return "client/show";
    }

    @GetMapping("/clients")
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getClients());
        return "client";
    }

    @GetMapping("/client/new")
    public String newClient(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("facility", new FacilityCommand());
        model.addAttribute("address", new AddressCommand());
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientEmptyForm";
    }

    @GetMapping("/client/{id}/update")
    public String updateClient(@PathVariable String id, Model model) {
        model.addAttribute("client", clientService.findCommandById(Long.valueOf(id)));
        return "client/clientForm";
    }

    @PostMapping("/client/save")
    public String saveNew(@Valid @ModelAttribute("client") ClientCommand clientCommand, BindingResult clientBindingResult,
                          @Valid @ModelAttribute("facility") FacilityCommand facilityCommand, BindingResult facilityBindingResult,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBindingResult, Model model) {
        //todo junit
        if (clientBindingResult.hasErrors() || facilityBindingResult.hasErrors() || addressBindingResult.hasErrors()) {
            clientBindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            facilityBindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("client", clientCommand);
            model.addAttribute("facility", facilityCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());

            return "client/clientEmptyForm";
        }

        clientService.saveDetached(clientCommand, facilityCommand);

        return "redirect:/clients";
    }

    @PostMapping("/client/update")
    public String updateExisting(@Valid @ModelAttribute("client") ClientCommand clientCommand, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            ClientCommand clientWithAgentsAndFacilities = clientService.findCommandById(clientCommand.getId());
            clientCommand.setRealEstateAgentCommands(clientWithAgentsAndFacilities.getRealEstateAgentCommands());
            clientCommand.setFacilityCommands(clientWithAgentsAndFacilities.getFacilityCommands());

            model.addAttribute("client", clientCommand);
            return "client/clientForm";
        }

        ClientCommand savedCommand = clientService.saveAttached(clientCommand);

        return "redirect:/client/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/client/{id}/delete")
    public String deleteById(@PathVariable String id) {
        clientService.deleteById(Long.valueOf(id));
        return "redirect:/clients";
    }

    @GetMapping("/apartment/client/new")
    public String newClientForApartment(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("apartment"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/basement/client/new")
    public String newClientForBasement(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("basement"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/garage/client/new")
    public String newClientForGarage(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("garage"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/house/client/new")
    public String newClientForHouse(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("house"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @GetMapping("/storage/client/new")
    public String newClientForStorage(Model model) {
        model.addAttribute("client", new ClientCommand());
        model.addAttribute("mapping", new MappingCommand("storage"));
        model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());
        return "client/clientForFacilityForm";
    }

    @PostMapping("/client/facility/save")
    public String saveForFacility(@Valid @ModelAttribute("client")
                                  ClientCommand clientCommand, BindingResult bindingResult,
                                  @ModelAttribute MappingCommand mappingCommand, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("client", clientCommand);
            model.addAttribute("mapping", mappingCommand);
            model.addAttribute("realEstateAgents", agentService.getRealEstateAgents());

            return "client/clientForFacilityForm";
        }
        clientService.saveAttached(clientCommand);
        return "redirect:/" + mappingCommand.getPageName() + "/new";
    }
}
