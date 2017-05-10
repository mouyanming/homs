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
 * The persistent class for the paymenttrs database table.
 */
@Entity
@Table(name = "paymenttrs")
@NamedQuery(name = "Paymenttr.findAll", query = "SELECT p FROM Paymenttr p")
public class Paymenttr implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PaymenttrPK id;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Column(name = "pmt_cnt", length = 100)
    private String pmtCnt;

    @Temporal(TemporalType.DATE)
    @Column(name = "pmt_dt")
    private Date pmtDt;

    @Column(name = "pmt_fee")
    private int pmtFee;

    @Column(name = "pmt_let", length = 20)
    private String pmtLet;

    @Column(name = "pmt_tgt", length = 100)
    private String pmtTgt;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    public Paymenttr() {
    }

    public PaymenttrPK getId() {
        return this.id;
    }

    public void setId(PaymenttrPK id) {
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

    public String getPmtCnt() {
        return this.pmtCnt;
    }

    public void setPmtCnt(String pmtCnt) {
        this.pmtCnt = pmtCnt;
    }

    public Date getPmtDt() {
        return this.pmtDt;
    }

    public void setPmtDt(Date pmtDt) {
        this.pmtDt = pmtDt;
    }

    public int getPmtFee() {
        return this.pmtFee;
    }

    public void setPmtFee(int pmtFee) {
        this.pmtFee = pmtFee;
    }

    public String getPmtLet() {
        return this.pmtLet;
    }

    public void setPmtLet(String pmtLet) {
        this.pmtLet = pmtLet;
    }

    public String getPmtTgt() {
        return this.pmtTgt;
    }

    public void setPmtTgt(String pmtTgt) {
        this.pmtTgt = pmtTgt;
    }

    public Timestamp getUpdTm() {
        return this.updTm;
    }

    public void setUpdTm(Timestamp updTm) {
        this.updTm = updTm;
    }

}