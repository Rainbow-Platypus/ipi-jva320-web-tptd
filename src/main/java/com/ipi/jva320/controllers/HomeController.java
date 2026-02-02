package com.ipi.jva320.controllers;


import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController{
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value = "/")
    public String home(final ModelMap model) {
        model.put("countSalaries", salarieAideADomicileService.countSalaries());
        return "home";
    }
}
