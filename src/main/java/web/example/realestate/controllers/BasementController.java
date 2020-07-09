package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.BasementService;
import web.example.realestate.services.ClientService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class BasementController {

    private final BasementService basementService;
    private final ClientService clientService;

    public BasementController(BasementService basementService, ClientService clientService) {
        this.basementService = basementService;
        this.clientService = clientService;
    }

    @GetMapping("/basement/{id}/show")
    public String getBasementById(@PathVariable String id, Model model) {
        model.addAttribute("basement", basementService.getById(Long.valueOf(id)));
        return "basement/show";
    }

    @GetMapping("/basement")
    public String getAllBasements(Model model) {
        model.addAttribute("basements", basementService.getBasements());
        return "basements";
    }

    @GetMapping("/basement/new")
    public String newBasement(Model model) {
        model.addAttribute("basement", new FacilityCommand());
        model.addAttribute("address", new AddressCommand());
        model.addAttribute("clients", clientService.getClients());
        return "basement/basementEmptyForm";
    }

    @GetMapping("/basement/{id}/update")
    public String updateBasement(@PathVariable String id, Model model) {
        FacilityCommand basement = basementService.findCommandById(Long.valueOf(id));
        model.addAttribute("basement", basement);
        model.addAttribute("address", basement.getAddress());
        model.addAttribute("clients", clientService.getClients());
        return "basement/basementForm";
    }

    @PostMapping("/basement/save")
    public String saveNew(@Valid @ModelAttribute("basement") FacilityCommand basementCommand, BindingResult basementBinding,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding, Model model) {
        if (basementBinding.hasErrors() || addressBinding.hasErrors()) {

            basementBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("basement", basementCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "basement/basementEmptyForm";
        }

        basementCommand.setAddress(addressCommand);
        FacilityCommand savedBasement = basementService.saveDetached(basementCommand);
        return "redirect:/basement/" + savedBasement.getId() + "/show";
    }

    @PostMapping("/basement/update")
    public String updateExisting(@Valid @ModelAttribute("basement") FacilityCommand basementCommand, BindingResult basementBinding,
                                 @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding, Model model) {
        if (basementBinding.hasErrors() || addressBinding.hasErrors()) {

            basementBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("basement", basementCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "basement/basementForm";
        }

        basementCommand.setAddress(addressCommand);
        FacilityCommand savedBasement = basementService.saveAttached(basementCommand);
        return "redirect:/basement/" + savedBasement.getId() + "/show";
    }

    @GetMapping("/basement/{id}/delete")
    public String deleteById(@PathVariable String id) {
        basementService.deleteById(Long.valueOf(id));
        return "redirect:/basement";
    }

    @GetMapping("/basement/{id}/image")
    public String basementImageUpload(@PathVariable String id, Model model) {
        model.addAttribute("basement", basementService.getById(Long.valueOf(id)));
        return "basement/basementImageUpload";
    }

    @GetMapping("/basement/{id}/basementimage")
    public void renderBasementImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        FacilityCommand basement = basementService.findCommandById(Long.valueOf(id));

        if (basement.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(basement.getImage());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/basement/{id}/image")
    public String saveBasementImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile multipartFile) {
        basementService.saveImage(Long.valueOf(id), multipartFile);
        return "redirect:/basement/" + id + "/show";
    }
}
