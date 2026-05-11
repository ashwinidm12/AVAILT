package com.availt.web;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Resolves display image URLs: uses provider URL when present, otherwise a stable category-themed image.
 */
@Component
public class ImagePlaceholderHelper {

    private static final String GENERIC = "https://images.unsplash.com/photo-1497366216548-37526070297c?auto=format&fit=crop&w=1200&q=80";

    private final Map<String, String> byCategory = new HashMap<String, String>();

    public ImagePlaceholderHelper() {
        byCategory.put("catering", "https://images.unsplash.com/photo-1504674900247-0877df9cc836?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("venue booking", "https://images.unsplash.com/photo-1519167758481-83f550bb49b3?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("event management", "https://images.unsplash.com/photo-1464366400600-7168b8af9bc3?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("photographer", "https://images.unsplash.com/photo-1452587925148-ce544e77e70d?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("medical services", "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("blood donors", "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("baby care", "https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("tutoring", "https://images.unsplash.com/photo-1434030216411-0b793f4b4173?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("pet store", "https://images.unsplash.com/photo-1450778869180-41d0601e046e?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("farmers services", "https://images.unsplash.com/photo-1500382017468-9049fed747ef?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("grocery store", "https://images.unsplash.com/photo-1542838132-92c53300491e?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("boutique", "https://images.unsplash.com/photo-1445205170230-053b83016050?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("book mart", "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?auto=format&fit=crop&w=1200&q=80");
        byCategory.put("house service", "https://images.unsplash.com/photo-1581578731548-c64695cc6952?auto=format&fit=crop&w=1200&q=80");
    }

    public String fallbackForCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            return GENERIC;
        }
        String k = category.trim().toLowerCase(Locale.ROOT);
        String u = byCategory.get(k);
        return u != null ? u : GENERIC;
    }

    public String resolve(String imageUrl, String category) {
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            return imageUrl.trim();
        }
        return fallbackForCategory(category);
    }

    public Map<String, String> categoryFallbackMapView() {
        return Collections.unmodifiableMap(byCategory);
    }
}
