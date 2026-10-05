package vab.com.vn.emailnhacno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name = "N_EMAIL_NHAC_NO_DMDVI")
public class EmailNhacNoDmDviEntity {


    @Id
    @Column(name = "DV_MA")
    private String dvMa;

    @Column(name = "DV_TEN")
    private String dvTen;

    @Column(name = "CN_MA")
    private String cnMa;

    @Column(name = "DV_MALD")
    private String dvMald;

    @Column(name = "DV_TENLD")
    private String dvTenld;

    @Column(name = "EMAIL_LD")
    private String emailLd;

    @Column(name = "TINHTRANGSD")
    private Integer tinhTrangSd;

    public String getDvMa() {
        return dvMa;
    }

    public void setDvMa(String dvMa) {
        this.dvMa = dvMa;
    }

    public String getDvTen() {
        return dvTen;
    }

    public void setDvTen(String dvTen) {
        this.dvTen = dvTen;
    }

    public String getCnMa() {
        return cnMa;
    }

    public void setCnMa(String cnMa) {
        this.cnMa = cnMa;
    }

    public String getDvMald() {
        return dvMald;
    }

    public void setDvMald(String dvMald) {
        this.dvMald = dvMald;
    }

    public String getDvTenld() {
        return dvTenld;
    }

    public void setDvTenld(String dvTenld) {
        this.dvTenld = dvTenld;
    }

    public String getEmailLd() {
        return emailLd;
    }

    public void setEmailLd(String emailLd) {
        this.emailLd = emailLd;
    }

    public Integer getTinhTrangSd() {
        return tinhTrangSd;
    }

    public void setTinhTrangSd(Integer tinhTrangSd) {
        this.tinhTrangSd = tinhTrangSd;
    }


}