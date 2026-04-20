/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vab.com.vn.emailnhacno.entity.NhanVienEntity;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author LAMNTP
 */
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVienEntity, String> {

    @Query("from NhanVienEntity n where n.NV_MA = :maNV")
    Optional<NhanVienEntity> getObjectByMaNV(@Param("maNV") String maNV);

    @Query("SELECT n.NV_EMAIL  FROM NhanVienEntity n " +
            "WHERE n.DV_MA LIKE  CONCAT(:dvMa, '%') " +
            "AND n.NV_NGAYNGHI IS NULL " +
            "AND n.CD_MA IN ('QL1047','QL2009','QL2018','QL2027','QL1009')")
    List<String> findEmailNhanVienByDvMa(@Param("dvMa") String dvMa);
}
