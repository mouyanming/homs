package jp.co.hyron.ope.dto;

import java.sql.Timestamp;
import java.util.Date;

import jp.co.hyron.ope.entity.UserMst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotEmpty
    private String usrNm;

    private short usrSex;

    private String usrTtl;

    private int id;

    public UserDto(UserMst usr) {
        this.id = usr.getId();
        this.userId = usr.getUsrId();
        this.crtTm = usr.getCrtTm();
        this.epDt = usr.getEpDt();
        this.jsgKb = usr.getJsgKb();
        this.lfDt = usr.getLfDt();
        this.spUsrId = usr.getSpUsrId();
        this.usrBth = usr.getUsrBth();
        this.usrNm = usr.getUsrNm();
        this.usrMb = usr.getUsrMb();
        this.usrSex = usr.getUsrSex() == null ? 9 : usr.getUsrSex().shortValue();
    }
}
