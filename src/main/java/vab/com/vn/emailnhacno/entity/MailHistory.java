/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author LAMNTP
 */
@Entity
@Table(name = "N_MAIL_HISTORY")
public class MailHistory implements Serializable {

    @EmbeddedId
    private MailHistoryKey PK;
    private String HO_TEN;
    private LocalDateTime THOI_GIAN;
//    @Column(name = "EMAIL", insertable = false, updatable = false)
//    private String EMAIL;
    private String TRANG_THAI;

    /**
     * @return the HO_TEN
     */
    public String getHO_TEN() {
        return HO_TEN;
    }

    /**
     * @param HO_TEN the HO_TEN to set
     */
    public void setHO_TEN(String HO_TEN) {
        this.HO_TEN = HO_TEN;
    }

    /**
     * @return the THOI_GIAN
     */
    public LocalDateTime getTHOI_GIAN() {
        return THOI_GIAN;
    }

    /**
     * @param THOI_GIAN the THOI_GIAN to set
     */
    public void setTHOI_GIAN(LocalDateTime THOI_GIAN) {
        this.THOI_GIAN = THOI_GIAN;
    }

//    /**
//     * @return the EMAIL
//     */
//    public String getEMAIL() {
//        return EMAIL;
//    }
//
//    /**
//     * @param EMAIL the EMAIL to set
//     */
//    public void setEMAIL(String EMAIL) {
//        this.EMAIL = EMAIL;
//    }

    /**
     * @return the TRANG_THAI
     */
    public String getTRANG_THAI() {
        return TRANG_THAI;
    }

    /**
     * @param TRANG_THAI the TRANG_THAI to set
     */
    public void setTRANG_THAI(String TRANG_THAI) {
        this.TRANG_THAI = TRANG_THAI;
    }

    /**
     * @return the PK
     */
    public MailHistoryKey getPK() {
        return PK;
    }

    /**
     * @param PK the PK to set
     */
    public void setPK(MailHistoryKey PK) {
        this.PK = PK;
    }

}
