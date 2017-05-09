package jp.co.hyron.ope.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping("/foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @RequestMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping("/buttons.html")
    public ModelAndView buttons() {
        return new ModelAndView("buttons");
    }

    @RequestMapping("/blank.html")
    public ModelAndView blank() {
        return new ModelAndView("blank");
    }

    @RequestMapping("/flot.html")
    public ModelAndView flat() {
        return new ModelAndView("flot");
    }

    @Secured("ROLE_STAFF")
    @RequestMapping("/forms.html")
    public ModelAndView forms() {
        return new ModelAndView("forms");
    }

    @RequestMapping("/grid.html")
    public ModelAndView grid() {
        return new ModelAndView("grid");
    }

    @RequestMapping("/icons.html")
    public ModelAndView icons() {
        return new ModelAndView("icons");
    }

    @RequestMapping(value = {"/login.html", "/login" })
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/morris.html")
    public ModelAndView morris() {
        return new ModelAndView("morris");
    }

    @RequestMapping("/notifications.html")
    public ModelAndView notifications() {
        return new ModelAndView("notifications");
    }

    @RequestMapping("/panels-wells.html")
    public ModelAndView panelsWells() {
        return new ModelAndView("panels-wells");
    }

    @Secured({"ROLE_ADMIN" })
    @RequestMapping("/employee.html")
    public ModelAndView tables(final Model model) {
        return new ModelAndView("employee");
    }

    @RequestMapping("/typography.html")
    public ModelAndView typography() {
        return new ModelAndView("typography");
    }
}
