package jp.co.hyron.ope.controller;

import java.security.AccessControlException;

import javax.validation.Valid;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.dto.ApplyDto;
import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.Applytr;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.ApplytrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private ApplytrRepository applytrRepository;

    @RequestMapping(value = {"/jimu/list" })
    public ModelAndView jlist(final Model model, @AuthenticationPrincipal User loginUser) {
        if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_JIMU)) {
            model.addAttribute("results", applytrRepository.findAll());
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_MANAGER)) {
            model.addAttribute("results", applytrRepository.findListByApLet(loginUser.getUserId()));
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_NORMAL_USER)) {
            model.addAttribute("results", applytrRepository.findListByUsrId(loginUser.getUserId()));
        }

        return new ModelAndView("request/jimu/list");
    }
    
    @Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/jimu/add", method = RequestMethod.GET)
    public ModelAndView applytrAdd(final Model model, @AuthenticationPrincipal User loginUser) {
		Applytr applytr = new Applytr();
		applytr.setUsrId(loginUser.getUserId());
        model.addAttribute("applyDto", applytr);
        return new ModelAndView("request/jimu/add");
    }
	
	@Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/jimu/add", method = RequestMethod.POST)
    public String applytrAdd(@Valid ApplyDto apply, BindingResult result, final Model model) {
		if (!result.hasErrors()) {
        	Applytr applytr = new Applytr();
        	applytr.convertToApply(apply);
        	applytrRepository.saveAndFlush(applytr);
            return "request/jimu/list";
        } else {
            return "request/jimu/add";
        }
    }
	
	@RequestMapping(value = {"/jimu/edit/{id}" }, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") int id, final Model model, @AuthenticationPrincipal User loginUser) {
		ApplyDto apply = new ApplyDto(applytrRepository.findByApsNo(id));
        model.addAttribute("ApplyDto", apply);
        return new ModelAndView("request/jimu/edit");
    }

}
