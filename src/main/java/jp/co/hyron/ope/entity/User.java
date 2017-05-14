package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jp.co.hyron.ope.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 本システム用 のユーザ情報
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = -7254273334291876188L;

	@Id
	@Column(name = "id")
	protected String id;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private Login login;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "ep_dt")
    private Date epDt;

    @Column(name = "jsg_kb", length = 1)
    private String jsgKb;

    @Temporal(TemporalType.DATE)
    @Column(name = "lf_dt")
    private Date lfDt;

    @Column(name = "sp_usr_id", length = 20)
    private String spUsrId;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "usr_bth")
    private Date usrBth;

    @Column(name = "usr_mb", length = 15)
    private String usrMb;

    @Column(name = "usr_ml", length = 30)
    private String usrMl;

    @Column(name = "usr_nm", length = 30)
    private String usrNm;

    @Column(name = "usr_sex")
    private int usrSex;

    @Column(name = "usr_ttl", length = 20)
    private String usrTtl;

    public void convertToUser(UserDto dto, boolean isAdmin) {
        // 自分更新不可の項目
        if (isAdmin) {
            if (this.usrNm != dto.getUsrNm()) {
                this.usrNm = dto.getUsrNm();
            }
            if (this.usrSex != dto.getUsrSex()) {
                this.usrSex = dto.getUsrSex();
            }
            if (this.usrMl != dto.getUsrMl()) {
                this.usrMl = dto.getUsrMl();
            }
            if (this.spUsrId != dto.getSpUsrId()) {
                this.spUsrId = dto.getSpUsrId();
            }
            if (this.jsgKb != dto.getJsgKb()) {
                this.jsgKb = dto.getJsgKb();
            }
            if (this.epDt != dto.getEpDt()) {
                this.epDt = dto.getEpDt();
            }
            if (this.lfDt != dto.getLfDt()) {
                this.lfDt = dto.getLfDt();
            }
            if (this.usrTtl != dto.getUsrTtl()) {
                this.usrTtl = dto.getUsrTtl();
            }
        }
        if (this.usrBth != dto.getUsrBth()) {
            this.usrBth = dto.getUsrBth();
        }
        if (this.usrMb != dto.getUsrMb()) {
            this.usrMb = dto.getUsrMb();
        }
        this.updTm = new Timestamp(System.currentTimeMillis());
    }
    
    public User(String usrId){
    	this.id = usrId;
    }
}
