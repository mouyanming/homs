package jp.co.hyron.ope.controller;

import java.security.Principal;
import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.dto.EmailDto;
import jp.co.hyron.ope.dto.PasswordDto;
import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.service.AccountService;
import jp.co.hyron.ope.service.MailService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserMstRepository userMstRepository;

    @Autowired
    private AccountService accountService;

    @Value("${homs.rootUrl}")
    private String rootUrl;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = {"/reg/sendmail" }, method = RequestMethod.POST)
    public String sendMail(final Model model, @Valid @ModelAttribute("account") EmailDto account, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            String userId = account.getEmail();
            if (userMstRepository.findUsrByUsrId(userId) != null) {
                result.rejectValue("email", null, "mail.isexists");
            } else {
                UserMst usr = new UserMst();
                usr.setUsrId(userId);
                usr.setVdCd(UUID.randomUUID().toString());
                usr.setAcSts(Status.NOTACTIVE.getCode());
                userMstRepository.saveAndFlush(usr);
                String url = rootUrl + "/account/reg/input/" + usr.getVdCd();
                mailService.sendMail(userId, url, locale);
                model.addAttribute("email", userId);
                return "account/reg/sendmaildone";
            }
        }
        return "/account/reg/index";

    }

    @RequestMapping(value = {"/reg/index" }, method = RequestMethod.GET)
    public ModelAndView index(final Model model) {
        model.addAttribute("account", new EmailDto());
        return new ModelAndView("account/reg/index");
    }

    @RequestMapping(value = {"/reg/input/{code}" }, method = RequestMethod.GET)
    public ModelAndView input(final Model model, @PathVariable("code") String code) {
        UserMst usr = userMstRepository.findByVdCd(code);
        if (usr == null) {
            model.addAttribute("result", "失敗");
        } else if (usr.getAcSts() != Status.NOTACTIVE.getCode()) {
            model.addAttribute("result", "既にアクティビティ済み");
        } else {
            model.addAttribute("account", new AccountDto(usr));
        }
        return new ModelAndView("account/reg/input");
    }

    @RequestMapping(value = {"/reg/confirm" }, method = RequestMethod.POST)
    public String confirm(final Model model, @Valid @ModelAttribute("account") AccountDto account, BindingResult result) {
        if (!result.hasErrors()) {
            return "account/reg/confirm";
        }
        return "/account/reg/input";
    }

    @RequestMapping(value = {"/reg/active" }, method = RequestMethod.POST)
    public String active(final Model model, @Valid @ModelAttribute("account") AccountDto account, BindingResult result) {
        if (!result.hasErrors()) {
            accountService.createNewAccount(account);
        }
        return "/account/reg/success";
    }

    @RequestMapping(value = {"/changepassword" }, method = RequestMethod.GET)
    public String changePasswordBegin(final Model model, Principal principal) {
        String loginName = principal.getName();
        PasswordDto account = new PasswordDto();
        if (loginName != null && !"".equals(loginName)) {
            account.setEmail(loginName);
        }
        model.addAttribute("account", account);
        return "/account/changepassword";
    }

    @RequestMapping(value = {"/changepassword" }, method = RequestMethod.POST)
    public String changePasswordEnd(final Model model, @Valid @ModelAttribute("account") PasswordDto account, BindingResult result, Principal principal) {
        if (!result.hasErrors() && principal != null) {
        	UserDetails user= userDetailsService.loadUserByUsername(account.getEmail());
        	//user.getPassword().equals(arg0)
        }
        return "redirect:/";
    }

    @RequestMapping(value = {"/reg/findmypassword" }, method = RequestMethod.GET)
    public ModelAndView findMyPassword(final Model model) {
        model.addAttribute("account", new EmailDto());
        return new ModelAndView("account/reg/findmypassword");
    }

    @RequestMapping(value = {"/reg/sendpassword" }, method = RequestMethod.POST)
    public String sendPassword(final Model model, @Valid @ModelAttribute("account") EmailDto account, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            String userId = account.getEmail();
            UserMst user = userMstRepository.findUsrByUsrId(userId);
            if (user == null) {
                result.rejectValue("email", null, "mail.notexists");
            } else {
                if (user.getAcSts() != Status.ACTIVE.getCode()) {
                    result.rejectValue("email", null, "mail.notactive");
                } else {
                    String newPassword = accountService.setNewPassword(userId);
                    if (newPassword != null) {
                        mailService.sendPassword(userId, newPassword, locale);
                        return "account/reg/sendpassworddone";
                    } else {
                        result.rejectValue("email", null, "mail.error.setpassword");
                    }
                }

            }
        }
        return "/account/reg/findmypassword";
    }

    @PostMapping(value = {"/uploadimage" })
    public String uploadImage(@RequestParam("id") int id, @RequestParam("upload_file") MultipartFile file, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails principal) {
        if (!file.isEmpty() && principal != null && principal.getUsername() != null) {
            String username = principal.getUsername();
            boolean isAdmin = principal.getAuthorities().contains(new SimpleGrantedAuthority(CommonConst.ROLE_ADMIN));
            UserMst usr = accountService.getUserByEmail(username);
            if (isAdmin && id != usr.getId()) {
                usr = accountService.getUserById(id);
            }
            try {
                accountService.uploadImage(usr, file);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "can not upload image file ");
            }
            if (id != usr.getId() && isAdmin) {
                return "redirect:/account/info/" + usr.getId();
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "you are not login");
        }
        return "redirect:/account/profile";

    }

    @PostMapping(value = {"/uploadimage/1" }, produces = "application/json")
    @ResponseBody
    public String uploadImage1(@RequestParam("id") int id, @RequestParam("upload_file") MultipartFile file, @AuthenticationPrincipal UserDetails principal) {
        String result = "";
        String error = "";
        if (!file.isEmpty() && principal != null && principal.getUsername() != null) {
            String username = principal.getUsername();
            boolean isAdmin = principal.getAuthorities().contains(new SimpleGrantedAuthority(CommonConst.ROLE_ADMIN));
            UserMst usr = accountService.getUserByEmail(username);
            if (isAdmin && id != usr.getId()) {
                usr = accountService.getUserById(id);
            }
            try {
                result = accountService.uploadImage(usr, file);
            } catch (Exception e) {
                error = "can not upload image file ";
            }
        } else {
            error = "you are not login";
        }
        System.out.println("{\"result\":\"" + result + "\"," + "\"error\":\"" + error + "\"" + "}");
        return "{\"result\":\"" + result + "\"," + "\"error\":\"" + error + "\"" + "}";

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = accountService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping(value = {"/upload" }, method = RequestMethod.GET)
    public ModelAndView upload() {
        return new ModelAndView("account/upload");
    }

    @GetMapping(value = "/profile")
    public ModelAndView profile(final Model model, Principal principal) {
        UserMst user = accountService.getUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return new ModelAndView("account/profile");
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @GetMapping(value = "/list")
    public ModelAndView list(final Model model) {
        model.addAttribute("list", userMstRepository.findAll());
        return new ModelAndView("account/list");
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @RequestMapping(value = "/info/{id}")
    public ModelAndView userInfo(@PathVariable("id") int id, final Model model) {
        UserMst user = accountService.getUserById(id);
        model.addAttribute("user", user);
        return new ModelAndView("account/profile");
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @GetMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") int id, final Model model) {
        model.addAttribute("userDto", accountService.getUserDtoById(id));
        model.addAttribute("affiliations", CommonConst.affilications);
        model.addAttribute("authlist", CommonConst.authorites);
        return new ModelAndView("account/edit");
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @PostMapping(value = "/edit")
    public ModelAndView editPost(final Model model, @Valid @ModelAttribute UserDto userDto, BindingResult result, Locale locale) {
        if (!result.hasFieldErrors()) {
            boolean isOk = accountService.updateAccount(userDto, result);
            if (isOk) {
                return new ModelAndView("redirect:/account/list");
            }
        }
        return new ModelAndView("account/edit");
    }
}
