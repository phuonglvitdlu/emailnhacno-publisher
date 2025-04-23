package vab.com.vn.emailnhacno.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.entity.*;
import vab.com.vn.emailnhacno.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private SenderService senderService;


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

        // Lấy danh sách template
        List<MailTemplate> templates = mailTemplateRepo.findAll();
        if (templates.isEmpty()) {
            LOGGER.warn("Không có template nào được cấu hình.");
            return;
        }

        String[] components = {"OD_DEN_HAN", "VAY_DEN_HAN", "OD_HET_HAN", "OD_QUA_HAN", "VAY_QUA_HAN"};
//        String[] components = {"VAY_QUA_HAN"};

        for (String component : components) {
            LOGGER.info("Processing component: {}", component);
            if (!component.equals("OD_DEN_HAN") && !component.equals("OD_QUA_HAN") && !component.equals("OD_HET_HAN")) {
                // Lấy danh sách khách hàng theo component
                List<NhacNoVayEntity> nhacNoVayList = nhacNoVayRepository.getDataByComponent(component, reportDate);
                if (nhacNoVayList.isEmpty()) {
                    LOGGER.warn("Không có dữ liệu nhắc nợ cho component: {}", component);
                    continue;
                }

                // Lấy template
                Optional<MailTemplate> templateOpt = mailTemplateRepo.findById(component);

                if (!templateOpt.isPresent()) {
                    LOGGER.warn("Không tìm thấy template cho component: {}", component);
                    continue;
                }

                // Gửi email cho từng khách hàng
                MailTemplate template = templateOpt.get();
                MailHistoryKey mailHistoryKey = new MailHistoryKey();
                EmailEntity email = new EmailEntity();
                email.setFromEmail(mailProperties.getUsername());

                nhacNoVayList.forEach(kh -> {
                    if (kh.getEMAIL_KH() == null || kh.getEMAIL_KH().isEmpty()) {
                        LOGGER.warn("Khách hàng không có email: {}", kh.getACCOUNT_NUMBER());
                        return;
                    }
                    email.setRunDate(reportDate);
                    email.setToEmail(kh.getEMAIL_KH());
                    email.setToCC(new String[]{kh.getEMAIL_KH()});
                    email.setSubject(template.getTITLE());
                    email.setBody(templateService.getTemplate(kh, templateOpt, component, 0, reportDate));
                    email.setCustomerNo(kh.getCUSTOMER_NO());
                    email.setComponent(component);
                    email.setName(kh.getTEN_KHACH_HANG());
                    email.setTieuDe(template.getTITLE());

                    mailHistoryKey.setRUN_DATE(reportDate);
                    mailHistoryKey.setMA_NV(kh.getCUSTOMER_NO());
                    mailHistoryKey.setCOMPONENT(kh.getCOMPONENT());
                    mailHistoryKey.setTIEU_DE(template.getTITLE());

                    // Kiểm tra lịch sử gửi mail
                    if (!mailHistoryRepo.existsById(mailHistoryKey)) {
                        senderService.sendToProducer(email);

                    } else {
                        LOGGER.info("Email đã được gửi trước đó cho khách hàng: {}", kh.getACCOUNT_NUMBER());
                    }
                });
                if (component.equals("VAY_QUA_HAN")) {
                    Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("VAY_QUA_HAN" + "_CBQL");
                    processCBBList(component, reportDate, templateCBOpt);
                }
            } else {

                List<KhachHangEntity> listKH;
                if (component.equals("OD_DEN_HAN")) {
                    // Lấy danh sách khách hàng Thấu chi đến hạn
                    listKH = khachHangRepo.getDistinctByCustomerNoODDenHan(reportDate);
                    if (listKH.isEmpty()) {
                        LOGGER.warn("Không có dữ liệu Thấu chi đến hạn cho ngày: {}", reportDate);
                        continue;
                    }
                } else if (component.equals("OD_QUA_HAN")) {
                    // Lấy danh sách khách hàng Thấu chi quá hạn
                    listKH = khachHangRepo.getDistinctByCustomerNoODQuaHan(reportDate);
                    if (listKH.isEmpty()) {
                        LOGGER.warn("Không có dữ liệu Thấu chi quá hạn cho ngày: {}", reportDate);
                        continue;
                    }
                } else {
                    // Lấy danh sách khách hàng Thấu chi quá hạn
                    listKH = khachHangRepo.getDistinctByCustomerNoODHetHan(reportDate);
                    if (listKH.isEmpty()) {
                        LOGGER.warn("Không có dữ liệu Thấu chi hết hạn cho ngày: {}", reportDate);
                        continue;
                    }
                }

                Optional<MailTemplate> templateOpt;
                // Lấy template
                if (component.equals("OD_QUA_HAN")) {
                    templateOpt = mailTemplateRepo.findById("THAUCHIQH");
                } else if (component.equals("OD_DEN_HAN")) {
                    templateOpt = mailTemplateRepo.findById("THAUCHI");
                } else {
                    templateOpt = mailTemplateRepo.findById("THAUCHIHH");
                }
                if (!templateOpt.isPresent()) {
                    LOGGER.warn("Không tìm thấy template cho component: {}", component);
                    continue;
                }

                MailTemplate template = templateOpt.get();
                MailHistoryKey mailHistoryKey = new MailHistoryKey();
                EmailEntity email = new EmailEntity();
                email.setFromEmail(mailProperties.getUsername());

                // Gửi email cho từng khách hàng
                listKH.forEach(kh -> {
                    if (kh.getCUST_EMAIL() == null || kh.getCUST_EMAIL().isEmpty()) {
                        LOGGER.warn("Khách hàng không có email: {}", kh.getACCOUNT_NUMBER());
                        return;
                    }
                    email.setRunDate(reportDate);
                    email.setToEmail(kh.getCUST_EMAIL());
                    email.setToCC(new String[]{kh.getCUST_EMAIL()});
                    email.setSubject(template.getTITLE());
                    email.setBody(templateService.getTemplate(kh, templateOpt, component, 0, reportDate));
                    email.setCustomerNo(kh.getCUSTOMER_NO());
                    email.setComponent(component);
                    email.setName(kh.getCUSTOMER_NAME());
                    email.setTieuDe(template.getTITLE());

                    mailHistoryKey.setRUN_DATE(reportDate);
                    mailHistoryKey.setMA_NV(kh.getCUSTOMER_NO());
                    mailHistoryKey.setCOMPONENT(component);
                    mailHistoryKey.setTIEU_DE(template.getTITLE());

                    // Kiểm tra lịch sử gửi mail
                    if (!mailHistoryRepo.existsById(mailHistoryKey)) {
                        senderService.sendToProducer(email);
                        LOGGER.info("Đã gửi email cho khách hàng: {}", kh.getACCOUNT_NUMBER());
                    } else {
                        LOGGER.info("Email đã được gửi trước đó cho khách hàng: {}", kh.getACCOUNT_NUMBER());
                    }
                });
                if (component.equals("OD_QUA_HAN")) {
                    Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("THAUCHIQH" + "_CBQL");
                    processCBBList(component, reportDate, templateCBOpt);
                } else if (component.equals("OD_DEN_HAN")) {
                    Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("VAY_DEN_HAN" + "_CBQL");
                    processCBBList(component, reportDate, templateCBOpt);
                } else {
                    Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("THAUCHIHH" + "_CBQL");
                    processCBBList(component, reportDate, templateCBOpt);
                }


            }

        }

        LOGGER.info("Hoàn thành xử lý email cho ngày: {}", reportDate);
    }

    private List<NhanVienEntity> getObjectCBQLFromMaCBB(String maCBB) {
        List<NhanVienEntity> result = new ArrayList<>();

        Optional<NhanVienEntity> objectCBBOpt = nhanVienRepo.getObjectByMaNV(maCBB);
        objectCBBOpt.ifPresent(result::add);

        String maQLTT = nhanVienRepo.getMAQLTT(maCBB);
        Optional<NhanVienEntity> objectQLTTOpt = nhanVienRepo.getObjectByMaNV(maQLTT);
        objectQLTTOpt.ifPresent(result::add);

        return result;
    }

    private void processCBBList(String component, String reportDate, Optional<MailTemplate> templateCBOpt) {
        // Xử lý danh sách CBB
        List<String> listCBB = nhacNoVayRepository.getMaCBB(reportDate, component);
        if (listCBB.isEmpty()) {
            LOGGER.warn("Không tìm thấy danh sách CBB cho component: {}", component);
            return;
        }

        MailHistoryKey mailHistoryKey = new MailHistoryKey();
        EmailEntity email = new EmailEntity();
        email.setFromEmail(mailProperties.getUsername());

        listCBB.forEach(cbb -> {
            List<NhacNoVayEntity> listKHVay = (component.equals("OD_DEN_HAN") || component.equals("VAY_DEN_HAN"))
                    ? nhacNoVayRepository.listKHVayDH(cbb, reportDate)
                    : nhacNoVayRepository.listKHVayQH(component, cbb, reportDate);

            if (listKHVay.isEmpty()) {
                LOGGER.warn("Danh sách khách hàng vay trống cho CBB: {}", cbb);
                return;
            }

            List<NhanVienEntity> objectCBQL = getObjectCBQLFromMaCBB(cbb);
            if (objectCBQL.size() >= 2) {
                NhanVienEntity primaryCB = objectCBQL.get(0);
                NhanVienEntity secondaryCB = objectCBQL.get(1);
                email.setRunDate(reportDate);
                email.setToEmail(primaryCB.getNV_EMAIL());
                email.setToCC(new String[]{primaryCB.getNV_EMAIL(), secondaryCB.getNV_EMAIL()});
                email.setSubject(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Subject"));
                email.setBody(templateService.getTemplateForVayCB(listKHVay, templateCBOpt, component));
                email.setTieuDe(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Title"));
                email.setCustomerNo(primaryCB.getNV_MA());
                email.setName(primaryCB.getNV_HO_TEN());
                email.setComponent(component);

                mailHistoryKey.setRUN_DATE(reportDate);
                mailHistoryKey.setMA_NV(primaryCB.getNV_MA());
                mailHistoryKey.setCOMPONENT(component);
                mailHistoryKey.setTIEU_DE(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Title"));

                if (!mailHistoryRepo.existsById(mailHistoryKey)) {

                    senderService.sendToProducer(email);
                    LOGGER.info("Đã gửi email cho cán bộ quản lý: {}", primaryCB.getNV_EMAIL());
                } else {
                    LOGGER.info("Email đã được gửi trước đó cho cán bộ: {}", primaryCB.getNV_EMAIL());
                }
            } else {
                LOGGER.warn("Không đủ thông tin cán bộ quản lý cho CBB: {}", cbb);
            }
        });
    }
}
