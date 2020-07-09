package web.example.realestate.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.example.realestate.commands.AddressCommand;
import web.example.realestate.commands.FacilityCommand;
import web.example.realestate.services.ClientService;
import web.example.realestate.services.HouseService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class HouseController {

    private final HouseService houseService;
    private final ClientService clientService;

    public HouseController(HouseService houseService, ClientService clientService) {
        this.houseService = houseService;
        this.clientService = clientService;
    }

    @GetMapping("/house/{id}/show")
    public String getHouseById(@PathVariable String id, Model model) {
        model.addAttribute("house", houseService.getById(Long.valueOf(id)));
        return "house/show";
    }

    @GetMapping("/house")
    public String getAllHouses(Model model) {
        model.addAttribute("houses", houseService.getHouses());
        return "houses";
    }

    @GetMapping("/house/new")
    public String newHouse(Model model) {
        model.addAttribute("house", new FacilityCommand());
        model.addAttribute("address", new AddressCommand());
        model.addAttribute("clients", clientService.getClients());
        return "house/houseEmptyForm";
    }

    @GetMapping("/house/{id}/update")
    public String updateHouse(@PathVariable String id, Model model) {
        FacilityCommand house = houseService.findCommandById(Long.valueOf(id));
        model.addAttribute("house", house);
        model.addAttribute("address", house.getAddress());
        model.addAttribute("clients", clientService.getClients());
        return "house/houseForm";
    }

    @PostMapping("/house/save")
    public String saveNew(@Valid @ModelAttribute("house") FacilityCommand houseCommand, BindingResult houseBinding,
                          @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                          Model model) {
        if (houseBinding.hasErrors() || addressBinding.hasErrors()) {

            houseBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("house", houseCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "house/houseEmptyForm";
        }

        houseCommand.setAddress(addressCommand);
        FacilityCommand savedHouse = houseService.saveDetached(houseCommand);

        return "redirect:/house/" + savedHouse.getId() + "/show";
    }

    @PostMapping("/house/update")
    public String updateExisting(@Valid @ModelAttribute("house") FacilityCommand houseCommand, BindingResult houseBinding,
                                 @Valid @ModelAttribute("address") AddressCommand addressCommand, BindingResult addressBinding,
                                 Model model) {
        if (houseBinding.hasErrors() || addressBinding.hasErrors()) {

            houseBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));
            addressBinding.getAllErrors().forEach(objectError -> System.out.println(objectError.toString()));

            model.addAttribute("house", houseCommand);
            model.addAttribute("address", addressCommand);
            model.addAttribute("clients", clientService.getClients());

            return "house/houseForm";
        }

        houseCommand.setAddress(addressCommand);
        FacilityCommand savedHouse = houseService.saveDetached(houseCommand);

        return "redirect:/house/" + savedHouse.getId() + "/show";
    }

    @GetMapping("/house/{id}/delete")
    public String deleteById(@PathVariable String id) {
        houseService.deleteById(Long.valueOf(id));
        return "redirect:/house";
    }

    @GetMapping("/house/{id}/image")
    public String houseImageUpload(@PathVariable String id, Model model) {
        model.addAttribute("house", houseService.getById(Long.valueOf(id)));
        return "house/houseImageUpload";
    }

    @GetMapping("/house/{id}/houseimage")
    public void renderHouseImage(@PathVariable String id, HttpServletResponse response) throws IOException {
        FacilityCommand house = houseService.findCommandById(Long.valueOf(id));

        if (house.getImage() != null) {
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(house.getImage());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @PostMapping("/house/{id}/image")
    public String saveHouseImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
        houseService.saveImage(Long.valueOf(id), file);
        return "redirect:/house/" + id + "/show";
    }
}
