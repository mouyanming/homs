package jp.co.hyron.ope.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the commutationtrs database table.
 */
@Embeddable
public class CommutationtrPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "ct_ym", unique = true, nullable = false, length = 6)
    private String ctYm;

    @Column(name = "ct_no", unique = true, nullable = false)
    private int ctNo;

    public CommutationtrPK() {
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getCtYm() {
        return this.ctYm;
    }

    public void setCtYm(String ctYm) {
        this.ctYm = ctYm;
    }

    public int getCtNo() {
        return this.ctNo;
    }

    public void setCtNo(int ctNo) {
        this.ctNo = ctNo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CommutationtrPK)) {
            return false;
        }
        CommutationtrPK castOther = (CommutationtrPK) other;
        return this.usrId.equals(castOther.usrId) && this.ctYm.equals(castOther.ctYm) && (this.ctNo == castOther.ctNo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.usrId.hashCode();
        hash = hash * prime + this.ctYm.hashCode();
        hash = hash * prime + this.ctNo;

        return hash;
    }
}