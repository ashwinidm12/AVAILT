package com.availt.service;

import com.availt.model.Menu;
import com.availt.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

    public List<Menu> getMenusByServiceId(Long serviceId) {
        return menuRepository.findByServiceId(serviceId);
    }
}