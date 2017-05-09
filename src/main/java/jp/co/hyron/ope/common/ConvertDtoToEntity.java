package jp.co.hyron.ope.common;

import java.util.ArrayList;
import java.util.List;

import jp.co.hyron.ope.dto.UserDto;
import jp.co.hyron.ope.entity.Authorities;
import jp.co.hyron.ope.entity.User;
import jp.co.hyron.ope.entity.type.AuthoritiesId;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ConvertDtoToEntity {
    public static User convertUserDtoToUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        // 暗号化変換する
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setUserId(dto.getUserId());
        user.setPassword(encoder.encode(dto.getPassWord()));
        user.setDisplayName(dto.getDisplayName());
        Authorities authorities = new Authorities();
        List<Authorities> list = new ArrayList<Authorities>();
        authorities.setId(new AuthoritiesId(dto.getUserId(), dto.getAuthorities()));
        list.add(authorities);
        user.setAuthorities(list);
        return user;
    }

}
