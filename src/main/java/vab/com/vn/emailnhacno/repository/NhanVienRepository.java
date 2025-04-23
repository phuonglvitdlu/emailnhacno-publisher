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

import java.util.Optional;

/**
 *
 * @author LAMNTP
 */
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVienEntity, String> {
    @Query("select t.NV_EMAIL from NhanVienEntity t where t.NV_MA = :maCBB")
    String getEmailByMaCBB(@Param("maCBB") String maCBB);

    @Query("select t.NV_LDTRUCTIEP from NhanVienEntity t where t.NV_MA = :maQLTT")
    String getMAQLTT(@Param("maQLTT") String maQLTT);

    @Query("from NhanVienEntity n where n.NV_MA = :maNV")
    Optional<NhanVienEntity> getObjectByMaNV(@Param("maNV") String maNV);
}
