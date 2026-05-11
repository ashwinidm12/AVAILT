package com.availt.web;

import com.availt.model.Menu;
import com.availt.model.ServiceEntity;
import com.availt.service.MenuService;
import com.availt.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MenuViewController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/menu/{serviceId}")
    public String menuPage(@PathVariable Long serviceId, Model model, HttpSession session) {
        ServiceEntity service = serviceService.getServiceById(serviceId);
        if (service == null) {
            return "redirect:/services";
        }
        if (!CategoryFormDefinition.isCatering(service.getCategory())) {
            return "redirect:/service/" + serviceId;
        }
        List<Menu> menus = menuService.getMenusByServiceId(serviceId);
        model.addAttribute("service", service);
        model.addAttribute("menus", menus);
        Long sel = (Long) session.getAttribute(SessionKeys.CATERING_MENU_ID);
        Long selSvc = (Long) session.getAttribute(SessionKeys.CATERING_MENU_SERVICE_ID);
        if (sel != null && serviceId.equals(selSvc)) {
            model.addAttribute("selectedMenuId", sel);
        }
        return "menu";
    }

    @PostMapping("/menu/{serviceId}/select")
    public String selectMenu(
            @PathVariable Long serviceId,
            @RequestParam Long menuId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        ServiceEntity service = serviceService.getServiceById(serviceId);
        if (service == null || !CategoryFormDefinition.isCatering(service.getCategory())) {
            return "redirect:/services";
        }
        return menuService.findMenuForService(menuId, serviceId)
                .map(m -> {
                    session.setAttribute(SessionKeys.CATERING_MENU_ID, m.getId());
                    session.setAttribute(SessionKeys.CATERING_MENU_SERVICE_ID, serviceId);
                    redirectAttributes.addFlashAttribute("menuSelected", m.getMenuName());
                    return "redirect:/book/" + serviceId;
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Invalid menu selection");
                    return "redirect:/menu/" + serviceId;
                });
    }
}
