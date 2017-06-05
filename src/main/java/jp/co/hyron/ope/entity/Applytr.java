package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jp.co.hyron.ope.dto.ApplyDto;

/**
 * The persistent class for the applytrs database table.
 */
@Entity
@Table(name = "applytrs")
@NamedQuery(name = "Applytr.findAll", query = "SELECT a FROM Applytr a")
public class Applytr implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "aps_no", unique = true, nullable = false)
    private int apsNo;

    @Column(name = "ap_cnt", length = 300)
    private String apCnt;

    @Column(name = "ap_kb", nullable = false, length = 1)
    private String apKb;

    @Column(name = "ap_let", length = 20)
    private String apLet;

    @Column(name = "ap_tm", nullable = false)
    private Timestamp apTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "dd_dt")
    private Date ddDt;

    @Column(name = "dl_cmt", length = 300)
    private String dlCmt;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Column(name = "usr_id", nullable = false, length = 20)
    private String usrId;

    public Applytr() {
    }

    public int getApsNo() {
        return this.apsNo;
    }

    public void setApsNo(int apsNo) {
        this.apsNo = apsNo;
    }

    public String getApCnt() {
        return this.apCnt;
    }

    public void setApCnt(String apCnt) {
        this.apCnt = apCnt;
    }

    public String getApKb() {
        return this.apKb;
    }

    public void setApKb(String apKb) {
        this.apKb = apKb;
    }

    public String getApLet() {
        return this.apLet;
    }

    public void setApLet(String apLet) {
        this.apLet = apLet;
    }

    public Timestamp getApTm() {
        return this.apTm;
    }

    public void setApTm(Timestamp apTm) {
        this.apTm = apTm;
    }

    public Date getDdDt() {
        return this.ddDt;
    }

    public void setDdDt(Date ddDt) {
        this.ddDt = ddDt;
    }

    public String getDlCmt() {
        return this.dlCmt;
    }

    public void setDlCmt(String dlCmt) {
        this.dlCmt = dlCmt;
    }

    public int getDlSts() {
        return this.dlSts;
    }

    public void setDlSts(int dlSts) {
        this.dlSts = dlSts;
    }

    public String getDlUsrId() {
        return this.dlUsrId;
    }

    public void setDlUsrId(String dlUsrId) {
        this.dlUsrId = dlUsrId;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
    
    public void convertToApply(ApplyDto dto) {
    	if (this.usrId == null || "".equals(this.usrId)) {
            this.usrId = dto.getUsrId();
        }
    	
    	if (dto.getApKb() != null && !"".equals(dto.getApKb())) {
            this.apKb = dto.getApKb();
        }
        
        if (this.apCnt != dto.getApCnt()) {
            this.apCnt = dto.getApCnt();
        }
        
        if (this.dlSts != dto.getDlSts()) {
            this.dlSts = dto.getDlSts();
        }else{
        	this.dlSts = 0;
        }
        
        this.ddDt = new Date();
        
        this.apTm = new Timestamp(System.currentTimeMillis());
        
        this.updTm = new Timestamp(System.currentTimeMillis());

    }

}