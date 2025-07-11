/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;



@Embeddable
public class MailHistoryKey implements Serializable {

    @Column(name = "MA_NV")
    private String MA_NV;

    @Column(name = "TIEU_DE")
    private String TIEU_DE;

    @Column(name = "COMPONENT")
    private String COMPONENT;

    @Column(name = "RUN_DATE")
    private String RUN_DATE;

    @Column(name = "EMAIL")
    private String EMAIL;

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }



    public String getMA_NV() {
        return MA_NV;
    }

    public void setMA_NV(String MA_NV) {
        this.MA_NV = MA_NV;
    }

    public String getTIEU_DE() {
        return TIEU_DE;
    }

    public void setTIEU_DE(String TIEU_DE) {
        this.TIEU_DE = TIEU_DE;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT;
    }



}
