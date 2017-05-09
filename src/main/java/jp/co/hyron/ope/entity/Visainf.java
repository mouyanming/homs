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
 * The persistent class for the visainf database table.
 */
@Entity
@Table(name = "visainf")
@NamedQuery(name = "Visainf.findAll", query = "SELECT v FROM Visainf v")
public class Visainf implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Temporal(TemporalType.DATE)
    @Column(name = "pp_ed")
    private Date ppEd;

    @Column(name = "pp_nmb", length = 20)
    private String ppNmb;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Column(name = "usr_nm_en", length = 40)
    private String usrNmEn;

    @Temporal(TemporalType.DATE)
    @Column(name = "visa_ed")
    private Date visaEd;

    @Column(name = "visa_kb", length = 2)
    private String visaKb;

    public Visainf() {
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

    public Date getPpEd() {
        return this.ppEd;
    }

    public void setPpEd(Date ppEd) {
        this.ppEd = ppEd;
    }

    public String getPpNmb() {
        return this.ppNmb;
    }

    public void setPpNmb(String ppNmb) {
        this.ppNmb = ppNmb;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

    public String getUsrNmEn() {
        return this.usrNmEn;
    }

    public void setUsrNmEn(String usrNmEn) {
        this.usrNmEn = usrNmEn;
    }

    public Date getVisaEd() {
        return this.visaEd;
    }

    public void setVisaEd(Date visaEd) {
        this.visaEd = visaEd;
    }

    public String getVisaKb() {
        return this.visaKb;
    }

    public void setVisaKb(String visaKb) {
        this.visaKb = visaKb;
    }

}