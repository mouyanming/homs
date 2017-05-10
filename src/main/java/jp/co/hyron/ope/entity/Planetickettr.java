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
 * The persistent class for the planetickettrs database table.
 */
@Entity
@Table(name = "planetickettrs")
@NamedQuery(name = "Planetickettr.findAll", query = "SELECT p FROM Planetickettr p")
public class Planetickettr implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pt_no", unique = true, nullable = false)
    private int ptNo;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Column(name = "fight_no", length = 10)
    private String fightNo;

    @Column(name = "from_ap", length = 20)
    private String fromAp;

    @Column(name = "pay_kb", length = 1)
    private String payKb;

    @Column(name = "pt_fee")
    private int ptFee;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_dt")
    private Date startDt;

    @Column(name = "to_ap", length = 20)
    private String toAp;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Column(name = "usr_id", nullable = false, length = 20)
    private String usrId;

    public Planetickettr() {
    }

    public int getPtNo() {
        return this.ptNo;
    }

    public void setPtNo(int ptNo) {
        this.ptNo = ptNo;
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

    public String getFightNo() {
        return this.fightNo;
    }

    public void setFightNo(String fightNo) {
        this.fightNo = fightNo;
    }

    public String getFromAp() {
        return this.fromAp;
    }

    public void setFromAp(String fromAp) {
        this.fromAp = fromAp;
    }

    public String getPayKb() {
        return this.payKb;
    }

    public void setPayKb(String payKb) {
        this.payKb = payKb;
    }

    public int getPtFee() {
        return this.ptFee;
    }

    public void setPtFee(int ptFee) {
        this.ptFee = ptFee;
    }

    public Date getStartDt() {
        return this.startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public String getToAp() {
        return this.toAp;
    }

    public void setToAp(String toAp) {
        this.toAp = toAp;
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

}