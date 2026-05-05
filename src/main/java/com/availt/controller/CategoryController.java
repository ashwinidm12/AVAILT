package com.availt.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private static final List<Map<String, String>> CATEGORIES = Collections.unmodifiableList(
            Arrays.asList(
                    cat("Catering", "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?auto=format&fit=crop&w=600&q=80"),
                    cat("Venue Booking", "https://images.unsplash.com/photo-1519167758481-83f29da8c62f?auto=format&fit=crop&w=600&q=80"),
                    cat("Event Management", "https://images.unsplash.com/photo-1540575467063-027aef1f32c4?auto=format&fit=crop&w=600&q=80"),
                    cat("Photographer", "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?auto=format&fit=crop&w=600&q=80"),
                    cat("Medical Services", "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?auto=format&fit=crop&w=600&q=80"),
                    cat("Blood Donors", "https://images.unsplash.com/photo-1615461066841-6116e61058f4?auto=format&fit=crop&w=600&q=80"),
                    cat("Baby Care", "https://images.unsplash.com/photo-1519689680058-324335c77eba?auto=format&fit=crop&w=600&q=80"),
                    cat("Tutoring", "https://images.unsplash.com/photo-1434030216411-0b793f4b4173?auto=format&fit=crop&w=600&q=80"),
                    cat("Pet Store", "https://images.unsplash.com/photo-1583337130417-3346a1be7dee?auto=format&fit=crop&w=600&q=80"),
                    cat("Farmers Services", "https://images.unsplash.com/photo-1625246333195-78d9c38ad449?auto=format&fit=crop&w=600&q=80"),
                    cat("Grocery Store", "https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=600&q=80"),
                    cat("Boutique", "https://images.unsplash.com/photo-1445205170230-053b83016050?auto=format&fit=crop&w=600&q=80"),
                    cat("Book Mart", "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?auto=format&fit=crop&w=600&q=80"),
                    cat("House Service", "https://images.unsplash.com/photo-1581578731548-c64695cc6952?auto=format&fit=crop&w=600&q=80")
            ));

    private static Map<String, String> cat(String name, String image) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("name", name);
        m.put("image", image);
        return m;
    }

    @GetMapping
    public List<Map<String, String>> list() {
        return CATEGORIES.stream().map(m -> {
            Map<String, String> copy = new LinkedHashMap<>();
            copy.put("name", m.get("name"));
            copy.put("image", m.get("image"));
            return copy;
        }).collect(Collectors.toList());
    }
}
