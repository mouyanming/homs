package jp.co.hyron.ope.service;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jp.co.hyron.ope.common.CommonUtil;
import jp.co.hyron.ope.common.ConvertDtoToEntity;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.controller.AccountController;
import jp.co.hyron.ope.dto.AccountDto;
import jp.co.hyron.ope.dto.FileUploadDto;
import jp.co.hyron.ope.dto.PasswordDto;
import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.entity.UserMst;
import jp.co.hyron.ope.repository.UserMstRepository;
import jp.co.hyron.ope.repository.UserRepository;
import jp.co.hyron.ope.storage.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMstRepository userMstRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final StorageService storageService;

    @Autowired
    public UserService(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * ログイン情報取得
     * @param id
     * @return
     */
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    /**
     * ログイン情報取得
     * @param email
     * @return
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    /**
     * 全てユーザ情報取得
     * @return
     */
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    /**
     * UserMst情報新規追加
     * @param email
     * @return
     */
    public String createNewUserMstWithNotActive(String email) {
        try {
            UserMst usr = new UserMst();
            usr.setUsrId(email);
            usr.setVdCd(UUID.randomUUID().toString());
            usr.setAcSts(Status.NOTACTIVE);
            userMstRepository.saveAndFlush(usr);
            return usr.getVdCd();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * User情報新規追加
     * @param dto
     * @return
     */
    public User create(AccountDto dto) {
        UserMst userMst = userMstRepository.findUsrByUsrId(dto.getEmail());
        if (userMst == null) {
            userMst = new UserMst();
            userMst.setUsrId(dto.getEmail());
            userMst.setAcSts(Status.ACTIVE);
            userMstRepository.saveAndFlush(userMst);
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setUserMst(userMst);
        return userRepository.saveAndFlush(user);
    }

    /**
     * User情報更新
     * @param usr
     */
    public void update(User usr) {
        userRepository.saveAndFlush(usr);
    }

    /**
     * パスワード更新
     * @param dto
     * @return
     */
    public boolean updatePassword(PasswordDto dto) {
        Optional<User> user = this.getUserByEmail(dto.getEmail());
        if (user.isPresent()) {
            User usr = user.get();
            if (!usr.getPassword().equals(dto.getOldPassword())) {
                return false;
            }
            usr.setPassword(passwordEncoder.encode(dto.getPassword()));
            this.update(usr);
            return true;
        }
        return false;
    }

    /**
     * VDCDでUserMst情報を取得
     * @param vdCd
     * @return
     */
    public UserMst findUserMstByVdCd(String vdCd) {
        return userMstRepository.findByVdCd(vdCd);
    }

    /**
     * User情報とUserMst情報を初期化
     * @param account
     * @return
     */
    public boolean accountInit(AccountDto account) {
        try {
            if (!this.getUserByEmail(account.getEmail()).isPresent()) {
                this.create(account);
            }

            UserMst usr = userMstRepository.findUsrByUsrId(account.getEmail());
            usr.setUsrBth(account.getUsrBth());
            usr.setAcSts(Status.ACTIVE);
            usr.setUsrSex(account.getUsrSex());
            usr.setUsrMb(account.getUsrMb());
            usr.setUsrNm(account.getUsrNm());
            usr.setPwdErrCnt((short) 0);
            userMstRepository.saveAndFlush(usr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * パスワード再設定
     * @param email
     * @return
     */
    public String setNewPassword(String email) {
        String newPassword = CommonUtil.makePassWord(8);
        Optional<User> result = this.getUserByEmail(email);
        if (result.isPresent()) {
            User usr = this.getUserByEmail(email).get();
            if (usr != null) {
                usr.setPassword(passwordEncoder.encode(newPassword));
                try {
                    this.update(usr);
                } catch (Exception e) {
                    return null;
                }
                return newPassword;
            }
        }
        return null;
    }

    /**
     * 画像アップロード
     * @param dto
     * @param isAdmin
     * @return
     */
    public String uploadImage(FileUploadDto dto) {
        MultipartFile file = dto.getMultipartFile();
        UserMst usr = userMstRepository.findOne(dto.getId());
        if (usr != null) {
            String email = usr.getUsrId();
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            int pos = email.indexOf("@");
            String filename = email.substring(0, pos == 0 ? email.length() : pos) + "_" + sdf.format(now) + ".jpg";
            try {
                storageService.store(file, filename);
                storageService.delete(usr.getImage());
                usr.setImage(filename);
                userMstRepository.saveAndFlush(usr);
            } catch (Exception e) {
                throw e;
            }
            return getImagePath(usr.getImage());
        }
        return null;

    }

    private String getImagePath(String imageName) {
        Path path = storageService.load(imageName);
        String imagePath = MvcUriComponentsBuilder.fromMethodName(AccountController.class, "serveFile", path.getFileName().toString()).build().toString();
        return imagePath;
    }

    public Resource loadAsResource(String filename) {
        Resource file = storageService.loadAsResource(filename);
        return file;
    }

    public UserMst getUserMstByEmail(String email) {
        return userMstRepository.findUsrByUsrId(email);
    }

    public List<UserMst> getAllUserMst() {
        return userMstRepository.findAll(new Sort("id"));
    }

    public UserMst getUserMstById(int id) {
        return userMstRepository.findOne(id);
    }

    public boolean updateAccount(UserDto dto) {
        boolean returnValue = true;
        try {
            UserMst usr = userMstRepository.findOne(dto.getId());
            if (usr.getUsrId().equals(dto.getUsrId())) {
                ConvertDtoToEntity.convertUserDtoToUserMst(dto, usr);
                userMstRepository.saveAndFlush(usr);
                Optional<User> user = this.getUserByEmail(usr.getUsrId());
                if (user.isPresent()) {
                    User account = this.getUserByEmail(usr.getUsrId()).get();
                    if (!dto.getAuthorites().equals(account.getRole())) {
                        account.setRole(dto.getAuthorites());
                        this.update(account);
                    }
                }
            } else {
                returnValue = false;
            }
        } catch (Exception e) {
            returnValue = false;
        }

        return returnValue;
    }

    public UserDto getUserDtoById(int id) {
        UserMst user = userMstRepository.findOne(id);
        Optional<User> result = this.getUserByEmail(user.getUsrId());
        String image = user.getImage();
        if (image != null && !"".equals(image)) {
            user.setImage(getImagePath(user.getImage()));
        }
        UserDto dto = new UserDto(user);
        if (result.isPresent()) {
            dto.setAuthorites(result.get().getRole());
        }
        return dto;
    }

}
