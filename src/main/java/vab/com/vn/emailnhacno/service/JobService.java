package vab.com.vn.emailnhacno.service;

import org.com.vab.entity.EmailEntity;
import org.com.vab.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.EmailnhacnoApplication;
import vab.com.vn.emailnhacno.entity.KhachHangEntity;
import vab.com.vn.emailnhacno.entity.MailHistoryKey;
import vab.com.vn.emailnhacno.entity.MailTemplate;
import vab.com.vn.emailnhacno.entity.NhacNoVayEntity;
import vab.com.vn.emailnhacno.repository.*;
import vab.com.vn.emailnhacno.service.canbo.ThauChiHHService;
import vab.com.vn.emailnhacno.service.canbo.ThauChiQHService;
import vab.com.vn.emailnhacno.service.canbo.VayQHService;
import vab.com.vn.emailnhacno.service.canbo.VayDHService;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private SenderService senderService;

    @Autowired
    private ProducerService producerService;
    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private MailTemplateRepository mailTemplateRepo;

    @Autowired
    private NhacNoVayRepository nhacNoVayRepository;

    @Autowired
    private DuLieuVayRepository duLieuVayRepository;

    @Autowired
    private MailHistoryRepository mailHistoryRepo;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private NhanVienRepository nhanVienRepo;

    public void executeService(String reportDate) {
        LOGGER.info("Start processing emails for date: {}", reportDate);
        processCBBList(reportDate);
        // Lấy danh sách template
        List<MailTemplate> templates = mailTemplateRepo.findAll();
        if (templates.isEmpty()) {
            LOGGER.warn("Không có template nào được cấu hình.");
            return;
        }

//        String[] components = {"OD_DEN_HAN", "VAY_DEN_HAN", "OD_HET_HAN", "OD_QUA_HAN", "VAY_QUA_HAN"};
//
//        for (String component : components) {
//            LOGGER.info("Processing component: {}", component);
//            if (!component.equals("OD_DEN_HAN") && !component.equals("OD_QUA_HAN") && !component.equals("OD_HET_HAN")) {
//                List<NhacNoVayEntity> nhacNoVayList = nhacNoVayRepository.getDataByComponent(component, reportDate);
//                if (nhacNoVayList.isEmpty()) {
//                    LOGGER.warn("Không có dữ liệu nhắc nợ cho component: {}", component);
//                    continue;
//                }
//
//                Optional<MailTemplate> templateOpt = mailTemplateRepo.findById(component);
//
//                if (!templateOpt.isPresent()) {
//                    LOGGER.warn("Không tìm thấy template cho component: {}", component);
//                    continue;
//                }
//
//                // Gửi email cho từng khách hàng
//                MailTemplate template = templateOpt.get();
//                MailHistoryKey mailHistoryKey = new MailHistoryKey();
//                EmailEntity email = new EmailEntity();
//                email.setFromEmail(mailProperties.getUsername());
//
//                nhacNoVayList.forEach(kh -> {
//                    if (kh.getEMAIL_KH() == null || kh.getEMAIL_KH().isEmpty()) {
//                        LOGGER.warn("Khách hàng không có email: {}", kh.getACCOUNT_NUMBER());
//                        return;
//                    }
//                    email.setRunDate(reportDate);
////                    email.setToEmail(kh.getEMAIL_KH());
//                    email.setToEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
//                    email.setToCC(new String[]{kh.getEMAIL_KH()});
//                    email.setSubject(template.getTITLE());
//                    email.setBody(templateService.getTemplate(kh, templateOpt, component, 0, reportDate));
//                    email.setCustomerNo(kh.getCUSTOMER_NO());
//                    email.setComponent(component);
//                    email.setName(kh.getTEN_KHACH_HANG());
//                    email.setTieuDe(template.getTITLE());
//
//                    mailHistoryKey.setRUN_DATE(reportDate);
//                    mailHistoryKey.setMA_NV(kh.getCUSTOMER_NO());
//                    mailHistoryKey.setCOMPONENT(kh.getCOMPONENT());
//                    mailHistoryKey.setTIEU_DE(template.getTITLE());
//
//                    if (!mailHistoryRepo.existsById(mailHistoryKey)) {
//                        producerService.sendEmail(email);
//
//                    } else {
//                        LOGGER.info("Email đã được gửi trước đó cho khách hàng: {}", kh.getACCOUNT_NUMBER());
//                    }
//                });
//
//            } else {
//
//                List<KhachHangEntity> listKH;
//                if (component.equals("OD_DEN_HAN")) {
//                    // Lấy danh sách khách hàng Thấu chi đến hạn
//                    listKH = khachHangRepo.getDistinctByCustomerNoODDenHan(reportDate);
//                    if (listKH.isEmpty()) {
//                        LOGGER.warn("Không có dữ liệu Thấu chi đến hạn cho ngày: {}", reportDate);
//                        continue;
//                    }
//                } else if (component.equals("OD_QUA_HAN")) {
//                    // Lấy danh sách khách hàng Thấu chi quá hạn
//                    listKH = khachHangRepo.getDistinctByCustomerNoODQuaHan(reportDate);
//                    if (listKH.isEmpty()) {
//                        LOGGER.warn("Không có dữ liệu Thấu chi quá hạn cho ngày: {}", reportDate);
//                        continue;
//                    }
//                } else {
//                    // Lấy danh sách khách hàng Thấu chi quá hạn
//                    listKH = khachHangRepo.getDistinctByCustomerNoODHetHan(reportDate);
//                    if (listKH.isEmpty()) {
//                        LOGGER.warn("Không có dữ liệu Thấu chi hết hạn cho ngày: {}", reportDate);
//                        continue;
//                    }
//                }
//
//                Optional<MailTemplate> templateOpt;
//                // Lấy template
//                if (component.equals("OD_QUA_HAN")) {
//                    templateOpt = mailTemplateRepo.findById("THAUCHIQH");
//                } else if (component.equals("OD_DEN_HAN")) {
//                    templateOpt = mailTemplateRepo.findById("THAUCHI");
//                } else {
//                    templateOpt = mailTemplateRepo.findById("THAUCHIHH");
//                }
//                if (!templateOpt.isPresent()) {
//                    LOGGER.warn("Không tìm thấy template cho component: {}", component);
//                    continue;
//                }
//
//                MailTemplate template = templateOpt.get();
//                MailHistoryKey mailHistoryKey = new MailHistoryKey();
//                EmailEntity email = new EmailEntity();
//                email.setFromEmail(mailProperties.getUsername());
//
//                // Gửi email cho từng khách hàng
//                listKH.forEach(kh -> {
//                    if (kh.getCUST_EMAIL() == null || kh.getCUST_EMAIL().isEmpty()) {
//                        LOGGER.warn("Khách hàng không có email: {}", kh.getACCOUNT_NUMBER());
//                        return;
//                    }
//                    email.setRunDate(reportDate);
//                    email.setToEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
////                    email.setToEmail(kh.getCUST_EMAIL());
//                    email.setToCC(new String[]{kh.getCUST_EMAIL()});
//                    email.setSubject(template.getTITLE());
//                    email.setBody(templateService.getTemplate(kh, templateOpt, component, 0, reportDate));
//                    email.setCustomerNo(kh.getCUSTOMER_NO());
//                    email.setComponent(component);
//                    email.setName(kh.getCUSTOMER_NAME());
//                    email.setTieuDe(template.getTITLE());
//
//                    mailHistoryKey.setRUN_DATE(reportDate);
//                    mailHistoryKey.setMA_NV(kh.getCUSTOMER_NO());
//                    mailHistoryKey.setCOMPONENT(component);
//                    mailHistoryKey.setTIEU_DE(template.getTITLE());
//
//                    // Kiểm tra lịch sử gửi mail
//                    if (!mailHistoryRepo.existsById(mailHistoryKey)) {
//                        producerService.sendEmail(email);
//                        LOGGER.info("Đã gửi email cho khách hàng: {}", kh.getACCOUNT_NUMBER());
//                    } else {
//                        LOGGER.info("Email đã được gửi trước đó cho khách hàng: {}", kh.getACCOUNT_NUMBER());
//                    }
//                });
//
//
//            }
//
//        }

        LOGGER.info("Hoàn thành xử lý email cho ngày: {}", reportDate);
    }


    @Autowired
    VayDHService vayDHService;

    @Autowired
    VayQHService vayQHService;

    @Autowired
    ThauChiHHService thauChiHHService;

    @Autowired
    ThauChiQHService thauChiQHService;

    private void processCBBList(String reportDatet) {
//        vayQHService.executeService(reportDatet);
//        vayDHService.executeService(reportDatet);
        thauChiHHService.executeService(reportDatet);
//        thauChiQHService.executeService(reportDatet);

    }
}
