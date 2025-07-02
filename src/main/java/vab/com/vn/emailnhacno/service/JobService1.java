package vab.com.vn.emailnhacno.service;

import org.com.vab.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.EmailnhacnoApplication;
import vab.com.vn.emailnhacno.entity.*;
import vab.com.vn.emailnhacno.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class JobService1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService1.class);

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

    @Autowired
    private MailProperties mailProperties;

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

                String emailNdd = null;
                String emailKh = null;

                try {
                    emailNdd = kh.getEmailNdd();
                    emailKh = kh.getEmail();
                } catch (Exception e) {
                    LOGGER.error("Lỗi khi lấy email của KH {}: {}", kh.getMaKh(), e.getMessage());
                    return;
                }

                if ((emailNdd == null || emailNdd.trim().isEmpty()) &&
                        (emailKh == null || emailKh.trim().isEmpty())) {
                    LOGGER.warn("Khách hàng {} không có cả email chính và emailNdd → không gửi", kh.getMaKh());
                    return;
                }

                try {
                    EmailEntity email = new EmailEntity();
                    email.setFromEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
                    email.setRunDate(reportDate);

                    // Gán ToEmail
                    if (emailKh != null && !emailKh.trim().isEmpty()) {
                        email.setToEmail(emailKh);
                    } else {
                        email.setToEmail(emailNdd);
                        LOGGER.warn("Khách hàng {} không có email chính, dùng emailNdd để gửi", kh.getMaKh());
                    }

                    // Gán CC nếu có và khác với To
                    if (emailNdd != null && !emailNdd.trim().isEmpty() &&
                            !emailNdd.equalsIgnoreCase(email.getToEmail())) {
                        email.setToCC(new String[]{emailNdd});
                    }

                    email.setSubject(template.getTITLE());
                    email.setBody(templateService.getTemplateContactCenter(kh, templateOpt));
                    email.setCustomerNo(kh.getMaKh());
                    email.setComponent(component);
                    email.setName(kh.getTenKh());
                    email.setTieuDe(template.getTITLE());
                    email.setToCC(new String[]{EmailnhacnoApplication.getProperty("spring.mail.username")});

                    MailHistoryKey mailHistoryKey = new MailHistoryKey();
                    mailHistoryKey.setRUN_DATE(reportDate);
                    mailHistoryKey.setMA_NV(kh.getMaKh());
                    mailHistoryKey.setCOMPONENT(kh.getMaKh());
                    mailHistoryKey.setTIEU_DE(template.getTITLE());

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
