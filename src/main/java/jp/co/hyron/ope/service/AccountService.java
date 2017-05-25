package jp.co.hyron.ope.service;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.hyron.ope.common.CommonConst;
import jp.co.hyron.ope.common.CommonUtil;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.controller.AccountController;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class AccountService {

    @Autowired
    private JdbcUserDetailsManager userDetailsService;

    @Autowired
    private UserMstRepository userMstRepository;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private final StorageService storageService;

    @Autowired
    public AccountService(StorageService storageService) {
        this.storageService = storageService;
    }

    public boolean createNewAccount(AccountDto account) {
        try {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(CommonConst.ROLE_NORMAL_USER));
            User userDetails = new User(account.getEmail(), encoder.encode(account.getPassword()), authorities);
            if (!userDetailsService.userExists(account.getEmail())) {
                userDetailsService.createUser(userDetails);
            } else {
                userDetailsService.updateUser(userDetails);
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
        if (userDetailsService.userExists(email)) {
            User userDetails = new User(email, encoder.encode(newPassword), userDetailsService.loadUserByUsername(email).getAuthorities());
            try {
                userDetailsService.updateUser(userDetails);
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
}
