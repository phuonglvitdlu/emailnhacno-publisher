package vab.com.vn.emailnhacno.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DU_LIEU_VAY")
public class DuLieuVayEntity {
    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private String ACCOUNT_NUMBER;
    private String COMPONENT;
    private String SO_HOP_DONG_VAY;
    private String CURRENCY;
    private String CUSTOMER_NO;
    private String TEN_KHACH_HANG;
    private String NGAY_VAY;
    private String SO_TIEN_VAY;
    private String DU_NO_GOC_HIENTAI;
    private String NGAY_DEN_HAN;
    private String SO_NGAY_QUAHAN;
    private String NGAY_QH_GOC;
    private String NGAY_QH_LAI;
    private String GOC_PHAI_TRA;
    private String LAI_PHAI_TRA;
    private String TONG_PHAI_TRA;
    private String EMAIL_KH;
    private String MA_CB_BAN;
    private String RUN_DATE;

    public String getDU_NO_GOC_HIENTAI() {
        return DU_NO_GOC_HIENTAI;
    }

    public void setDU_NO_GOC_HIENTAI(String DU_NO_GOC_HIENTAI) {
        this.DU_NO_GOC_HIENTAI = DU_NO_GOC_HIENTAI;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT;
    }

    public String getSO_HOP_DONG_VAY() {
        return SO_HOP_DONG_VAY;
    }

    public void setSO_HOP_DONG_VAY(String SO_HOP_DONG_VAY) {
        this.SO_HOP_DONG_VAY = SO_HOP_DONG_VAY;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getCUSTOMER_NO() {
        return CUSTOMER_NO;
    }

    public void setCUSTOMER_NO(String CUSTOMER_NO) {
        this.CUSTOMER_NO = CUSTOMER_NO;
    }

    public String getTEN_KHACH_HANG() {
        return TEN_KHACH_HANG;
    }

    public void setTEN_KHACH_HANG(String TEN_KHACH_HANG) {
        this.TEN_KHACH_HANG = TEN_KHACH_HANG;
    }

    public String getNGAY_VAY() {
        return NGAY_VAY;
    }

    public void setNGAY_VAY(String NGAY_VAY) {
        this.NGAY_VAY = NGAY_VAY;
    }

    public String getSO_TIEN_VAY() {
        return SO_TIEN_VAY;
    }

    public void setSO_TIEN_VAY(String SO_TIEN_VAY) {
        this.SO_TIEN_VAY = SO_TIEN_VAY;
    }

    public String getNGAY_DEN_HAN() {
        return NGAY_DEN_HAN;
    }

    public void setNGAY_DEN_HAN(String NGAY_DEN_HAN) {
        this.NGAY_DEN_HAN = NGAY_DEN_HAN;
    }

    public String getSO_NGAY_QUAHAN() {
        return SO_NGAY_QUAHAN;
    }

    public void setSO_NGAY_QUAHAN(String SO_NGAY_QUAHAN) {
        this.SO_NGAY_QUAHAN = SO_NGAY_QUAHAN;
    }

    public String getNGAY_QH_GOC() {
        return NGAY_QH_GOC;
    }

    public void setNGAY_QH_GOC(String NGAY_QH_GOC) {
        this.NGAY_QH_GOC = NGAY_QH_GOC;
    }

    public String getNGAY_QH_LAI() {
        return NGAY_QH_LAI;
    }

    public void setNGAY_QH_LAI(String NGAY_QH_LAI) {
        this.NGAY_QH_LAI = NGAY_QH_LAI;
    }

    public String getGOC_PHAI_TRA() {
        return GOC_PHAI_TRA;
    }

    public void setGOC_PHAI_TRA(String GOC_PHAI_TRA) {
        this.GOC_PHAI_TRA = GOC_PHAI_TRA;
    }

    public String getLAI_PHAI_TRA() {
        return LAI_PHAI_TRA;
    }

    public void setLAI_PHAI_TRA(String LAI_PHAI_TRA) {
        this.LAI_PHAI_TRA = LAI_PHAI_TRA;
    }

    public String getTONG_PHAI_TRA() {
        return TONG_PHAI_TRA;
    }

    public void setTONG_PHAI_TRA(String TONG_PHAI_TRA) {
        this.TONG_PHAI_TRA = TONG_PHAI_TRA;
    }

    public String getEMAIL_KH() {
        return EMAIL_KH;
    }

    public void setEMAIL_KH(String EMAIL_KH) {
        this.EMAIL_KH = EMAIL_KH;
    }

    public String getMA_CB_BAN() {
        return MA_CB_BAN;
    }

    public void setMA_CB_BAN(String MA_CB_BAN) {
        this.MA_CB_BAN = MA_CB_BAN;
    }

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }

}
