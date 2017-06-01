package jp.co.hyron.ope.controller;

import java.nio.file.Path;
import java.security.Principal;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.common.MessageAccess;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.dto.EmailDto;
import jp.co.hyron.ope.dto.FileUploadDto;
import jp.co.hyron.ope.dto.JsonResult;
import jp.co.hyron.ope.dto.PasswordDto;
import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.service.MailService;
import jp.co.hyron.ope.service.UserService;
import jp.co.hyron.ope.storage.StorageService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final MailService mailService;

    @Value("${homs.rootUrl}")
    private String rootUrl;

    private final UserService userService;

    private final StorageService storageService;

    private final MessageAccess messageAccess;

    @RequestMapping(value = {"/reg/index" }, method = RequestMethod.POST)
    public String sendMail(final Model model, @Valid @ModelAttribute("account") EmailDto account, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            String email = account.getEmail();
            model.addAttribute("email", email);
            if (userService.getUserByEmail(email).isPresent()) {
                // 既に存在している
                result.rejectValue("email", "mail.isexists");
            } else {
                String vdCd = userService.createNewUserMstWithNotActive(email);
                if (vdCd != null) {
                    String url = rootUrl + "/account/reg/input/" + vdCd;
                    mailService.sendMail(email, url);
                    return "account/reg/sendmail";
                } else {
                    result.rejectValue("email", "mail.error");
                }
            }
        }
        return "account/reg/index";

    }

    @RequestMapping(value = {"/reg/index" }, method = RequestMethod.GET)
    public String index(final Model model) {
        model.addAttribute("account", new EmailDto());
        return "account/reg/index";
    }

    @RequestMapping(value = {"/reg/input/{code}" }, method = RequestMethod.GET)
    public String input(final Model model, @PathVariable("code") String code) {
        UserMst usr = userService.findUserMstByVdCd(code);
        if (usr == null) {
            model.addAttribute("result", messageAccess.getMessage("error.active.failure"));
        } else if (usr.getAcSts() != Status.NOTACTIVE) {
            model.addAttribute("result", messageAccess.getMessage("error.active.again"));
        } else {
            model.addAttribute("account", new AccountDto(usr));
        }
        return "account/reg/input";
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
            if (userService.accountInit(account)) {
                return "/account/reg/success";
            } else {
                result.rejectValue("email", "mail.error");
            }
        }
        return "account/reg/confirm";

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
            if (!account.getPassword().equals(account.getPasswordTwice())) {
                result.rejectValue("passwordTwice", "error.validation.password.notequal");
            }
            boolean isUpdated = userService.updatePassword(account);
            if (!isUpdated) {
                result.rejectValue("oldPassword", "error.validation.password.cannotupdate");
            } else {
                return "redirect:/";
            }
        }
        return "/account/changepassword";
    }

    @RequestMapping(value = {"/reg/findmypassword" }, method = RequestMethod.GET)
    public String findMyPassword(final Model model) {
        model.addAttribute("account", new EmailDto());
        return "account/reg/findmypassword";
    }

    @RequestMapping(value = {"/reg/findmypassword" }, method = RequestMethod.POST)
    public String sendPassword(final Model model, @Valid @ModelAttribute("account") EmailDto account, BindingResult result, Locale locale) {
        if (!result.hasErrors()) {
            String userId = account.getEmail();
            Optional<User> returnValue = userService.getUserByEmail(userId);
            if (returnValue.isPresent()) {
                UserMst user = returnValue.get().getUserMst();
                if (user == null) {
                    result.rejectValue("email", "mail.notexists");
                } else {
                    if (user.getAcSts() != Status.ACTIVE) {
                        result.rejectValue("email", "mail.notactive");
                    } else {
                        String newPassword = userService.setNewPassword(userId);
                        if (newPassword != null) {
                            mailService.sendPassword(userId, newPassword);
                            return "account/reg/sendpassword";
                        } else {
                            result.rejectValue("email", "mail.error.setpassword");
                        }
                    }
                }
            }
        }
        return "/account/reg/findmypassword";
    }

    @PostMapping(value = {"/uploadimage" }, produces = "application/json")
    @ResponseBody
    public JsonResult uploadImage(@Validated FileUploadDto dto, BindingResult result, Principal principal) {
        JsonResult returnValue = new JsonResult();
        if (!result.hasErrors()) {
            if (principal != null && principal.getName() != null) {
                try {
                    returnValue.setResult(getImagePath(userService.uploadImage(dto, storageService)));
                } catch (Exception e) {
                    returnValue.setError(messageAccess.getMessage("error.image.upload"));
                }
            } else {
                returnValue.setError(messageAccess.getMessage("error.notlogin"));
            }
        } else {
            returnValue.setError(messageAccess.getMessage("error.validation"));
        }
        return returnValue;

    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private String getImagePath(String imageName) {
        Path path = storageService.load(imageName);
        String imagePath = MvcUriComponentsBuilder.fromMethodName(AccountController.class, "serveFile", path.getFileName().toString()).build().toString();
        return imagePath;
    }

    @RequestMapping(value = {"/upload" }, method = RequestMethod.GET)
    public String upload() {
        return "account/upload";
    }

    @GetMapping(value = "/profile")
    public String profile(final Model model, Principal principal) {
        UserMst user = userService.getUserMstByEmail(principal.getName());
        if (user != null && user.getImage() != null) {
            user.setImage(getImagePath(user.getImage()));
        }
        model.addAttribute("user", user);
        return "account/profile";
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @GetMapping(value = "/list")
    public String list(final Model model) {
        model.addAttribute("list", userService.getAllUserMst());
        return "account/list";
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @RequestMapping(value = "/info/{id}")
    public String userInfo(@PathVariable("id") int id, final Model model) {
        UserMst user = userService.getUserMstById(id);
        if (user != null && user.getImage() != null) {
            user.setImage(getImagePath(user.getImage()));
        }
        model.addAttribute("user", user);
        return "account/profile";
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") int id, final Model model) {
        model.addAttribute("userDto", userService.getUserDtoById(id));
        model.addAttribute("affiliations", CommonConst.affilications);
        model.addAttribute("authlist", CommonConst.authorites);
        model.addAttribute("statusList", CommonConst.statusList);
        model.addAttribute("positions", CommonConst.positions);
        model.addAttribute("departments", CommonConst.departments);
        return "account/edit";
    }

    @Secured({CommonConst.ROLE_ADMIN })
    @PostMapping(value = "/edit")
    public String editPost(final Model model, @Valid @ModelAttribute UserDto userDto, BindingResult result, Locale locale) {
        if (!result.hasFieldErrors()) {
            boolean isOk = userService.updateUserInfo(userDto);
            if (isOk) {
                return "redirect:/account/list";
            } else {
                result.rejectValue("email", "error.email");
            }
        }
        return "account/edit";
    }
}
