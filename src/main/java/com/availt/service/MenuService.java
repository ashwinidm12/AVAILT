package com.availt.service;

import com.availt.model.Menu;
import com.availt.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusByServiceId(Long serviceId) {
        return menuRepository.findByServiceId(serviceId);
    }

    public Optional<Menu> findMenuForService(Long menuId, Long serviceId) {
        return menuRepository.findByIdAndServiceId(menuId, serviceId);
    }
}