package jp.co.hyron.ope.service;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import jp.co.hyron.ope.common.CommonUtil;
import jp.co.hyron.ope.common.ConvertDtoToEntity;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.controller.AccountController;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.storage.StorageService;

@Service
public class AccountService {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private UserMstRepository userMstRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final StorageService storageService;

    @Autowired
    public AccountService(StorageService storageService) {
        this.storageService = storageService;
    }

    public boolean createNewAccount(AccountDto account) {
        try {
            if (userDetailsService.getUserByEmail(account.getEmail()) == null) {
                userDetailsService.create(account);
            } 

            UserMst usr = userMstRepository.findUsrByUsrId(account.getEmail());
            usr.setUsrBth(account.getUsrBth());
            usr.setAcSts(Status.ACTIVE.getCode());
            usr.setUsrSex(account.getUsrSex());
            usr.setUsrMb(account.getUsrMb());
            usr.setUsrNm(account.getUsrNm());
            userMstRepository.saveAndFlush(usr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String setNewPassword(String email) {
        String newPassword = CommonUtil.makePassWord(8);
        User usr = userDetailsService.getUserByEmail(email).get();
        if (usr !=null) {
        	usr.setPassword(passwordEncoder.encode(newPassword));
            try {
                userDetailsService.update(usr);
            } catch (Exception e) {
                return null;
            }
            return newPassword;
        }
        return null;
    }

    public UserMst getUserByEmail(String email) {
        UserMst user = userMstRepository.findUsrByUsrId(email);
        String image = user.getImage();
        if (image != null && !"".equals(image)) {
            user.setImage(getImagePath(user.getImage()));
        }
        return user;
    }

    public UserDto getUserDtoById(int id) {
        UserMst user = userMstRepository.findOne(id);
        User usr = userDetailsService.getUserByEmail(user.getUsrId()).get();
        String image = user.getImage();
        if (image != null && !"".equals(image)) {
            user.setImage(getImagePath(user.getImage()));
        }
        UserDto dto = new UserDto(user);
        dto.setAuthorites(usr.getRole());
        return dto;
    }

    public UserMst getUserById(int id) {
        UserMst user = userMstRepository.findOne(id);
        String image = user.getImage();
        if (image != null && !"".equals(image)) {
            user.setImage(getImagePath(user.getImage()));
        }
        return user;
    }

    public String uploadImage(UserMst usr, MultipartFile file) {
        if (usr != null) {
            String email = usr.getUsrId();
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            int pos = email.indexOf("@");
            String filename = email.substring(0, pos == 0 ? email.length() : pos) + "_" + sdf.format(now) + ".jpg";
            try {
                storageService.store(file, filename);
                usr.setImage(filename);
                userMstRepository.saveAndFlush(usr);
            } catch (Exception e) {
                throw e;
            }
            return getImagePath(usr.getImage());
        }
        return null;

    }

    public Resource loadAsResource(String filename) {
        Resource file = storageService.loadAsResource(filename);
        return file;
    }

    public String getImagePath(String imageName) {
        Path path = storageService.load(imageName);
        String imagePath = MvcUriComponentsBuilder.fromMethodName(AccountController.class, "serveFile", path.getFileName().toString()).build().toString();
        return imagePath;
    }

    public boolean updateAccount(UserDto dto, BindingResult result) {
        boolean returnValue = true;
        try {
            UserMst usr = userMstRepository.findOne(dto.getId());
            if (usr.getUsrId().equals(dto.getUsrId())) {
                ConvertDtoToEntity.convertUserDtoToUserMst(dto, usr);
                userMstRepository.saveAndFlush(usr);
                User account = userDetailsService.getUserByEmail(usr.getUsrId()).get();
                if (!dto.getAuthorites().equals(account.getRole())) {
                    account.setRole(dto.getAuthorites());
                    userDetailsService.update(account);
                }
            } else {
                result.rejectValue("email", "error.email");
                returnValue = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            returnValue = false;
        }

        return returnValue;
    }
}
