package vab.com.vn.emailnhacno.service;

import org.com.vab.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.EmailnhacnoApplication;
import vab.com.vn.emailnhacno.entity.*;
import vab.com.vn.emailnhacno.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private ContactCenterRepository repository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private MailTemplateRepository mailTemplateRepo;

    @Autowired
    private MailHistoryRepository mailHistoryRepo;

    @Autowired
    private TemplateService templateService;

    public void executeService(String reportDate) {
        LOGGER.info("CONTACT-CENTER Start processing emails for date: {}", reportDate);

        List<MailTemplate> templates = mailTemplateRepo.findAll();
        if (templates.isEmpty()) {
            LOGGER.warn("Không có template nào được cấu hình.");
            return;
        }

        String[] components = {"CONTACT_CENTER"};

        for (String component : components) {
            LOGGER.info("Processing component: {}", component);

            if (!component.equals("CONTACT_CENTER")) continue;

            List<VwEmailGtttEntity> getAll = repository.findAll();
            if (getAll.isEmpty()) {
                LOGGER.warn("Không có dữ liệu nhắc nợ cho component: {}", component);
                continue;
            }

            Optional<MailTemplate> templateOpt = mailTemplateRepo.findById(component);
            if (!templateOpt.isPresent()) {
                LOGGER.warn("Không tìm thấy template cho component: {}", component);
                continue;
            }

            MailTemplate template = templateOpt.get();

            getAll.forEach(kh -> {
                if (kh == null) {
                    LOGGER.warn("Dữ liệu khách hàng null");
                    return;
                }

                String emailKh = null;

                try {
                    emailKh = kh.getEmail();
                } catch (Exception e) {
                    LOGGER.error("Lỗi khi lấy email của KH {}: {}", kh.getMaKh(), e.getMessage());
                    return;
                }


                try {
                    EmailEntity email = new EmailEntity();
                    email.setFromEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
                    email.setRunDate(reportDate);

                    // Gán ToEmail
//                    if (kh.getLoaiKh().equals("I")) {
                    email.setToEmail(emailKh);
//                    }

                    // Gán CC nếu có và khác với To
//                    if (emailNdd != null && !emailNdd.trim().isEmpty() &&
//                            !emailNdd.equalsIgnoreCase(email.getToEmail())) {
//                        email.setToCC(new String[]{emailNdd});
//                    }

                    email.setSubject(template.getTITLE());
                    email.setBody(templateService.getTemplateContactCenter(kh, templateOpt));
                    email.setCustomerNo(kh.getMaKh());
                    email.setComponent(component);
                    email.setName(kh.getTenKh());
                    email.setDonvi(kh.getDonVi());
                    email.setLoaiKH(kh.getLoaiKh());
                    email.setNgaycap(kh.getNGAY_CAP());
                    email.setCifndd(kh.getCifNdd());
                    email.setLoaigttndd(kh.getLOAI_GTTT_NDD());

                    if (kh.getLoaiKh().equals("I")) {
                        email.setNgayhethl(kh.getNgayHetHan());
                        email.setSoGTTT(kh.getSoGttt());
                        email.setLoaiKH(kh.getLoaiKh());
                    } else {
                        email.setNgayhethl(kh.getNgayHetHanGtttNdd());
                        email.setSoGTTT(kh.getSoGtttNdd());
                        email.setLoaiKH(kh.getLoaiKh());
                    }


                    email.setTieuDe(template.getTITLE());
                    email.setToCC(new String[]{EmailnhacnoApplication.getProperty("spring.mail.username")});

                    MailHistoryKey mailHistoryKey = new MailHistoryKey();
                    mailHistoryKey.setRUN_DATE(reportDate);
                    mailHistoryKey.setMA_NV(kh.getMaKh());
                    mailHistoryKey.setCOMPONENT("CONTACT_CENTER");
                    mailHistoryKey.setTIEU_DE(template.getTITLE());
                    mailHistoryKey.setEMAIL(kh.getEmail());

                    if (!mailHistoryRepo.existsById(mailHistoryKey)) {
                        producerService.sendEmail(email);
                        LOGGER.info("Đã gửi email cho KH: {}", kh.getMaKh());
                    } else {
                        LOGGER.info("Email đã được gửi trước đó cho khách hàng: {}", kh.getMaKh());
                    }
                } catch (Exception e) {
                    LOGGER.error("Lỗi khi xử lý email cho KH {}: {}", kh.getMaKh(), e.getMessage(), e);
                }
            });

            LOGGER.info("CONTACT-CENTER Hoàn thành xử lý email cho ngày: {}", reportDate);
        }
    }
}
