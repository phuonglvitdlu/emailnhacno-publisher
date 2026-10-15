
package vab.com.vn.emailnhacno.entity;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MailHistoryKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "MA_NV")
    private String MA_NV;

    @Column(name = "TIEU_DE")
    private String TIEU_DE;

    @Column(name = "COMPONENT")
    private String COMPONENT;

    @Column(name = "RUN_DATE")
    private String RUN_DATE;

    @Column(name = "EMAIL_TYPE")
    private String EMAIL_TYPE;

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

    public String getRUN_DATE() {
        return RUN_DATE;
    }

    public void setRUN_DATE(String RUN_DATE) {
        this.RUN_DATE = RUN_DATE;
    }

    public String getEMAIL_TYPE() {
        return EMAIL_TYPE;
    }

    public void setEMAIL_TYPE(String EMAIL_TYPE) {
        this.EMAIL_TYPE = EMAIL_TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MailHistoryKey)) {
            return false;
        }
        MailHistoryKey that = (MailHistoryKey) o;
        return Objects.equals(MA_NV, that.MA_NV)
                && Objects.equals(TIEU_DE, that.TIEU_DE)
                && Objects.equals(COMPONENT, that.COMPONENT)
                && Objects.equals(RUN_DATE, that.RUN_DATE)
                && Objects.equals(EMAIL_TYPE, that.EMAIL_TYPE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MA_NV, TIEU_DE, COMPONENT, RUN_DATE, EMAIL_TYPE);
    }
}
