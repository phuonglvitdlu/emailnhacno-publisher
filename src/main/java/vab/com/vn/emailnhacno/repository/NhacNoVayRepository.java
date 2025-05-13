package vab.com.vn.emailnhacno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vab.com.vn.emailnhacno.entity.NhacNoVayEntity;

import java.util.List;

public interface NhacNoVayRepository extends JpaRepository<NhacNoVayEntity, String> {

    @Query(value = "SELECT * FROM (SELECT n.*, ROW_NUMBER() OVER (PARTITION BY n.CUSTOMER_NO ORDER BY n.ACCOUNT_NUMBER ASC) AS row_num FROM Nhac_No_Vay n WHERE n.COMPONENT = :component AND n.RUN_DATE = :runDate) sub WHERE sub.row_num = 1", nativeQuery = true)
    List<NhacNoVayEntity> getDataByComponent(@Param("component") String component, @Param("runDate") String runDate);

    @Query(value = "select n from NhacNoVayEntity n  WHERE n.COMPONENT = :component and n.CUSTOMER_NO = :customer_no and n.CUSTOMER_NO = :customer_no and n.RUN_DATE =:RUN_DATE\n")
    List<NhacNoVayEntity> getDataByComponentAndCustomerNo(@Param("component") String component, @Param("customer_no") String customer_no, @Param("RUN_DATE") String runDate);

    @Query("SELECT T.MA_CB_BAN\n" +
            "FROM NhacNoVayEntity T\n" +
            "WHERE T.MA_CB_BAN IS NOT NULL AND T.RUN_DATE =:RUN_DATE AND T.COMPONENT=:component group by ma_cb_ban\n")
    List<String> getMaCBB(@Param("RUN_DATE") String runDate, @Param("component") String component);


    @Query(
            value = "SELECT NV_EMAIL FROM  V_EMAIL_NHAC_NO_DS_NV@DBLINK_QLNSTL\n" +
                    " WHERE CD_MA IN ('CV1056','CV1091','CV2058','CV2098','QL2009','QL1009','QL1047','CV3023',\n" +
                    "'CV3019','CV3016','CV3006','CV3004')\n" +
                    "and substr(dv_ma,1, 3)=:branch",
            nativeQuery = true
    )
    List<String> getListCBBByBranch(@Param("branch") String branch);


    @Query("SELECT T FROM NhacNoVayEntity T WHERE COMPONENT IN ('VAY_DEN_HAN','OD_DEN_HAN') AND T.MA_CB_BAN = :maCBB AND T.RUN_DATE =:RUN_DATE\n")
    List<NhacNoVayEntity> listKHVayDH(@Param("maCBB") String maCBB, @Param("RUN_DATE") String runDate);

    @Query("SELECT T FROM NhacNoVayEntity T WHERE T.COMPONENT = :component AND T.MA_CB_BAN = :maCBB AND T.RUN_DATE = :RUN_DATE")
    List<NhacNoVayEntity> listKHVayQH(@Param("component") String component,
                                      @Param("maCBB") String maCBB,
                                      @Param("RUN_DATE") String runDate);
}
