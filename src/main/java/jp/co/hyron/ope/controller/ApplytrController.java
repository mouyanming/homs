package jp.co.hyron.ope.controller;

import java.security.Principal;

import javax.validation.Valid;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.dto.ApplyDto;
import jp.co.hyron.ope.entity.Applytr;
import jp.co.hyron.ope.repository.ApplytrRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/applytr")
public class ApplytrController {
	
	@Autowired
    private ApplytrRepository applytrRepository;
	
	@Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
	@RequestMapping(value = "/applytrAdd")
    public String index() {
		System.out.println("test 1");
        return "/applytr/applytrAdd";
    }
	
//	@Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
//    @RequestMapping(value = {"/applytrAdd"})
//    public String applytrAdd(@Valid ApplyDto apply) {
//		System.out.println("test 1");
//    	Applytr applytr = new Applytr();
//    	applytr.convertToApply(apply);
//    	applytrRepository.saveAndFlush(applytr);
//		return "/applytr/applytrAdd";
//    }

//	@Secured({CommonConst.DEF_AUTHOR_ROLE_ADMIN })
//    @RequestMapping(value = "/applytrAdd", method = RequestMethod.POST)
//    public String applytrAdd(@Valid ApplyDto apply, BindingResult result, final Model model, Principal principal) {
//        if (!result.hasErrors()) {
//        	Applytr applytr = new Applytr();
//        	applytr.convertToApply(apply);
//        	applytrRepository.saveAndFlush(applytr);
//            return "redirect:/applytr/applytrEnd.html";
//        } else {
//            return "/applytr/applytrAdd.html";
//        }
//
//    }
	
}
