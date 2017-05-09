package jp.co.hyron.ope.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the vacationinf database table.
 */
@Embeddable
public class VacationinfPK implements Serializable {
    // default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "usr_id", unique = true, nullable = false, length = 20)
    private String usrId;

    @Column(name = "pv_year", unique = true, nullable = false, length = 4)
    private String pvYear;

    @Column(name = "pv_no", unique = true, nullable = false)
    private int pvNo;

    public VacationinfPK() {
    }

    public String getUsrId() {
        return this.usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getPvYear() {
        return this.pvYear;
    }

    public void setPvYear(String pvYear) {
        this.pvYear = pvYear;
    }

    public int getPvNo() {
        return this.pvNo;
    }

    public void setPvNo(int pvNo) {
        this.pvNo = pvNo;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VacationinfPK)) {
            return false;
        }
        VacationinfPK castOther = (VacationinfPK) other;
        return this.usrId.equals(castOther.usrId) && this.pvYear.equals(castOther.pvYear) && (this.pvNo == castOther.pvNo);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.usrId.hashCode();
        hash = hash * prime + this.pvYear.hashCode();
        hash = hash * prime + this.pvNo;

        return hash;
    }
}