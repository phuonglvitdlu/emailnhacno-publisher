package vab.com.vn.emailnhacno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vab.com.vn.emailnhacno.entity.KhachHangEntity;

import java.util.List;


@Repository
public interface KhachHangRepository extends JpaRepository<KhachHangEntity, String> {
    @Query(value = "SELECT * FROM (SELECT n.*, ROW_NUMBER() OVER (PARTITION BY n.CUSTOMER_NO ORDER BY n.ACCOUNT_NUMBER ASC) AS row_num FROM N_KHACHHANG_NOTIEN n WHERE n.COMPONENT IN ('OD_DEN_HAN', 'THAUCHI_DEN_HAN') AND n.RUN_DATE = :runDate) sub WHERE sub.row_num = 1", nativeQuery = true)
    List<KhachHangEntity> getDistinctByCustomerNoODDenHan(@Param("runDate") String runDate);

    @Query(value = "SELECT * FROM (SELECT n.*, ROW_NUMBER() OVER (PARTITION BY n.CUSTOMER_NO ORDER BY n.ACCOUNT_NUMBER ASC) AS row_num FROM N_KHACHHANG_NOTIEN n WHERE n.COMPONENT IN ('OD_QUA_HAN') AND n.RUN_DATE = :runDate) sub WHERE sub.row_num = 1", nativeQuery = true)
    List<KhachHangEntity> getDistinctByCustomerNoODQuaHan(@Param("runDate") String runDate);

    @Query(value = "SELECT * FROM (SELECT n.*, ROW_NUMBER() OVER (PARTITION BY n.CUSTOMER_NO ORDER BY n.ACCOUNT_NUMBER ASC) AS row_num FROM N_KHACHHANG_NOTIEN n WHERE n.COMPONENT IN ('OD_HET_HAN') AND n.RUN_DATE = :runDate) sub WHERE sub.row_num = 1", nativeQuery = true)
    List<KhachHangEntity> getDistinctByCustomerNoODHetHan(@Param("runDate") String runDate);


    @Query("SELECT n FROM KhachHangEntity n WHERE n.COMPONENT IN (:COMPONENT) AND n.RUN_DATE = :RUN_DATE AND n.CUSTOMER_NO = :CUSTOMER_NO")
    List<KhachHangEntity> getThauChiByCustomerno(
            @Param("COMPONENT") List<String> COMPONENT,
            @Param("RUN_DATE") String RUN_DATE,
            @Param("CUSTOMER_NO") String CUSTOMER_NO
    );

}

