package jp.co.hyron.ope.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the bbstrs database table.
 */
@Entity
@Table(name = "bbstrs")
@NamedQuery(name = "Bbstr.findAll", query = "SELECT b FROM Bbstr b")
public class Bbstr implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bbs_no", unique = true, nullable = false)
    private int bbsNo;

    @Column(name = "bbs_cnt", length = 200)
    private String bbsCnt;

    @Column(name = "bbs_ttl", length = 40)
    private String bbsTtl;

    @Column(name = "crt_tm", nullable = false)
    private Timestamp crtTm;

    @Column(name = "dl_sts")
    private int dlSts;

    @Column(name = "dl_usr_id", length = 20)
    private String dlUsrId;

    @Column(name = "p_bbs_no")
    private int pBbsNo;

    @Column(name = "upd_tm", nullable = false)
    private Timestamp updTm;

    @Column(name = "usr_id", nullable = false, length = 20)
    private String usrId;

    public Bbstr() {
    }

    public int getBbsNo() {
        return this.bbsNo;
    }

    public void setBbsNo(int bbsNo) {
        this.bbsNo = bbsNo;
    }

    public String getBbsCnt() {
        return this.bbsCnt;
    }

    public void setBbsCnt(String bbsCnt) {
        this.bbsCnt = bbsCnt;
    }

    public String getBbsTtl() {
        return this.bbsTtl;
    }

    public void setBbsTtl(String bbsTtl) {
        this.bbsTtl = bbsTtl;
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

    public int getPBbsNo() {
        return this.pBbsNo;
    }

    public void setPBbsNo(int pBbsNo) {
        this.pBbsNo = pBbsNo;
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