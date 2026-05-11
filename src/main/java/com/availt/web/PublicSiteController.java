package com.availt.web;

import com.availt.model.ServiceEntity;
import com.availt.service.ServiceService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PublicSiteController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ImagePlaceholderHelper imagePlaceholderHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/")
    public String home(Model model) {
        List<ServiceEntity> all = serviceService.getServices(null, null, null, null);
        int n = Math.min(6, all.size());
        model.addAttribute("featured", n == 0 ? all : all.subList(0, n));
        return "home";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        List<Map<String, String>> cards = new ArrayList<Map<String, String>>();
        for (String cat : serviceService.listDistinctCategories()) {
            List<ServiceEntity> list = serviceService.getServices(cat, null, null, null);
            ServiceEntity sample = list.isEmpty() ? null : list.get(0);
            Map<String, String> row = new LinkedHashMap<String, String>();
            row.put("name", cat);
            String img = sample == null ? null : sample.getImageUrl();
            row.put("imageUrl", imagePlaceholderHelper.resolve(img, cat));
            cards.add(row);
        }
        model.addAttribute("categoryCards", cards);
        return "categories";
    }

    @GetMapping("/services")
    public String services(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String sort,
            Model model) {
        String cat = category == null || category.isEmpty() || "all".equalsIgnoreCase(category) ? null : category;
        model.addAttribute("services", serviceService.getServices(cat, q, null, sort));
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("category", category == null ? "all" : category);
        model.addAttribute("sort", sort == null ? "" : sort);
        model.addAttribute("categories", serviceService.listDistinctCategories());
        return "services";
    }

    @GetMapping("/service/{id}")
    public String serviceDetail(@PathVariable Long id, Model model) {
        ServiceEntity s = serviceService.getServiceById(id);
        if (s == null) {
            return "redirect:/services";
        }
        model.addAttribute("service", s);
        model.addAttribute("isCatering", CategoryFormDefinition.isCatering(s.getCategory()));
        model.addAttribute("heroImage", imagePlaceholderHelper.resolve(s.getImageUrl(), s.getCategory()));
        model.addAttribute("galleryImages", parseGalleryUrls(s.getGalleryImageUrls()));
        return "service-detail";
    }

    private List<String> parseGalleryUrls(String json) {
        if (json == null || json.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            List<String> urls = objectMapper.readValue(json, new TypeReference<List<String>>() { });
            List<String> cleaned = new ArrayList<String>();
            for (String u : urls) {
                if (u != null && !u.trim().isEmpty()) {
                    cleaned.add(u.trim());
                }
            }
            return cleaned;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
}
