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
 * The persistent class for the traffictrs database table.
 */
@Entity
@Table(name = "traffictrs")
@NamedQuery(name = "Traffictr.findAll", query = "SELECT t FROM Traffictr t")
public class Traffictr implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TraffictrPK id;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Column(name = "from_sta", length = 20)
    private String fromSta;

    @Column(name = "ofu_flg")
    private int ofuFlg;

    @Temporal(TemporalType.DATE)
    @Column(name = "tf_dt")
    private Date tfDt;

    @Column(name = "tf_fee")
    private int tfFee;

    @Column(name = "tf_kb", length = 1)
    private String tfKb;

    @Column(name = "to_sta", length = 20)
    private String toSta;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    public Traffictr() {
    }

    public TraffictrPK getId() {
        return this.id;
    }

    public void setId(TraffictrPK id) {
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

    public String getFromSta() {
        return this.fromSta;
    }

    public void setFromSta(String fromSta) {
        this.fromSta = fromSta;
    }

    public int getOfuFlg() {
        return this.ofuFlg;
    }

    public void setOfuFlg(int ofuFlg) {
        this.ofuFlg = ofuFlg;
    }

    public Date getTfDt() {
        return this.tfDt;
    }

    public void setTfDt(Date tfDt) {
        this.tfDt = tfDt;
    }

    public int getTfFee() {
        return this.tfFee;
    }

    public void setTfFee(int tfFee) {
        this.tfFee = tfFee;
    }

    public String getTfKb() {
        return this.tfKb;
    }

    public void setTfKb(String tfKb) {
        this.tfKb = tfKb;
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