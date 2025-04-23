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

    //@Query(value = "select n from NhacNoVayEntity n  WHERE n.COMPONENT = :component and n.CUSTOMER_NO = :customer_no and n.CUSTOMER_NO = :customer_no\n", nativeQuery = true)
//List<KhachHangEntity> getDataByComponentAndCustomerNo(@Param("customer_no") String customer_no, @Param("component") String component);
    @Query("SELECT T.MA_CB_BAN\n" +
            "FROM NhacNoVayEntity T\n" +
            "WHERE T.MA_CB_BAN IS NOT NULL AND T.RUN_DATE =:RUN_DATE AND T.COMPONENT=:component group by ma_cb_ban\n")
    List<String> getMaCBB(@Param("RUN_DATE") String runDate, @Param("component") String component);


    @Query("SELECT T FROM NhacNoVayEntity T WHERE COMPONENT IN ('VAY_DEN_HAN','OD_DEN_HAN') AND T.MA_CB_BAN = :maCBB AND T.RUN_DATE =:RUN_DATE\n")
    List<NhacNoVayEntity> listKHVayDH(@Param("maCBB") String maCBB, @Param("RUN_DATE") String runDate);

    @Query("SELECT T FROM NhacNoVayEntity T WHERE T.COMPONENT = :component AND T.MA_CB_BAN = :maCBB AND T.RUN_DATE = :RUN_DATE")
    List<NhacNoVayEntity> listKHVayQH(@Param("component") String component,
                                      @Param("maCBB") String maCBB,
                                      @Param("RUN_DATE") String runDate);
}
