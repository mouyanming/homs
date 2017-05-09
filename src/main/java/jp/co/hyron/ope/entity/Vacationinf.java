package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the vacationinf database table.
 */
@Entity
@Table(name = "vacationinf")
@NamedQuery(name = "Vacationinf.findAll", query = "SELECT v FROM Vacationinf v")
public class Vacationinf implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private VacationinfPK id;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Temporal(TemporalType.DATE)
    @Column(name = "pv_dt")
    private Date pvDt;

    @Column(name = "pv_kb", length = 1)
    private String pvKb;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    public Vacationinf() {
    }

    public VacationinfPK getId() {
        return this.id;
    }

    public void setId(VacationinfPK id) {
        this.id = id;
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

    public Date getPvDt() {
        return this.pvDt;
    }

    public void setPvDt(Date pvDt) {
        this.pvDt = pvDt;
    }

    public String getPvKb() {
        return this.pvKb;
    }

    public void setPvKb(String pvKb) {
        this.pvKb = pvKb;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

}