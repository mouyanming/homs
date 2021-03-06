package jp.co.hyron.ope.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.dto.ApplyDto;
import jp.co.hyron.ope.entity.Applytr;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.ApplytrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/request")
public class RequestController {

    @Autowired
    private ApplytrRepository applytrRepository;
    
    private List<Applytr> applytrList;
    
    private List<ApplyDto> applyDtoList;

    @RequestMapping(value = {"/jimu/list" })
    public ModelAndView jlist(final Model model, @AuthenticationPrincipal User loginUser) {
    	applytrList = new ArrayList<Applytr>();
    	applyDtoList = new ArrayList<ApplyDto>();
        if (loginUser != null && (loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_JIMU) || loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_ADMIN))) {
        	applytrList = applytrRepository.findAll();
        	for(Applytr applytr : applytrList){
        		applyDtoList.add(new ApplyDto(applytr));
        	}
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_MANAGER)) {
        	applytrList = applytrRepository.findListByApLet(loginUser.getUserId());
        	for(Applytr applytr : applytrList){
        		applyDtoList.add(new ApplyDto(applytr));
        	}	
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_NORMAL_USER)) {
        	applytrList = applytrRepository.findListByUsrId(loginUser.getUserId());
        	for(Applytr applytr : applytrList){
        		applyDtoList.add(new ApplyDto(applytr));
        	}
        }
        model.addAttribute("results", applyDtoList);
        return new ModelAndView("request/jimu/list");
    }
    
    @Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/jimu/add", method = RequestMethod.GET)
    public ModelAndView applytrAdd(final Model model, @AuthenticationPrincipal User loginUser) {
		System.out.println("ModelAndView applytrAdd");
    	Applytr applytr = new Applytr();
		applytr.setUsrId(loginUser.getUserId());
        model.addAttribute("applyDto", applytr);
        return new ModelAndView("request/jimu/add");
    }
	
	@Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/jimu/add", method = RequestMethod.POST)
    public String applytrAdd(@Valid ApplyDto apply, BindingResult result, final Model model) {
		System.out.println("String applytrAdd");
		if (!result.hasErrors()) {
        	Applytr applytr = new Applytr();
        	apply.setApsNo(0);
        	applytr.convertToApply(apply);
        	applytrRepository.saveAndFlush(applytr);
            return "redirect:/request/jimu/list";
        } else {
        	System.out.println("result.hasErrors()="+result.getErrorCount());
            return "request/jimu/add";
        }
    }
	
	@GetMapping(value = "/jimu/edit/{id}")
    public String applytrEdit(@PathVariable("id") Integer id, final Model model, @AuthenticationPrincipal User loginUser) {
		System.out.println("ModelAndView applytrEdit id="+id);
		if (loginUser != null && (loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_JIMU) || loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_ADMIN))) {
            model.addAttribute("authorities", CommonConst.DEF_AUTHOR_ROLE_JIMU);
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_MANAGER)) {
            model.addAttribute("authorities", CommonConst.DEF_AUTHOR_ROLE_MANAGER);
        } else if (loginUser != null && loginUser.isRoleUser(CommonConst.DEF_AUTHOR_ROLE_NORMAL_USER)) {
            model.addAttribute("authorities", CommonConst.DEF_AUTHOR_ROLE_NORMAL_USER);
        }
		ApplyDto apply = new ApplyDto(applytrRepository.findByApsNo(id));
        model.addAttribute("apply", apply);
        return "request/jimu/edit";
    }
	
	@PostMapping(value = "/jimu/edit")
    public String applytrEdit(@Valid ApplyDto apply, BindingResult result, final Model model, Principal principal) {
    	System.out.println("String applytrEdit");
    	System.out.println("apply"+apply.getApKbName());
    	if (!result.hasErrors()) {
        	Applytr applytr = new Applytr();
        	applytr.convertToApply(apply);
        	applytrRepository.saveAndFlush(applytr);
            return "redirect:/request/jimu/list";
        } else {
            return "request/jimu/edit";
        }
    }

}
