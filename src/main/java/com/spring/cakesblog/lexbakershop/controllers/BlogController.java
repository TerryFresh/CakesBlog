package com.spring.cakesblog.lexbakershop.controllers;

import com.spring.cakesblog.lexbakershop.entity.Cakes;
import com.spring.cakesblog.lexbakershop.repositories.CakesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller
public class BlogController {
    @Autowired
    CakesRepository cakesRepository;

    @GetMapping("/myworks")
    public String blogMain(Model model){
        Iterable<Cakes> cakes = cakesRepository.findAll();
        model.addAttribute("cakes", cakes);
        return "my-works-view";
    }

    @GetMapping("/myworks/add")
    public String blogAdd(Model model){
        Iterable<Cakes> cakes = cakesRepository.findAll();
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

        return "redirect:/myworks";
    }


}
