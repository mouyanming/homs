package jp.co.hyron.ope.controller;

import java.security.Principal;

import jp.co.hyron.ope.repository.ApplytrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private ApplytrRepository applytrRepository;

    @RequestMapping(value = {"/jimu/list" })
    public ModelAndView jlist(final Model model, Principal loginUser) {
        model.addAttribute("results", applytrRepository.findAll());
        return new ModelAndView("request/jimu/list");
    }

}
