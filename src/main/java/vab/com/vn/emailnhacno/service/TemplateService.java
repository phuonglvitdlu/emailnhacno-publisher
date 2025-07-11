package vab.com.vn.emailnhacno.service;

import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.entity.MailTemplate;
import vab.com.vn.emailnhacno.entity.VwEmailGtttEntity;

import java.util.Optional;

@Service
public class TemplateService {

    public String getTemplateContactCenter(VwEmailGtttEntity kh, Optional<MailTemplate> l_template) {

        if (kh.getLoaiKh().equals("I")) {
            return l_template.get().getCONTENT()
                    .replace("#hoten@", kh.getTenKh())
                    .replace("#sogiayto@", kh.getSoGttt())
                    .replace("#sotaikhoan@", kh.getSoTk())
                    .replace("#ngayhethan@", kh.getNgayHetHan())
                    .replace("#email@", kh.getEmail());

        }
        return l_template.get().getCONTENT()
                .replace("#hoten@", kh.getTenNdd())
                .replace("#sogiayto@", kh.getSoGtttNdd())
                .replace("#sotaikhoan@", kh.getSoTk())
                .replace("#ngayhethan@", kh.getNgayHetHanGtttNdd())
                .replace("#email@", kh.getEmail());

    }


}
