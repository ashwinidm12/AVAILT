package com.availt.web;

import com.availt.dto.BookingRequest;
import com.availt.model.Booking;
import com.availt.model.Menu;
import com.availt.model.ServiceEntity;
import com.availt.model.User;
import com.availt.service.BookingService;
import com.availt.service.MenuService;
import com.availt.service.ServiceService;
import com.availt.service.UserService;
import com.availt.web.dto.BookingDraft;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookingFlowController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/book/{serviceId}")
    public String bookingForm(
            @PathVariable Long serviceId,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        ServiceEntity service = serviceService.getServiceById(serviceId);
        if (service == null) {
            return "redirect:/services";
        }
        String category = service.getCategory();
        if (CategoryFormDefinition.categoryNeedsMenuStep(category)) {
            Long mid = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
            Long msid = (Long) session.getAttribute(SessionKeys.CATERING_MENU_SERVICE_ID);
            if (mid == null || !serviceId.equals(msid)) {
                redirectAttributes.addFlashAttribute("error", "Please choose a catering menu first.");
                return "redirect:/menu/" + serviceId;
            }
        } else {
            session.removeAttribute(SessionKeys.CATERING_MENU_ID);
            session.removeAttribute(SessionKeys.CATERING_MENU_SERVICE_ID);
        }

        model.addAttribute("service", service);
        model.addAttribute("fields", CategoryFormDefinition.fieldsFor(category));
        model.addAttribute("categoryKey", category);
        if (CategoryFormDefinition.isCatering(category)) {
            Long menuId = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
            menuService.findMenuForService(menuId, serviceId).ifPresent(m -> model.addAttribute("selectedMenu", m));
        }
        return "booking";
    }

    @PostMapping("/book/{serviceId}")
    public String bookingSubmit(
            @PathVariable Long serviceId,
            HttpServletRequest request,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        ServiceEntity service = serviceService.getServiceById(serviceId);
        if (service == null) {
            return "redirect:/services";
        }
        String category = service.getCategory();

        if (CategoryFormDefinition.categoryNeedsMenuStep(category)) {
            Long mid = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
            Long msid = (Long) session.getAttribute(SessionKeys.CATERING_MENU_SERVICE_ID);
            if (mid == null || !serviceId.equals(msid)) {
                redirectAttributes.addFlashAttribute("error", "Please choose a catering menu first.");
                return "redirect:/menu/" + serviceId;
            }
        }

        Map<String, String> data = CategoryFormDefinition.extractData(category, request.getParameterMap());
        List<String> errors = CategoryFormDefinition.validate(category, data);
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("service", service);
            model.addAttribute("fields", CategoryFormDefinition.fieldsFor(category));
            model.addAttribute("categoryKey", category);
            if (CategoryFormDefinition.isCatering(category)) {
                Long menuId = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
                menuService.findMenuForService(menuId, serviceId).ifPresent(m -> model.addAttribute("selectedMenu", m));
            }
            return "booking";
        }

        BookingDraft draft = new BookingDraft();
        draft.setServiceId(serviceId);
        draft.setServiceName(service.getName());
        draft.setCategory(category);
        draft.setBookingData(data);

        double total;
        if (CategoryFormDefinition.isCatering(category)) {
            Long menuId = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
            Menu menu = menuService.findMenuForService(menuId, serviceId).orElse(null);
            if (menu == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid menu. Please select again.");
                return "redirect:/menu/" + serviceId;
            }
            int people = Integer.parseInt(data.get("people").trim());
            draft.setSelectedMenuId(menu.getId());
            draft.setSelectedMenuName(menu.getMenuName());
            draft.setSelectedMenuItems(menu.getItems());
            draft.setSelectedMenuPricePerPerson(menu.getPrice());
            total = people * (menu.getPrice() == null ? 0d : menu.getPrice());
            draft.setTotalPrice(total);
        } else {
            total = service.getPrice() == null ? 0d : service.getPrice();
            draft.setTotalPrice(total);
        }

        session.setAttribute(SessionKeys.BOOKING_DRAFT, draft);
        return "redirect:/summary";
    }

    @GetMapping("/summary")
    public String summary(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        BookingDraft draft = (BookingDraft) session.getAttribute(SessionKeys.BOOKING_DRAFT);
        if (draft == null) {
            redirectAttributes.addFlashAttribute("error", "No booking to review. Start from a service.");
            return "redirect:/services";
        }
        model.addAttribute("draft", draft);
        return "summary";
    }

    @PostMapping("/summary/confirm")
    public String confirm(
            HttpSession session,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        BookingDraft draft = (BookingDraft) session.getAttribute(SessionKeys.BOOKING_DRAFT);
        if (draft == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired. Please book again.");
            return "redirect:/services";
        }
        User user = currentUser(authentication);
        if (user == null) {
            return "redirect:/login";
        }

        BookingRequest req = new BookingRequest();
        req.setServiceId(draft.getServiceId());
        req.setCategory(draft.getCategory());
        req.setBookingData(CategoryFormDefinition.toBookingDataJsonMap(draft.getCategory(), draft.getBookingData()));
        req.setTotalPrice(draft.getTotalPrice());

        if (CategoryFormDefinition.isCatering(draft.getCategory())) {
            Map<String, Object> sm = new LinkedHashMap<String, Object>();
            sm.put("id", draft.getSelectedMenuId());
            sm.put("menuName", draft.getSelectedMenuName());
            sm.put("items", draft.getSelectedMenuItems());
            sm.put("pricePerPerson", draft.getSelectedMenuPricePerPerson());
            req.setSelectedMenu(sm);
        }

        try {
            Booking saved = bookingService.create(user, req);
            session.removeAttribute(SessionKeys.BOOKING_DRAFT);
            session.removeAttribute(SessionKeys.CATERING_MENU_ID);
            session.removeAttribute(SessionKeys.CATERING_MENU_SERVICE_ID);
            return "redirect:/confirmation?id=" + saved.getId();
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage() != null ? ex.getMessage() : "Could not save booking");
            return "redirect:/summary";
        }
    }

    @GetMapping("/confirmation")
    public String confirmation(
            @RequestParam(required = false) Long id,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (id == null) {
            return "redirect:/";
        }
        User user = currentUser(authentication);
        if (user == null) {
            return "redirect:/login";
        }
        Booking b = bookingService.getById(id);
        if (b == null || !b.getUserId().equals(user.getId())) {
            redirectAttributes.addFlashAttribute("error", "Booking not found.");
            return "redirect:/services";
        }
        model.addAttribute("booking", b);
        ServiceEntity svc = serviceService.getServiceById(b.getServiceId());
        model.addAttribute("serviceName", svc != null ? svc.getName() : "Service");
        try {
            model.addAttribute("bookingDataMap", objectMapper.readValue(b.getBookingData(),
                    new TypeReference<Map<String, Object>>() { }));
        } catch (IOException e) {
            model.addAttribute("bookingDataMap", Collections.<String, Object>emptyMap());
        }
        Map<String, Object> menuMap = Collections.emptyMap();
        if (b.getSelectedMenu() != null && !b.getSelectedMenu().isEmpty()) {
            try {
                menuMap = objectMapper.readValue(b.getSelectedMenu(), new TypeReference<Map<String, Object>>() { });
            } catch (IOException e) {
                menuMap = Collections.emptyMap();
            }
        }
        model.addAttribute("selectedMenuMap", menuMap);
        return "confirmation";
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object p = authentication.getPrincipal();
        if (p instanceof UserDetails) {
            return userService.findByEmail(((UserDetails) p).getUsername());
        }
        return null;
    }
}
