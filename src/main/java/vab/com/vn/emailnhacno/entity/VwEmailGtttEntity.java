package vab.com.vn.emailnhacno.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VW_EMAIL_GTTT")
public class VwEmailGtttEntity {

    @Id
    @Column(name = "E_MAIL")
    private String email;

    @Column(name = "DON_VI")
    private String donVi;

    @Column(name = "MA_KH")
    private String maKh;

    @Column(name = "LOAI_KH")
    private String loaiKh;

    @Column(name = "TEN_KH")
    private String tenKh;

    @Column(name = "SO_TK")
    private String soTk;

    @Column(name = "SO_GTTT")
    private String soGttt;

    @Column(name = "NGAY_HET_HAN")
    private String ngayHetHan;

    @Column(name = "CIF_NDD")
    private String cifNdd;

    @Column(name = "TEN_NDD")
    private String tenNdd;

    @Column(name = "SO_GTTT_NDD")
    private String soGtttNdd;

    @Column(name = "NGAY_HET_HAN_GTTT_NDD")
    private String ngayHetHanGtttNdd;

    @Column(name = "NGAY_CAP")
    private String NGAY_CAP;



    @Column(name = "LOAI_GTTT_NDD")
    private String LOAI_GTTT_NDD;

    public String getNGAY_CAP() {
        return NGAY_CAP;
    }

    public void setNGAY_CAP(String NGAY_CAP) {
        this.NGAY_CAP = NGAY_CAP;
    }


    public String getLOAI_GTTT_NDD() {
        return LOAI_GTTT_NDD;
    }

    public void setLOAI_GTTT_NDD(String LOAI_GTTT_NDD) {
        this.LOAI_GTTT_NDD = LOAI_GTTT_NDD;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getLoaiKh() {
        return loaiKh;
    }

    public void setLoaiKh(String loaiKh) {
        this.loaiKh = loaiKh;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getSoTk() {
        return soTk;
    }

    public void setSoTk(String soTk) {
        this.soTk = soTk;
    }

    public String getSoGttt() {
        return soGttt;
    }

    public void setSoGttt(String soGttt) {
        this.soGttt = soGttt;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getCifNdd() {
        return cifNdd;
    }

    public void setCifNdd(String cifNdd) {
        this.cifNdd = cifNdd;
    }

    public String getTenNdd() {
        return tenNdd;
    }

    public void setTenNdd(String tenNdd) {
        this.tenNdd = tenNdd;
    }

    public String getSoGtttNdd() {
        return soGtttNdd;
    }

    public void setSoGtttNdd(String soGtttNdd) {
        this.soGtttNdd = soGtttNdd;
    }

    public String getNgayHetHanGtttNdd() {
        return ngayHetHanGtttNdd;
    }

    public void setNgayHetHanGtttNdd(String ngayHetHanGtttNdd) {
        this.ngayHetHanGtttNdd = ngayHetHanGtttNdd;
    }


}
