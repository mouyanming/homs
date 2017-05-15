package jp.co.hyron.ope.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import jp.co.hyron.ope.entity.UserMst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto extends EmailDto{

	@DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date usrBth;

    private String usrMb;

    private String usrMl;

    @NotEmpty
    private String usrNm;

    private int usrSex;
    
    @NotEmpty
    @Length(min = 8, max = 20)
    private String password;
    
    public AccountDto(UserMst usr){
    	this.setEmail(usr.getId());
    	this.setUsrBth(usr.getUsrBth());
    	this.setUsrMb(usr.getUsrMb());
    	this.setUsrNm(usr.getUsrNm());
    	this.setUsrSex(usr.getUsrSex());
    }

}
