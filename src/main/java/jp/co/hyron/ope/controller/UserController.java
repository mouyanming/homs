package jp.co.hyron.ope.controller;

import java.security.AccessControlException;
import java.security.Principal;

import javax.validation.Valid;

import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.repository.UserRepository;

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
@RequestMapping("/user")
public class UserController {

    // システム管理権限
    public static final String DEF_AUTHOR_ROLE_ADMIN = "ROLE_ADMIN";

    // 上位管理者権限
    public static final String DEF_AUTHOR_ROLE_MANAGER = "ROLE_MANAGER";

    // 一般権限
    public static final String DEF_AUTHOR_ROLE_NORMAL_USER = "ROLE_NORMAL_USER";

    // 事務処理権限
    public static final String DEF_AUTHOR_ROLE_JIMU = "ROLE_JIMU";

    @Autowired
    private UserRepository userRepository;

    @Secured({DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = {"/users.html", "/users", "/user" })
    public ModelAndView list(final Model model) {
        model.addAttribute("users", userRepository.findAll());
        return new ModelAndView("user/users");
    }

    @Secured({DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid UserDto user, BindingResult result, final Model model, Principal principal) {
        if (!result.hasErrors()) {
            User usr = new User();
            usr.convertToUser(user, true);
            userRepository.saveAndFlush(usr);
            return "redirect:/user/users";
        } else {
            return "/user/add";
        }

    }

    @Secured({DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add(final Model model) {
        UserDto user = new UserDto();
        // 新規画面で「運用閲覧ユーザ」の権限をデフォルト指定
        user.setAuthorities(DEF_AUTHOR_ROLE_NORMAL_USER);
        model.addAttribute("userDto", user);
        return new ModelAndView("user/add");
    }

    @RequestMapping(value = {"/edit/{id}" }, method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id, final Model model, @AuthenticationPrincipal User loginUser) {
        if (loginUser != null && (loginUser.getUserId().equals(id) || loginUser.isRoleUser(DEF_AUTHOR_ROLE_ADMIN))) {
            if (null != id && !"".equals(id)) {
                UserDto usr = new UserDto(userRepository.findByUserId(id));
                model.addAttribute("userDto", usr);
            } else {
                throw new AccessControlException("403 returned");
            }
        }
        return new ModelAndView("user/edit");
    }

    @RequestMapping(value = {"/edit" }, method = RequestMethod.POST)
    public String edit(@Valid UserDto user, BindingResult result, final Model model, @AuthenticationPrincipal User loginUser) {
        if (!result.hasErrors()) {
            if (loginUser != null && (loginUser.getUserId().equals(user.getUserId()) || loginUser.isRoleUser(DEF_AUTHOR_ROLE_ADMIN))) {
                User usr = userRepository.findByUserId(user.getUserId());
                usr.convertToUser(user, true);
                userRepository.saveAndFlush(usr);
            } else {
                throw new AccessControlException("403 returned");
            }
            return "redirect:/user/users";
        } else {
            return "/user/edit";
        }
    }

    @Secured({DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = {"/update/{id}" }, method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("id") String id, final Model model) {
        if (null != id && !"".equals(id)) {
            UserDto usr = new UserDto(userRepository.findByUserId(id));
            model.addAttribute("userDto", usr);
        }
        return new ModelAndView("user/update");
    }

    @Secured({DEF_AUTHOR_ROLE_ADMIN })
    @RequestMapping(value = {"/update" }, method = RequestMethod.POST)
    public String update(@Valid UserDto user, BindingResult result, final Model model, Principal principal) {
        if (!result.hasErrors()) {
            // ログインユーザー取得
            // String updName = (principal != null) ? principal.getName() : "未ログインユーザ";
            User usr = userRepository.findByUserId(user.getUserId());
            usr.convertToUser(user, true);
            userRepository.saveAndFlush(usr);
            return "redirect:/user/users";
        } else {
            return "/user/update";
        }
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
