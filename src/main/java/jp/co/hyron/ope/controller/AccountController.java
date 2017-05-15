package jp.co.hyron.ope.controller;

import java.util.UUID;

import javax.validation.Valid;

import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserMstRepository userMstRepository;

    @RequestMapping(value = {"/sendmail" }, method = RequestMethod.POST)
    public String sendMail(final Model model, @Valid @ModelAttribute("account") AccountDto account, BindingResult result) {
        if (!result.hasErrors()) {
            String userId = account.getEmail();
            if (userMstRepository.findOne(userId) != null) {
                ObjectError error = new ObjectError("email", "An account already exists for this email.");
                result.addError(error);
            } else {
                UserMst usr = new UserMst();
                usr.setId(userId);
                usr.setVdCd(UUID.randomUUID().toString());
                usr.setAcSts((short) 0);
                userMstRepository.saveAndFlush(usr);
                mailService.sendMail(userId);
                model.addAttribute("email", userId);
                return "account/sendmaildone";
            }
        }
        return "/account/index";

    }

    @RequestMapping(value = {"/index" }, method = RequestMethod.GET)
    public ModelAndView index(final Model model) {
        model.addAttribute("account", new AccountDto());
        return new ModelAndView("account/index");
    }

    @RequestMapping(value = {"/input/{code}" }, method = RequestMethod.GET)
    public ModelAndView input(final Model model, @PathVariable("code") String code) {
        UserMst usr = userMstRepository.findByVdCd(code);
        if (usr == null) {
            model.addAttribute("result", "失敗");
        } else if (usr.getAcSts() != 0) {
            model.addAttribute("result", "既にアクティビティ済み");
        }
        model.addAttribute("account", usr);
        return new ModelAndView("account/input");
    }
}
