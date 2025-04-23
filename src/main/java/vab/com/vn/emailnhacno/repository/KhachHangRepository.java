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

    @Query("select n from KhachHangEntity n  WHERE n.COMPONENT IN ('OD_DEN_HAN','THAUCHI_DEN_HAN') and n.RUN_DATE =:RUN_DATE \n"
    )
    List<KhachHangEntity> getAllThauChiDH( @Param("RUN_DATE") String runDate);

    @Query("SELECT n FROM KhachHangEntity n WHERE n.COMPONENT IN (:COMPONENT) AND n.RUN_DATE = :RUN_DATE AND n.CUSTOMER_NO = :CUSTOMER_NO")
    List<KhachHangEntity> getThauChiByCustomerno(
            @Param("COMPONENT") List<String> COMPONENT,
            @Param("RUN_DATE") String RUN_DATE,
            @Param("CUSTOMER_NO") String CUSTOMER_NO
    );

    //    @Query(value = "SELECT \n" +
//            "    ACCOUNT_NUMBER,-- 0\n" +
//            "    ACCOUNT_BRANCH,-- 1\n" +
//            "    BRANCH_NAME,-- 2\n" +
//            "    CURRENCY,-- 3\n" +
//            "    SCHEDULE_DUE_DATE,--5\n" +
//            "    NGAY_DEN_HAN,--6\n" +
//            "    NGAY_VAY,--4\n" +
//            "    RUN_DATE,--7\n" +
//            "    CUSTOMER_NO,--8 \n" +
//            "    MA_CB_BAN, --9\n" +
//            "    CUST_EMAIL,-- 10\n" +
//            "    CUSTOMER_NAME,--11 \n" +
//            "    SO_HD_VAY, --12\n" +
//            "    SUM(GOC_PHAI_TRA) AS GOC_PHAI_TRA, --15\n" +
//            "    SUM(LAI_PHAI_TRA) AS LAI_PHAI_TRA,-- 16\n" +
//            "    DU_NO_GOC,--13\n" +
//            "    DU_NO_HIEN_TAI--14\n" +
//            "  \n" +
//            "FROM \n" +
//            "(\n" +
//            "    SELECT \n" +
//            "        T.CUSTOMER_NO, \n" +
//            "        T.USER_REF_NO AS SO_HD_VAY, \n" +
//            "        T.ACCOUNT_NUMBER, \n" +
//            "        T.LIMIT_START_DATE AS NGAY_VAY,\n" +
//            "        T.LIMIT_EXPIRE_DATE AS NGAY_DEN_HAN, \n" +
//            "        T.CUST_EMAIL, \n" +
//            "        T.CUSTOMER_NAME, \n" +
//            "        T.BRANCH_NAME, \n" +
//            "        T.RUN_DATE,\n" +
//            "        T.MA_CB_BAN, \n" +
//            "        T.ACCOUNT_BRANCH, \n" +
//            "        T.CURRENCY, \n" +
//            "        T.COMPONENT, \n" +
//            "        T.SCHEDULE_DUE_DATE,\n" +
//            "        T1.DU_NO_GOC,\n" +
//            "        T.DU_NO_HIEN_TAI,\n" +
//            "        \n" +
//            "               -- Sử dụng `CASE WHEN` cho `GOC_PHAI_TRA`\n" +
//            "        CASE \n" +
//            "            WHEN T.COMPONENT = 'I_T05_PRINCIPAL' THEN T1.AMOUNT_DUE \n" +
//            "            ELSE 0 \n" +
//            "        END AS GOC_PHAI_TRA,\n" +
//            "        \n" +
//            "        -- Sử dụng `CASE WHEN` cho `LAI_PHAI_TRA`\n" +
//            "        CASE \n" +
//            "            WHEN T.COMPONENT = 'I_T05_INTEREST' THEN T1.AMOUNT_DUE \n" +
//            "            ELSE 0 \n" +
//            "        END AS LAI_PHAI_TRA\n" +
//            "    FROM \n" +
//            "        N_KHACHHANG_NOTIEN t\n" +
//            "        ,N_KHACHHANG_NOTIEN T1\n" +
//            "    WHERE \n" +
//            "      t.COMPONENT IN ('I_T05_PRINCIPAL', 'I_T05_INTEREST')\n" +
//            "    AND T.RUN_DATE = TO_DATE('27-JUN-2023', 'DD-MON-YYYY') \n" +
//            "    AND T.ACCOUNT_NUMBER = T1.ACCOUNT_NUMBER\n" +
//            "    AND T.COMPONENT = T1.COMPONENT\n" +
//            "    AND T.RUN_DATE = T1.RUN_DATE    \n" +
//            " \n" +
//            "   -- and T.CUSTOMER_NO = '020039813'\n" +
//            ") A\n" +
//            "GROUP BY \n" +
//            "    CUSTOMER_NO, \n" +
//            "    SO_HD_VAY, \n" +
//            "    ACCOUNT_NUMBER, \n" +
//            "    NGAY_VAY, \n" +
//            "    NGAY_DEN_HAN,\n" +
//            "    CUST_EMAIL, \n" +
//            "    CUSTOMER_NAME, \n" +
//            "    BRANCH_NAME, \n" +
//            "    RUN_DATE, \n" +
//            "    MA_CB_BAN,\n" +
//            "    ACCOUNT_BRANCH, \n" +
//            "    CURRENCY, \n" +
//            "    SCHEDULE_DUE_DATE,\n" +
//            "    DU_NO_GOC,--15\n" +
//            "    DU_NO_HIEN_TAI--16\n" +
//            "ORDER BY \n" +
//            "    CUSTOMER_NO \n" +
//            "\n", nativeQuery = true)
//    List<Object> getAllDueDebt();
    @Query(value = "SELECT \n" +
            "    ACCOUNT_NUMBER,-- 0\n" +
            "    ACCOUNT_BRANCH,-- 1\n" +
            "    BRANCH_NAME,-- 2\n" +
            "    CURRENCY,-- 3\n" +
            "    SCHEDULE_DUE_DATE,--5\n" +
            "    NGAY_DEN_HAN,--6\n" +
            "    NGAY_VAY,--4\n" +
            "    RUN_DATE,--7\n" +
            "    CUSTOMER_NO,--8 \n" +
            "    MA_CB_BAN, --9\n" +
            "    CUST_EMAIL,-- 10\n" +
            "    CUSTOMER_NAME,--11 \n" +
            "    SO_HD_VAY, --12\n" +
            "    SUM(GOC_PHAI_TRA) AS GOC_PHAI_TRA, --15\n" +
            "    SUM(LAI_PHAI_TRA) AS LAI_PHAI_TRA,-- 16\n" +
            "    DU_NO_GOC,--13\n" +
            "    DU_NO_HIEN_TAI--14\n" +
            "  \n" +
            "FROM \n" +
            "(\n" +
            "    SELECT \n" +
            "        T.CUSTOMER_NO, \n" +
            "        T.USER_REF_NO AS SO_HD_VAY, \n" +
            "        T.ACCOUNT_NUMBER, \n" +
            "        T.LIMIT_START_DATE AS NGAY_VAY,\n" +
            "        T.LIMIT_EXPIRE_DATE AS NGAY_DEN_HAN, \n" +
            "        T.CUST_EMAIL, \n" +
            "        T.CUSTOMER_NAME, \n" +
            "        T.BRANCH_NAME, \n" +
            "        T.RUN_DATE,\n" +
            "        T.MA_CB_BAN, \n" +
            "        T.ACCOUNT_BRANCH, \n" +
            "        T.CURRENCY, \n" +
            "        T.COMPONENT, \n" +
            "        T.SCHEDULE_DUE_DATE,\n" +
            "        T1.DU_NO_GOC,\n" +
            "        T.DU_NO_HIEN_TAI,\n" +
            "        \n" +
            "               -- Sử dụng `CASE WHEN` cho `GOC_PHAI_TRA`\n" +
            "        CASE \n" +
            "            WHEN T.COMPONENT = 'I_T05_PRINCIPAL' THEN T1.AMOUNT_DUE \n" +
            "            ELSE 0 \n" +
            "        END AS GOC_PHAI_TRA,\n" +
            "        \n" +
            "        -- Sử dụng `CASE WHEN` cho `LAI_PHAI_TRA`\n" +
            "        CASE \n" +
            "            WHEN T.COMPONENT = 'I_T05_INTEREST' THEN T1.AMOUNT_DUE \n" +
            "            ELSE 0 \n" +
            "        END AS LAI_PHAI_TRA\n" +
            "    FROM \n" +
            "        N_KHACHHANG_NOTIEN t\n" +
            "        ,N_KHACHHANG_NOTIEN T1\n" +
            "    WHERE \n" +
            "    -- t.COMPONENT IN ('I_T05_PRINCIPAL', 'I_T05_INTEREST')\n" +
            " --   AND T.RUN_DATE = TO_DATE('27-JUN-2023', 'DD-MON-YYYY') \n" +
            "   -- AND\n" +
            "    T.ACCOUNT_NUMBER = T1.ACCOUNT_NUMBER\n" +
            "  --  AND T.COMPONENT = T1.COMPONENT\n" +
            "  --  AND T.RUN_DATE = T1.RUN_DATE    \n" +
            " \n" +
            "and\n" +
            "  T.CUSTOMER_NO = '321001523'\n" +
            ") A\n" +
            "GROUP BY \n" +
            "    CUSTOMER_NO, \n" +
            "    SO_HD_VAY, \n" +
            "    ACCOUNT_NUMBER, \n" +
            "    NGAY_VAY, \n" +
            "    NGAY_DEN_HAN,\n" +
            "    CUST_EMAIL, \n" +
            "    CUSTOMER_NAME, \n" +
            "    BRANCH_NAME, \n" +
            "    RUN_DATE, \n" +
            "    MA_CB_BAN,\n" +
            "    ACCOUNT_BRANCH, \n" +
            "    CURRENCY, \n" +
            "    SCHEDULE_DUE_DATE,\n" +
            "    DU_NO_GOC,--15\n" +
            "    DU_NO_HIEN_TAI--16\n" +
            "ORDER BY \n" +
            "    CUSTOMER_NO \n" +
            "\n", nativeQuery = true)
    List<Object> getAllDueDebt();

