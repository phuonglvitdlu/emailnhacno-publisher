package vab.com.vn.emailnhacno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;



@Entity
@Table(name = "N_KHACHHANG_NOTIEN")
public class KhachHangEntity implements Serializable {


    @Id
    @Column(name = "ACCOUNT_NUMBER")
    private String ACCOUNT_NUMBER;

    public String getACCOUNT_BRANCH() {
        return ACCOUNT_BRANCH;
    }

    public void setACCOUNT_BRANCH(String ACCOUNT_BRANCH) {
        this.ACCOUNT_BRANCH = ACCOUNT_BRANCH;
    }

    private String ACCOUNT_BRANCH;
    private String CUSTOMER_NO;
    private String CURRENCY;
    private String COMPONENT;
    private String SCHEDULE_DUE_DATE;
    private String LIMIT_EXPIRE_DATE;
    private String LIMIT_START_DATE;
    private BigDecimal AMOUNT_DUE;
    private BigDecimal TOT_AMT;
    private String PHONE_NO;
    private String USER_REF_NO;
    private String RUN_DATE;
    private String CUST_EMAIL;
    private String CUSTOMER_NAME;
    private String BRANCH_NAME;
    private String MA_CB_BAN;
    private BigDecimal DU_NO_HIEN_TAI;
    private BigDecimal DU_NO_GOC;
    private BigDecimal GOC_PHAI_TRA;
    private BigDecimal LAI_PHAI_TRA;
    private BigDecimal SO_TIEN_VAY;



    public String getCUSTOMER_NO() {
        return CUSTOMER_NO;
    }

    public void setCUSTOMER_NO(String CUSTOMER_NO) {
        this.CUSTOMER_NO = CUSTOMER_NO;
    }

    public BigDecimal getLAI_PHAI_TRA() {
        return LAI_PHAI_TRA;
    }

    public void setLAI_PHAI_TRA(BigDecimal LAI_PHAI_TRA) {
        this.LAI_PHAI_TRA = LAI_PHAI_TRA;
    }


    public BigDecimal getSO_TIEN_VAY() {
        return SO_TIEN_VAY;
    }

    public void setSO_TIEN_VAY(BigDecimal SO_TIEN_VAY) {
        this.SO_TIEN_VAY = SO_TIEN_VAY;
    }

    public BigDecimal getGOC_PHAI_TRA() {
        return GOC_PHAI_TRA;
    }

    public void setGOC_PHAI_TRA(BigDecimal GOC_PHAI_TRA) {
        this.GOC_PHAI_TRA = GOC_PHAI_TRA;
    }

    public BigDecimal getDU_NO_HIEN_TAI() {
        return DU_NO_HIEN_TAI;
    }

    public void setDU_NO_HIEN_TAI(BigDecimal DU_NO_HIEN_TAI) {
        this.DU_NO_HIEN_TAI = DU_NO_HIEN_TAI;
    }

    public BigDecimal getDU_NO_GOC() {
        return DU_NO_GOC;
    }

    public void setDU_NO_GOC(BigDecimal DU_NO_GOC) {
        this.DU_NO_GOC = DU_NO_GOC;
    }


    public String getMA_CB_BAN() {
        return MA_CB_BAN;
    }

    public void setMA_CB_BAN(String MA_CB_BAN) {
        this.MA_CB_BAN = MA_CB_BAN;
    }

    public String getLIMIT_START_DATE() {
        return LIMIT_START_DATE;
    }

    public void setLIMIT_START_DATE(String LIMIT_START_DATE) {
        this.LIMIT_START_DATE = LIMIT_START_DATE;
    }

    public String getLIMIT_EXPIRE_DATE() {
        return LIMIT_EXPIRE_DATE;
    }

    public void setLIMIT_EXPIRE_DATE(String LIMIT_EXPIRE_DATE) {
        this.LIMIT_EXPIRE_DATE = LIMIT_EXPIRE_DATE;
    }

    public String getBRANCH_NAME() {
        return BRANCH_NAME;
    }

    public void setBRANCH_NAME(String BRANCH_NAME) {
        this.BRANCH_NAME = BRANCH_NAME;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }


    public String getCUST_EMAIL() {
        return CUST_EMAIL;
    }

    public void setCUST_EMAIL(String CUST_EMAIL) {
        this.CUST_EMAIL = CUST_EMAIL;
    }

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }

    public String getUSER_REF_NO() {
        return USER_REF_NO;
    }

    public void setUSER_REF_NO(String USER_REF_NO) {
        this.USER_REF_NO = USER_REF_NO;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public BigDecimal getTOT_AMT() {
        return TOT_AMT;
    }

    public void setTOT_AMT(BigDecimal TOT_AMT) {
        this.TOT_AMT = TOT_AMT;
    }

    public BigDecimal getAMOUNT_DUE() {
        return AMOUNT_DUE;
    }

    public void setAMOUNT_DUE(BigDecimal AMOUNT_DUE) {
        this.AMOUNT_DUE = AMOUNT_DUE;
    }

    public String getSCHEDULE_DUE_DATE() {
        return SCHEDULE_DUE_DATE;
    }

    public void setSCHEDULE_DUE_DATE(String SCHEDULE_DUE_DATE) {
        this.SCHEDULE_DUE_DATE = SCHEDULE_DUE_DATE;
    }

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT;
    }

    public String getCURRENCY() {
        return CURRENCY;
    }

    public void setCURRENCY(String CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }


}
