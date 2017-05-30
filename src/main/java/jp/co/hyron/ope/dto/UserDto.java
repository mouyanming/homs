package jp.co.hyron.ope.dto;

import java.util.Date;

import jp.co.hyron.ope.common.Affiliation;
import jp.co.hyron.ope.common.Department;
import jp.co.hyron.ope.common.Position;
import jp.co.hyron.ope.common.Role;
import jp.co.hyron.ope.common.Status;
import jp.co.hyron.ope.entity.UserMst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

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

    private Position usrTtl = Position.OTHER;

    private Status status;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date epDt;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date lfDt;

    private String spUsrId;

    private Affiliation jsgKb = Affiliation.NO;

    private Department usrDept;

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
        this.usrTtl = usr.getUsrTtl();
        this.usrDept = usr.getUsrDept();
    }
}
