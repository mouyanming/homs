package jp.co.hyron.ope.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the paymenttrs database table.
 */
@Embeddable
public class PaymenttrPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "pmt_ym", unique = true, nullable = false, length = 6)
    private String pmtYm;

    @Column(name = "pmts_no", unique = true, nullable = false)
    private int pmtsNo;

    public PaymenttrPK() {
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getPmtYm() {
        return this.pmtYm;
    }

    public void setPmtYm(String pmtYm) {
        this.pmtYm = pmtYm;
    }

    public int getPmtsNo() {
        return this.pmtsNo;
    }

    public void setPmtsNo(int pmtsNo) {
        this.pmtsNo = pmtsNo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PaymenttrPK)) {
            return false;
        }
        PaymenttrPK castOther = (PaymenttrPK) other;
        return this.usrId.equals(castOther.usrId) && this.pmtYm.equals(castOther.pmtYm) && (this.pmtsNo == castOther.pmtsNo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.usrId.hashCode();
        hash = hash * prime + this.pmtYm.hashCode();
        hash = hash * prime + this.pmtsNo;

        return hash;
    }
}