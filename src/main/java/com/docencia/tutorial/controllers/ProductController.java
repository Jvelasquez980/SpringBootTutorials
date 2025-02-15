package com.docencia.tutorial.controllers;


import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;


import jakarta.validation.Valid;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;


@Controller

public class ProductController {


    private static final List<Map<String, String>> products = new ArrayList<>(List.of(

            Map.of("id", "1", "name", "TV", "description", "Best TV"),

            Map.of("id", "2", "name", "iPhone", "description", "Best iPhone"),

            Map.of("id", "3", "name", "Chromecast", "description", "Best Chromecast"),

            Map.of("id", "4", "name", "Glasses", "description", "Best Glasses")

    ));


    @GetMapping("/products")

    public String index(Model model) {

        model.addAttribute("title", "Products - Online Store");

        model.addAttribute("subtitle", "List of products");

        model.addAttribute("products", products);

        return "product/index";

    }


    @GetMapping("/products/create")

    public String create(Model model) {

        model.addAttribute("title", "Create Product");

        model.addAttribute("productForm", new ProductForm());

        return "product/create";

    }


    @PostMapping("/products/save")
    public String save(@Valid @ModelAttribute("productForm") ProductForm productForm,
                       BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Create Product");
            return "product/create";
        }

        // Simulating saving the product (no DB persistence)
        Map<String, String> newProduct = new HashMap<>();
        newProduct.put("id", String.valueOf(products.size() + 1));
        newProduct.put("name", productForm.getName());
        newProduct.put("description", "Price: $" + productForm.getPrice());
        newProduct.put("price", String.valueOf(productForm.getPrice()));
        products.add(newProduct);

        // Redirect to the created confirmation page
        return "redirect:/products/created";
    }

    @GetMapping("/products/created")
    public String created(Model model) {
        model.addAttribute("title", "Product Created");
        model.addAttribute("subtitle", "Your product has been created successfully.");
        return "product/created";
    }

}