//    @Query(value ="SELECT \n" +
//            "    ACCOUNT_NUMBER,-- 0\n" +
//            "    ACCOUNT_BRANCH,-- 1\n" +
//            "    BRANCH_NAME,-- 2\n" +
//            "    CURRENCY,-- 3\n" +
//            "    SCHEDULE_DUE_DATE,--5\n" +
//            "    NGAY_DEN_HAN,--6\n" +
//            "    NGAY_VAY,--4\n" +
//            "    RUN_DATE,--7\n" +
//            "    CUSTOMER_NO,--8 \n" +
//            "    MA_CB_BAN, --9\n" +
//            "    CUST_EMAIL,-- 10\n" +
//            "    CUSTOMER_NAME,--11 \n" +
//            "    SO_HD_VAY, --12\n" +
//            "    SUM(GOC_PHAI_TRA) AS GOC_PHAI_TRA, --15\n" +
//            "    SUM(LAI_PHAI_TRA) AS LAI_PHAI_TRA,-- 16\n" +
//            "    DU_NO_GOC,--13\n" +
//            "    DU_NO_HIEN_TAI--14\n" +
//            "  \n" +
//            "FROM \n" +
//            "(\n" +
//            "    SELECT \n" +
//            "        T.CUSTOMER_NO, \n" +
//            "        T.USER_REF_NO AS SO_HD_VAY, \n" +
//            "        T.ACCOUNT_NUMBER, \n" +
//            "        T.LIMIT_START_DATE AS NGAY_VAY,\n" +
//            "        T.LIMIT_EXPIRE_DATE AS NGAY_DEN_HAN, \n" +
//            "        T.CUST_EMAIL, \n" +
//            "        T.CUSTOMER_NAME, \n" +
//            "        T.BRANCH_NAME, \n" +
//            "        T.RUN_DATE,\n" +
//            "        T.MA_CB_BAN, \n" +
//            "        T.ACCOUNT_BRANCH, \n" +
//            "        T.CURRENCY, \n" +
//            "        T.COMPONENT, \n" +
//            "        T.SCHEDULE_DUE_DATE,\n" +
//            "        T1.DU_NO_GOC,\n" +
//            "        T.DU_NO_HIEN_TAI,\n" +
//            "        \n" +
//            "               -- Sử dụng `CASE WHEN` cho `GOC_PHAI_TRA`\n" +
//            "        CASE \n" +
//            "            WHEN T.COMPONENT = 'I_T05_PRINCIPAL' THEN T1.AMOUNT_DUE \n" +
//            "            ELSE 0 \n" +
//            "        END AS GOC_PHAI_TRA,\n" +
//            "        \n" +
//            "        -- Sử dụng `CASE WHEN` cho `LAI_PHAI_TRA`\n" +
//            "        CASE \n" +
//            "            WHEN T.COMPONENT = 'I_T05_INTEREST' THEN T1.AMOUNT_DUE \n" +
//            "            ELSE 0 \n" +
//            "        END AS LAI_PHAI_TRA\n" +
//            "    FROM \n" +
//            "        N_KHACHHANG_NOTIEN t\n" +
//            "        ,N_KHACHHANG_NOTIEN T1\n" +
//            "    WHERE \n" +
//            "      t.COMPONENT IN ('I_T05_PRINCIPAL', 'I_T05_INTEREST')\n" +
//            "    AND T.RUN_DATE = TO_DATE('27-JUN-2023', 'DD-MON-YYYY') \n" +
//            "    AND T.ACCOUNT_NUMBER = T1.ACCOUNT_NUMBER\n" +
//            "    AND T.COMPONENT = T1.COMPONENT\n" +
//            "    AND T.RUN_DATE = T1.RUN_DATE    \n" +
//            " \n" +
//            "    and T.CUSTOMER_NO = '321001523'\n" +
//            ") A\n" +
//            "GROUP BY \n" +
//            "    CUSTOMER_NO, \n" +
//            "    SO_HD_VAY, \n" +
//            "    ACCOUNT_NUMBER, \n" +
//            "    NGAY_VAY, \n" +
//            "    NGAY_DEN_HAN,\n" +
//            "    CUST_EMAIL, \n" +
//            "    CUSTOMER_NAME, \n" +
//            "    BRANCH_NAME, \n" +
//            "    RUN_DATE, \n" +
//            "    MA_CB_BAN,\n" +
//            "    ACCOUNT_BRANCH, \n" +
//            "    CURRENCY, \n" +
//            "    SCHEDULE_DUE_DATE,\n" +
//            "    DU_NO_GOC,--15\n" +
//            "    DU_NO_HIEN_TAI--16\n" +
//            "ORDER BY \n" +
//            "    CUSTOMER_NO \n" +
//            "\n", nativeQuery = true)
//    List<Object> getDueDebtGroupByCustomerNo();


    @Query(value = "SELECT \n" +
            "    ACCOUNT_NUMBER,-- 0\n" +
            "    ACCOUNT_BRANCH,-- 1\n" +
            "    BRANCH_NAME,-- 2\n" +
            "    CURRENCY,-- 3\n" +
            "    SCHEDULE_DUE_DATE,--5\n" +
            "    NGAY_DEN_HAN,--6\n" +
            "    NGAY_VAY,--4\n" +
            "    RUN_DATE,--7\n" +
            "    CUSTOMER_NO,--8 \n" +
            "    MA_CB_BAN, --9\n" +
            "    CUST_EMAIL,-- 10\n" +
            "    CUSTOMER_NAME,--11 \n" +
            "    SO_HD_VAY, --12\n" +
            "    SUM(GOC_PHAI_TRA) AS GOC_PHAI_TRA, --15\n" +
            "    SUM(LAI_PHAI_TRA) AS LAI_PHAI_TRA,-- 16\n" +
            "    DU_NO_GOC,--13\n" +
            "    DU_NO_HIEN_TAI--14\n" +
            "  \n" +
            "FROM \n" +
            "(\n" +
            "    SELECT \n" +
            "        T.CUSTOMER_NO, \n" +
            "        T.USER_REF_NO AS SO_HD_VAY, \n" +
            "        T.ACCOUNT_NUMBER, \n" +
            "        T.LIMIT_START_DATE AS NGAY_VAY,\n" +
            "        T.LIMIT_EXPIRE_DATE AS NGAY_DEN_HAN, \n" +
            "        T.CUST_EMAIL, \n" +
            "        T.CUSTOMER_NAME, \n" +
            "        T.BRANCH_NAME, \n" +
            "        T.RUN_DATE,\n" +
            "        T.MA_CB_BAN, \n" +
            "        T.ACCOUNT_BRANCH, \n" +
            "        T.CURRENCY, \n" +
            "        T.COMPONENT, \n" +
            "        T.SCHEDULE_DUE_DATE,\n" +
            "        T1.DU_NO_GOC,\n" +
            "        T.DU_NO_HIEN_TAI,\n" +
            "        \n" +
            "               -- Sử dụng `CASE WHEN` cho `GOC_PHAI_TRA`\n" +
            "        CASE \n" +
            "            WHEN T.COMPONENT = 'I_T05_PRINCIPAL' THEN T1.AMOUNT_DUE \n" +
            "            ELSE 0 \n" +
            "        END AS GOC_PHAI_TRA,\n" +
            "        \n" +
            "        -- Sử dụng `CASE WHEN` cho `LAI_PHAI_TRA`\n" +
            "        CASE \n" +
            "            WHEN T.COMPONENT = 'I_T05_INTEREST' THEN T1.AMOUNT_DUE \n" +
            "            ELSE 0 \n" +
            "        END AS LAI_PHAI_TRA\n" +
            "    FROM \n" +
            "        N_KHACHHANG_NOTIEN t\n" +
            "        ,N_KHACHHANG_NOTIEN T1\n" +
            "    WHERE \n" +
            "    -- t.COMPONENT IN ('I_T05_PRINCIPAL', 'I_T05_INTEREST')\n" +
            " --   AND T.RUN_DATE = TO_DATE('27-JUN-2023', 'DD-MON-YYYY') \n" +
            "   -- AND\n" +
            "    T.ACCOUNT_NUMBER = T1.ACCOUNT_NUMBER\n" +
            "  --  AND T.COMPONENT = T1.COMPONENT\n" +
            "  --  AND T.RUN_DATE = T1.RUN_DATE    \n" +
            " \n" +
            "and\n" +
            "  T.CUSTOMER_NO = :customer_no\n" +
            ") A\n" +
            "GROUP BY \n" +
            "    CUSTOMER_NO, \n" +
            "    SO_HD_VAY, \n" +
            "    ACCOUNT_NUMBER, \n" +
            "    NGAY_VAY, \n" +
            "    NGAY_DEN_HAN,\n" +
            "    CUST_EMAIL, \n" +
            "    CUSTOMER_NAME, \n" +
            "    BRANCH_NAME, \n" +
            "    RUN_DATE, \n" +
            "    MA_CB_BAN,\n" +
            "    ACCOUNT_BRANCH, \n" +
            "    CURRENCY, \n" +
            "    SCHEDULE_DUE_DATE,\n" +
            "    DU_NO_GOC,--15\n" +
            "    DU_NO_HIEN_TAI--16\n" +
            "ORDER BY \n" +
            "    CUSTOMER_NO \n" +
            "\n", nativeQuery = true)
    List<Object> getDueDebtGroupByCustomerNo(String customer_no);
}

