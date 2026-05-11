package com.availt.service;

import com.availt.model.ServiceEntity;
import com.availt.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<ServiceEntity> getServices(String category, String search, Double minRating, String sort) {
        List<ServiceEntity> services;
        boolean allCategories = category == null || category.trim().isEmpty() || category.equalsIgnoreCase("all");

        if (allCategories) {
            services = new ArrayList<ServiceEntity>(serviceRepository.findAllOrderedSeedFirst());
        } else {
            services = new ArrayList<ServiceEntity>(serviceRepository.findByCategoryOrderedSeedFirst(category.trim()));
        }

        if (search != null && !search.trim().isEmpty()) {
            String q = search.toLowerCase(Locale.ROOT);
            services = services.stream()
                    .filter(service -> service.getName().toLowerCase(Locale.ROOT).contains(q)
                            || service.getCategory().toLowerCase(Locale.ROOT).contains(q)
                            || (service.getType() != null && service.getType().toLowerCase(Locale.ROOT).contains(q))
                            || (service.getAddress() != null && service.getAddress().toLowerCase(Locale.ROOT).contains(q))
                            || (service.getCity() != null && service.getCity().toLowerCase(Locale.ROOT).contains(q)))
                    .collect(Collectors.toList());
        }

        if (minRating != null) {
            services = services.stream()
                    .filter(service -> service.getRating() != null && service.getRating() >= minRating)
                    .collect(Collectors.toList());
        }

        if (sort != null) {
            if (sort.equalsIgnoreCase("priceAsc")) {
                services.sort(Comparator
                        .comparing((ServiceEntity service) -> service.getPrice() == null ? 0.0 : service.getPrice())
                        .thenComparing(s -> Boolean.TRUE.equals(s.getCommunitySubmitted()))
                        .thenComparing(ServiceEntity::getId));
            } else if (sort.equalsIgnoreCase("priceDesc")) {
                services.sort(Comparator
                        .comparing((ServiceEntity service) -> service.getPrice() == null ? 0.0 : service.getPrice())
                        .reversed()
                        .thenComparing(s -> Boolean.TRUE.equals(s.getCommunitySubmitted()))
                        .thenComparing(ServiceEntity::getId));
            }
        }

        return services;
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    public List<String> listDistinctCategories() {
        return serviceRepository.findDistinctCategories();
    }
}
