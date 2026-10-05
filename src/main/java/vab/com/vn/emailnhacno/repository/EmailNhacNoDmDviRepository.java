package vab.com.vn.emailnhacno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vab.com.vn.emailnhacno.entity.EmailNhacNoDmDviEntity;

public interface EmailNhacNoDmDviRepository
        extends JpaRepository<EmailNhacNoDmDviEntity, String> {

    EmailNhacNoDmDviEntity findByDvMa(String dvMa);
}