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
 * The persistent class for the commutationtrs database table.
 */
@Entity
@Table(name = "commutationtrs")
@NamedQuery(name = "Commutationtr.findAll", query = "SELECT c FROM Commutationtr c")
public class Commutationtr implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CommutationtrPK id;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "ct_fee")
    private int ctFee;

    @Column(name = "ct_kb", length = 1)
    private String ctKb;

    @Column(name = "ct_let", length = 20)
    private String ctLet;

    @Column(name = "ct_tm_kb", length = 1)
    private String ctTmKb;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_dt")
    private Date endDt;

    @Column(name = "from_sta", length = 20)
    private String fromSta;

    @Column(name = "mid_sta", length = 20)
    private String midSta;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_dt")
    private Date startDt;

    @Column(name = "to_sta", length = 20)
    private String toSta;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    public Commutationtr() {
    }

    public CommutationtrPK getId() {
        return this.id;
    }

    public void setId(CommutationtrPK id) {
        this.id = id;
    }

    public Timestamp getCrtTm() {
        return this.crtTm;
    }

    public void setCrtTm(Timestamp crtTm) {
        this.crtTm = crtTm;
    }

    public int getCtFee() {
        return this.ctFee;
    }

    public void setCtFee(int ctFee) {
        this.ctFee = ctFee;
    }

    public String getCtKb() {
        return this.ctKb;
    }

    public void setCtKb(String ctKb) {
        this.ctKb = ctKb;
    }

    public String getCtLet() {
        return this.ctLet;
    }

    public void setCtLet(String ctLet) {
        this.ctLet = ctLet;
    }

    public String getCtTmKb() {
        return this.ctTmKb;
    }

    public void setCtTmKb(String ctTmKb) {
        this.ctTmKb = ctTmKb;
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

    public Date getEndDt() {
        return this.endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public String getFromSta() {
        return this.fromSta;
    }

    public void setFromSta(String fromSta) {
        this.fromSta = fromSta;
    }

    public String getMidSta() {
        return this.midSta;
    }

    public void setMidSta(String midSta) {
        this.midSta = midSta;
    }

    public Date getStartDt() {
        return this.startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getToSta() {
        return this.toSta;
    }

    public void setToSta(String toSta) {
        this.toSta = toSta;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

}