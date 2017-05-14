package jp.co.hyron.ope.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import jp.co.hyron.ope.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private String userId;

    private Timestamp crtTm;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date epDt;

    private String jsgKb;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date lfDt;

    private String spUsrId;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date usrBth;

    private String usrMb;

    private String usrMl;

    @NotEmpty
    private String usrNm;

    private int usrSex;

    private String usrTtl;

    public UserDto(User usr) {
        this.userId = usr.getId();
        this.crtTm = usr.getCrtTm();
        this.epDt = usr.getEpDt();
        this.jsgKb = usr.getJsgKb();
        this.lfDt = usr.getLfDt();
        this.spUsrId = usr.getSpUsrId();
        this.usrBth = usr.getUsrBth();
        this.usrNm = usr.getUsrNm();
        this.usrSex = usr.getUsrSex();
        this.usrTtl = usr.getUsrTtl();
        this.usrMb = usr.getUsrMb();
        this.usrMl = usr.getUsrMl();
    }
}
