package jp.co.hyron.ope.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the traffictrs database table.
 */
@Embeddable
public class TraffictrPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "tf_ym", unique = true, nullable = false, length = 6)
    private String tfYm;

    @Column(name = "tfs_no", unique = true, nullable = false)
    private int tfsNo;

    public TraffictrPK() {
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getTfYm() {
        return this.tfYm;
    }

    public void setTfYm(String tfYm) {
        this.tfYm = tfYm;
    }

    public int getTfsNo() {
        return this.tfsNo;
    }

    public void setTfsNo(int tfsNo) {
        this.tfsNo = tfsNo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TraffictrPK)) {
            return false;
        }
        TraffictrPK castOther = (TraffictrPK) other;
        return this.usrId.equals(castOther.usrId) && this.tfYm.equals(castOther.tfYm) && (this.tfsNo == castOther.tfsNo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.usrId.hashCode();
        hash = hash * prime + this.tfYm.hashCode();
        hash = hash * prime + this.tfsNo;

        return hash;
    }
}