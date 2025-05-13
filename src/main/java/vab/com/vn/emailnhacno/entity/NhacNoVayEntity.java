package vab.com.vn.emailnhacno.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "NHAC_NO_VAY")
public class NhacNoVayEntity {
    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private String ACCOUNT_NUMBER;
    private String COMPONENT;
    private String ACCOUNT_BRANCH;
    private String BRANCH_NAME;
    private String SO_HOP_DONG_VAY;
    private String CURRENCY;
    private String CUSTOMER_NO;
    private String TEN_KHACH_HANG;

    private String RM_PHUTRACH;

    private String NGAY_VAY;
    private BigDecimal SO_TIEN_VAY;
    private BigDecimal DU_NO_GOC_HIENTAI;
    private String NGAY_DEN_HAN;
    private String SO_NGAY_QUAHAN;
    private String NGAY_QH_GOC;
    private String NGAY_QH_LAI;
    private String NGAY_DEN_HAN_THANHTOAN;
    private BigDecimal GOC_PHAI_TRA;
    private BigDecimal LAI_PHAI_TRA;
    private String EMAIL_KH;
    private String RUN_DATE;
    private BigDecimal TONG_PHAI_TRA;
    private String  TAIKHOAN_TRICH_NO;
    private String  MA_CB_BAN;

    public String getMA_CB_BAN() {
        return MA_CB_BAN;
    }

    public void setMA_CB_BAN(String MA_CB_BAN) {
        this.MA_CB_BAN = MA_CB_BAN;
    }


    public String getTAIKHOAN_TRICH_NO() {
        return TAIKHOAN_TRICH_NO;
    }

    public void setTAIKHOAN_TRICH_NO(String TAIKHOAN_TRICH_NO) {
        this.TAIKHOAN_TRICH_NO = TAIKHOAN_TRICH_NO;
    }


    public String getRM_PHUTRACH() {
        return RM_PHUTRACH;
    }

    public void setRM_PHUTRACH(String RM_PHUTRACH) {
        this.RM_PHUTRACH = RM_PHUTRACH;
    }

    public BigDecimal getTONG_PHAI_TRA() {
        return TONG_PHAI_TRA;
    }

    public void setTONG_PHAI_TRA(BigDecimal TONG_PHAI_TRA) {
        this.TONG_PHAI_TRA = TONG_PHAI_TRA;
    }

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }

    public String getEMAIL_KH() {
        return EMAIL_KH;
    }

    public void setEMAIL_KH(String EMAIL_KH) {
        this.EMAIL_KH = EMAIL_KH;
    }

    public BigDecimal getLAI_PHAI_TRA() {
        return LAI_PHAI_TRA;
    }

    public void setLAI_PHAI_TRA(BigDecimal LAI_PHAI_TRA) {
        this.LAI_PHAI_TRA = LAI_PHAI_TRA;
    }

    public BigDecimal getGOC_PHAI_TRA() {
        return GOC_PHAI_TRA;
    }

    public void setGOC_PHAI_TRA(BigDecimal GOC_PHAI_TRA) {
        this.GOC_PHAI_TRA = GOC_PHAI_TRA;
    }

    public String getNGAY_DEN_HAN_THANHTOAN() {
        return NGAY_DEN_HAN_THANHTOAN;
    }

    public void setNGAY_DEN_HAN_THANHTOAN(String NGAY_DEN_HAN_THANHTOAN) {
        this.NGAY_DEN_HAN_THANHTOAN = NGAY_DEN_HAN_THANHTOAN;
    }

    public String getNGAY_QH_LAI() {
        return NGAY_QH_LAI;
    }

    public void setNGAY_QH_LAI(String NGAY_QH_LAI) {
        this.NGAY_QH_LAI = NGAY_QH_LAI;
    }

    public String getNGAY_QH_GOC() {
        return NGAY_QH_GOC;
    }

    public void setNGAY_QH_GOC(String NGAY_QH_GOC) {
        this.NGAY_QH_GOC = NGAY_QH_GOC;
    }

    public String getSO_NGAY_QUAHAN() {
        return SO_NGAY_QUAHAN;
    }

    public void setSO_NGAY_QUAHAN(String SO_NGAY_QUAHAN) {
        this.SO_NGAY_QUAHAN = SO_NGAY_QUAHAN;
    }

    public String getNGAY_DEN_HAN() {
        return NGAY_DEN_HAN;
    }

    public void setNGAY_DEN_HAN(String NGAY_DEN_HAN) {
        this.NGAY_DEN_HAN = NGAY_DEN_HAN;
    }

    public BigDecimal getDU_NO_GOC_HIENTAI() {
        return DU_NO_GOC_HIENTAI;
    }

    public void setDU_NO_GOC_HIENTAI(BigDecimal DU_NO_GOC_HIENTAI) {
        this.DU_NO_GOC_HIENTAI = DU_NO_GOC_HIENTAI;
    }

    public BigDecimal getSO_TIEN_VAY() {
        return SO_TIEN_VAY;
    }

    public void setSO_TIEN_VAY(BigDecimal SO_TIEN_VAY) {
        this.SO_TIEN_VAY = SO_TIEN_VAY;
    }

    public String getNGAY_VAY() {
        return NGAY_VAY;
    }

    public void setNGAY_VAY(String NGAY_VAY) {
        this.NGAY_VAY = NGAY_VAY;
    }

    public String getTEN_KHACH_HANG() {
        return TEN_KHACH_HANG;
    }

    public void setTEN_KHACH_HANG(String TEN_KHACH_HANG) {
        this.TEN_KHACH_HANG = TEN_KHACH_HANG;
    }

    public String getCUSTOMER_NO() {
        return CUSTOMER_NO;
    }

    public void setCUSTOMER_NO(String CUSTOMER_NO) {
        this.CUSTOMER_NO = CUSTOMER_NO;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getSO_HOP_DONG_VAY() {
        return SO_HOP_DONG_VAY;
    }

    public void setSO_HOP_DONG_VAY(String SO_HOP_DONG_VAY) {
        this.SO_HOP_DONG_VAY = SO_HOP_DONG_VAY;
    }

    public String getBRANCH_NAME() {
        return BRANCH_NAME;
    }

    public void setBRANCH_NAME(String BRANCH_NAME) {
        this.BRANCH_NAME = BRANCH_NAME;
    }

    public String getACCOUNT_BRANCH() {
        return ACCOUNT_BRANCH;
    }

    public void setACCOUNT_BRANCH(String ACCOUNT_BRANCH) {
        this.ACCOUNT_BRANCH = ACCOUNT_BRANCH;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }






}
