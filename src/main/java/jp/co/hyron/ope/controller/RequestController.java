package jp.co.hyron.ope.controller;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.ApplytrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ModelAndView jlist(final Model model, @AuthenticationPrincipal User loginUser) {
        if (loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_JIMU)) {
            model.addAttribute("results", applytrRepository.findAll());
        } else {
            model.addAttribute("results", applytrRepository.findListByUsrId(loginUser.getUserId()));
        }

        return new ModelAndView("request/jimu/list");
    }

}
