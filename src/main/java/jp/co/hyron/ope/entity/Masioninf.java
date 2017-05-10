package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the masioninf database table.
 */
@Entity
@Table(name = "masioninf")
@NamedQuery(name = "Masioninf.findAll", query = "SELECT m FROM Masioninf m")
public class Masioninf implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_dt")
    private Date endDt;

    @Column(name = "masion_ad", length = 100)
    private String masionAd;

    @Column(name = "masion_pn", length = 100)
    private String masionPn;

    @Column(name = "mgr_cp", length = 100)
    private String mgrCp;

    @Column(name = "mgr_tel", length = 20)
    private String mgrTel;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_dt")
    private Date startDt;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    public Masioninf() {
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public Timestamp getCrtTm() {
        return this.crtTm;
    }

    public void setCrtTm(Timestamp crtTm) {
        this.crtTm = crtTm;
    }

    public Date getEndDt() {
        return this.endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getMasionAd() {
        return this.masionAd;
    }

    public void setMasionAd(String masionAd) {
        this.masionAd = masionAd;
    }

    public String getMasionPn() {
        return this.masionPn;
    }

    public void setMasionPn(String masionPn) {
        this.masionPn = masionPn;
    }

    public String getMgrCp() {
        return this.mgrCp;
    }

    public void setMgrCp(String mgrCp) {
        this.mgrCp = mgrCp;
    }

    public String getMgrTel() {
        return this.mgrTel;
    }

    public void setMgrTel(String mgrTel) {
        this.mgrTel = mgrTel;
    }

    public Date getStartDt() {
        return this.startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

}