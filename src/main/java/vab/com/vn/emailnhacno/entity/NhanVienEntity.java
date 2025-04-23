/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LAMNTP
 */
@Entity
@Table(name = "N_NHANVIEN")
public class NhanVienEntity implements Serializable {

    private String ND_MA;
    @Id
    @Column(name = "NV_MA")
    private String NV_MA;
    private String NV_HO_TEN;

    private String DV_MA;
    private String DV_TEN;
    private String CD_MA;
    private String CD_TEN;
    private String NV_EMAIL;
    private String NV_NGAYVAO;
    private String NV_NGAYNGHI;
    private String DV_TEN2;
    private String NV_LDTRUCTIEP;

    public String getND_MA() {
        return ND_MA;
    }

    public void setND_MA(String ND_MA) {
        this.ND_MA = ND_MA;
    }

    public String getNV_MA() {
        return NV_MA;
    }

    public void setNV_MA(String NV_MA) {
        this.NV_MA = NV_MA;
    }

    public String getNV_HO_TEN() {
        return NV_HO_TEN;
    }

    public void setNV_HO_TEN(String NV_HO_TEN) {
        this.NV_HO_TEN = NV_HO_TEN;
    }

    public String getDV_MA() {
        return DV_MA;
    }

    public void setDV_MA(String DV_MA) {
        this.DV_MA = DV_MA;
    }

    public String getDV_TEN() {
        return DV_TEN;
    }

    public void setDV_TEN(String DV_TEN) {
        this.DV_TEN = DV_TEN;
    }

    public String getCD_MA() {
        return CD_MA;
    }

    public void setCD_MA(String CD_MA) {
        this.CD_MA = CD_MA;
    }

    public String getCD_TEN() {
        return CD_TEN;
    }

    public void setCD_TEN(String CD_TEN) {
        this.CD_TEN = CD_TEN;
    }

    public String getNV_EMAIL() {
        return NV_EMAIL;
    }

    public void setNV_EMAIL(String NV_EMAIL) {
        this.NV_EMAIL = NV_EMAIL;
    }

    public String getNV_NGAYVAO() {
        return NV_NGAYVAO;
    }

    public void setNV_NGAYVAO(String NV_NGAYVAO) {
        this.NV_NGAYVAO = NV_NGAYVAO;
    }

    public String getNV_NGAYNGHI() {
        return NV_NGAYNGHI;
    }

    public void setNV_NGAYNGHI(String NV_NGAYNGHI) {
        this.NV_NGAYNGHI = NV_NGAYNGHI;
    }

    public String getDV_TEN2() {
        return DV_TEN2;
    }

    public void setDV_TEN2(String DV_TEN2) {
        this.DV_TEN2 = DV_TEN2;
    }

    public String getNV_LDTRUCTIEP() {
        return NV_LDTRUCTIEP;
    }

    public void setNV_LDTRUCTIEP(String NV_LDTRUCTIEP) {
        this.NV_LDTRUCTIEP = NV_LDTRUCTIEP;
    }


}
