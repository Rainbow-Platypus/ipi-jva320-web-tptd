package com.ipi.jva320.controllers;


import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalarieController {
    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @GetMapping(value = "/salaries/{id}")
    public String DisplaySalarie(final ModelMap model,  @PathVariable Long id) {

        model.put("salarie", salarieAideADomicileService.getSalarie(id));
        return "detail_Salarie";
    }

    @GetMapping(value = "/salaries/aide/new")
    public String CreateSalarie(final ModelMap model) {
        return "add_Salarie";
    }

    @PostMapping(value = "/salaries/add")
    public String AddSalarie(SalarieAideADomicile salarie) throws SalarieException {
       salarie = salarieAideADomicileService.creerSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @PostMapping(value = "/salaries/save")
    public String SaveSalarie(SalarieAideADomicile salarie) throws SalarieException {
        salarieAideADomicileService.updateSalarieAideADomicile(salarie);
        return "redirect:/salaries/" + salarie.getId();
    }

    @GetMapping("/salaries")
    public String listSalarie(final ModelMap model,
                              @RequestParam(required = false) String nom,
                              @PageableDefault(size = 10, sort = "nom") Pageable pageable) {
        Page<SalarieAideADomicile> salariesPage;

        if (nom != null && !nom.isEmpty()) {
            // Recherche par nom avec pagination
            salariesPage = salarieAideADomicileService.searchSalariesByNom(nom, pageable);
        } else {
            // Liste complète avec pagination
            salariesPage = salarieAideADomicileService.getSalaries(pageable);
        }

        model.put("salaries", salariesPage.getContent());
        model.put("totalPages", salariesPage.getTotalPages());
        model.put("totalElements", salariesPage.getTotalElements());
        model.put("currentPage", pageable.getPageNumber());
        model.put("size", pageable.getPageSize());
        model.put("nom", nom);
        return "list";
    }



    @PostMapping(value = "/salaries/delete/{id}")
    public String DeleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }
}
