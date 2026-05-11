package com.availt.web;

import com.availt.model.ServiceEntity;
import com.availt.service.ServiceService;
import com.availt.repository.ServiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class JoinProviderController {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ImagePlaceholderHelper imagePlaceholderHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/join-provider")
    public String joinForm(Model model) {
        model.addAttribute("categories", serviceService.listDistinctCategories());
        return "join-provider";
    }

    @PostMapping("/join-provider")
    public String joinSubmit(
            @RequestParam String businessName,
            @RequestParam String ownerName,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String category,
            @RequestParam String serviceType,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String description,
            @RequestParam String priceRangeMin,
            @RequestParam(required = false) String priceRangeMax,
            @RequestParam String openingHours,
            @RequestParam(required = false) String shopImageUrl,
            @RequestParam(required = false) String serviceImageUrls,
            @RequestParam(required = false) String cateringMenuItems,
            @RequestParam(required = false) String cateringPackageImageUrls,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (businessName.trim().isEmpty() || ownerName.trim().isEmpty() || phone.trim().isEmpty()
                || email.trim().isEmpty() || category.trim().isEmpty() || serviceType.trim().isEmpty()
                || address.trim().isEmpty() || city.trim().isEmpty() || description.trim().isEmpty()
                || priceRangeMin.trim().isEmpty() || openingHours.trim().isEmpty()) {
            model.addAttribute("error", "Please fill all required fields.");
            model.addAttribute("categories", serviceService.listDistinctCategories());
            return "join-provider";
        }

        String catNorm = matchCategory(category.trim());
        if (catNorm == null) {
            model.addAttribute("error", "Please choose a valid category from the list.");
            model.addAttribute("categories", serviceService.listDistinctCategories());
            return "join-provider";
        }

        Double price = parsePrice(priceRangeMin);
        if (price == null) {
            model.addAttribute("error", "Enter a valid starting price (numbers only).");
            model.addAttribute("categories", serviceService.listDistinctCategories());
            return "join-provider";
        }
        Double pmax = parsePrice(priceRangeMax);

        ServiceEntity s = new ServiceEntity();
        s.setName(businessName.trim());
        s.setCategory(catNorm);
        s.setType(serviceType.trim());
        s.setAddress(address.trim());
        s.setCity(city.trim());
        s.setContact(phone.trim());
        s.setOwnerName(ownerName.trim());
        s.setProviderEmail(email.trim());
        s.setOpeningHours(openingHours.trim());
        s.setPrice(price);
        s.setPriceMax(pmax);
        s.setRating(4.3);
        s.setCommunitySubmitted(Boolean.TRUE);

        String desc = description.trim();
        if ("Catering".equalsIgnoreCase(catNorm) && cateringMenuItems != null && !cateringMenuItems.trim().isEmpty()) {
            desc = desc + "\n\nMenu highlights:\n" + cateringMenuItems.trim();
        }
        s.setDescription(desc);

        String mainImg = shopImageUrl != null && !shopImageUrl.trim().isEmpty()
                ? shopImageUrl.trim()
                : imagePlaceholderHelper.fallbackForCategory(catNorm);
        s.setImageUrl(mainImg);

        List<String> gallery = new ArrayList<String>();
        gallery.addAll(splitUrlLines(serviceImageUrls));
        if ("Catering".equalsIgnoreCase(catNorm)) {
            gallery.addAll(splitUrlLines(cateringPackageImageUrls));
        }
        gallery = gallery.stream().map(String::trim).filter(u -> !u.isEmpty()).collect(Collectors.toList());
        if (!gallery.isEmpty()) {
            try {
                s.setGalleryImageUrls(objectMapper.writeValueAsString(gallery));
            } catch (JsonProcessingException e) {
                s.setGalleryImageUrls(null);
            }
        }

        serviceRepository.save(s);
        redirectAttributes.addFlashAttribute("joinSuccess", businessName.trim());
        return "redirect:/services?category=" + UriUtils.encodeQueryParam(catNorm, StandardCharsets.UTF_8);
    }

    private String matchCategory(String selected) {
        for (String c : serviceService.listDistinctCategories()) {
            if (c.equalsIgnoreCase(selected)) {
                return c;
            }
        }
        return null;
    }

    private static Double parsePrice(String raw) {
        if (raw == null) {
            return null;
        }
        String t = raw.replaceAll("[^0-9.]", "").trim();
        if (t.isEmpty()) {
            return null;
        }
        try {
            return Double.parseDouble(t);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<String> splitUrlLines(String block) {
        if (block == null || block.trim().isEmpty()) {
            return new ArrayList<String>();
        }
        String[] parts = block.split("[\\r\\n,;]+");
        List<String> out = new ArrayList<String>();
        for (String p : parts) {
            if (p != null && !p.trim().isEmpty()) {
                out.add(p.trim());
            }
        }
        return out;
    }
}
