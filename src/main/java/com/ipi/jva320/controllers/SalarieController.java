package com.ipi.jva320.controllers;


import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
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
                              @RequestParam(required = false) String nom) {
        if (nom != null && !nom.isEmpty()) {
            model.put("salaries", salarieAideADomicileService.getSalaries(nom));
        } else {
            model.put("salaries", salarieAideADomicileService.getSalaries());
        }
        model.put("nom", nom);  // Passer le nom au template
        return "list";
    }



    @PostMapping(value = "/salaries/delete/{id}")
    public String DeleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);
        return "redirect:/salaries";
    }
}
