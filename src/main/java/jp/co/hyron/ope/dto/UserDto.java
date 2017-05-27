package jp.co.hyron.ope.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import jp.co.hyron.ope.common.Role;
import jp.co.hyron.ope.entity.UserMst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date usrBth;

    @Length(min = 0, max = 11)
    private String usrMb;

    @NotEmpty
    private String usrNm;

    private short usrSex;

    private String usrId;

    private int id;

    private String usrTtl;

    private short status;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date epDt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date lfDt;

    private String spUsrId;

    private String jsgKb;

    private String usrDept;

    private Short pwdErrCnt = 0;

    private Role authorites;

    public UserDto(UserMst usr) {
        this.id = usr.getId();
        this.usrId = usr.getUsrId();
        this.usrBth = usr.getUsrBth();
        this.usrMb = usr.getUsrMb();
        this.usrNm = usr.getUsrNm();
        this.usrSex = usr.getUsrSex();
        this.jsgKb = usr.getJsgKb();
        this.epDt = usr.getEpDt();
        this.lfDt = usr.getLfDt();
        this.pwdErrCnt = usr.getPwdErrCnt();
        this.status = usr.getAcSts();
        this.spUsrId = usr.getSpUsrId();
    }
}
