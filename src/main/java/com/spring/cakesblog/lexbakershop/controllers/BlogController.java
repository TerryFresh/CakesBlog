package com.spring.cakesblog.lexbakershop.controllers;

import com.spring.cakesblog.lexbakershop.entity.Cakes;
import com.spring.cakesblog.lexbakershop.repositories.CakesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Controller
public class BlogController {
    @Autowired
    CakesRepository cakesRepository;

    @GetMapping("/myworks")
    public String blogMain(Model model) {
        Iterable<Cakes> cakes = cakesRepository.findAll();
        model.addAttribute("cakes", cakes);
        return "my-works-view";
    }

    @GetMapping("/myworks/add")
    public String blogAdd(Model model) {
        Iterable<Cakes> cakes = cakesRepository.findAll();
        model.addAttribute("cakes", cakes);
        return "add-new-cakes-view";
    }

    @PostMapping("/myworks/add")
    public String blogPostAdd(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String amount,
            @RequestParam int price,
            @RequestParam MultipartFile file,
            Model model) throws IOException {

        String fileName = "/" + file.getOriginalFilename();

        BufferedImage input = ImageIO.read(file.getInputStream());

        File output = new File("D:/Java/JavaProject/SpringBootDudar/LexBakerShop/src/main/resources/static" + fileName);
        ImageIO.write(input, "JPG", output);


        Cakes cakes = new Cakes(name, description, amount, price, fileName);
        cakesRepository.save(cakes);

        return "redirect:/myworks/add";
    }

    @GetMapping("/myworks/add/{id}")
    public String blogDetails(@PathVariable(value = "id") int id, Model model) {
        if (!cakesRepository.existsById(id)) {
            return "redirect:/myworks";
        }

        Optional<Cakes> cake = cakesRepository.findById(id);
        ArrayList<Cakes> res = new ArrayList<>();
        cake.ifPresent(res::add);
        model.addAttribute("cake", res);
        return "blog-details-view";
    }

    @GetMapping("/myworks/add/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") int id, Model model) {
        if (!cakesRepository.existsById(id)) {
            return "redirect:/myworks";
        }

        Optional<Cakes> cake = cakesRepository.findById(id);
        ArrayList<Cakes> res = new ArrayList<>();
        cake.ifPresent(res::add);
        model.addAttribute("cake", res);
        return "blog-edit-view";
    }

    @PostMapping("/myworks/add/{id}/edit")
    public String blogPostUpdate(
            @PathVariable(value = "id") int id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String amount,
            @RequestParam int price,
            @RequestParam MultipartFile file,
            Model model) throws IOException {


        Cakes cakes = cakesRepository.findById(id).orElseThrow();
        cakes.setName(name);
        cakes.setDescription(description);
        cakes.setAmount(amount);
        cakes.setPrice(price);

        if (!Objects.equals(file.getOriginalFilename(), "")) {

            String fileName = "/" + file.getOriginalFilename();

            BufferedImage input = ImageIO.read(file.getInputStream());

            File output = new File("D:/Java/JavaProject/SpringBootDudar/LexBakerShop/src/main/resources/static" + fileName);
            ImageIO.write(input, "JPG", output);

            cakes.setFileName(fileName);
        }
        cakesRepository.save(cakes);

        return "redirect:/myworks/add";
    }

    @PostMapping("/myworks/add/{id}/remove")
    public String blogPostDelete(
            @PathVariable(value = "id") int id,
            Model model) {

        Cakes cakes = cakesRepository.findById(id).orElseThrow();
        cakesRepository.delete(cakes);

//        File file = new File("D:/Java/JavaProject/SpringBootDudar/LexBakerShop/src/main/resources/static" + cakes.getFileName());
//        file.delete();

        return "redirect:/myworks/add";
    }
}

