package vab.com.vn.emailnhacno.service.canbo;

import org.com.vab.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vab.com.vn.emailnhacno.EmailnhacnoApplication;
import vab.com.vn.emailnhacno.entity.*;
import vab.com.vn.emailnhacno.repository.*;
import vab.com.vn.emailnhacno.service.TemplateService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ThauChiHHService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VayDHService.class);

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private MailTemplateRepository mailTemplateRepo;

    @Autowired
    private NhacNoVayRepository nhacNoVayRepository;


    @Autowired
    private MailHistoryRepository mailHistoryRepo;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailNhacNoDmDviRepository emailNhacNoDmDviRepository;


    @Autowired
    private NhanVienRepository nhanVienRepo;


    public void executeService(String reportDate) {
        LOGGER.info("Start processing THAUCHIHH CB emails for date: {}", reportDate);
        String component = "THAUCHIHH";

        List<NhacNoVayEntity> nhacNoVayList = nhacNoVayRepository.getDataByComponent("OD_HET_HAN", reportDate);

        Map<String, List<NhacNoVayEntity>> branchToEntityList = nhacNoVayList.stream()
                .collect(Collectors.groupingBy(NhacNoVayEntity::getACCOUNT_BRANCH));
        Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById(component + "_CBQL");
        processCBBList(branchToEntityList, component, reportDate, templateCBOpt);

        LOGGER.info("Hoàn thành xử lý email cho ngày: {}", reportDate);
    }

    private void processCBBList(Map<String, List<NhacNoVayEntity>> branchToEntityList, String component, String reportDate, Optional<MailTemplate> templateCBOpt) {


        branchToEntityList.forEach((branch, list) -> {
            // Lấy tất cả MA_CB_BAN trong list của branch
            Set<String> maCbBanSet = list.stream()
                    .map(NhacNoVayEntity::getMA_CB_BAN)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // Gom tất cả email của CBB + QLTT từ các MA_CB_BAN
            Set<String> toEmails = new HashSet<>();
            for (String maCbBan : maCbBanSet) {
                List<NhanVienEntity> cbqlList = getObjectCBQLFromMaCBB(maCbBan);
                cbqlList.stream()
                        .map(NhanVienEntity::getNV_EMAIL) // chú ý sửa đúng getter của bạn
                        .filter(Objects::nonNull)
                        .forEach(toEmails::add);
            }

            List<String> listCBB = nhacNoVayRepository.getListCBBByBranch(branch);
            EmailNhacNoDmDviEntity dvi = null;
            dvi = emailNhacNoDmDviRepository.findByDvMa(branch);
            // Lấy CC danh sách CBB của branch

            List<String> emails = nhanVienRepository.findEmailNhanVienByDvMa(dvi.getCnMa());
            listCBB.addAll(emails);


// Loại bỏ email trùng
            listCBB = listCBB.stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .distinct()
                    .collect(Collectors.toList());

            EmailEntity email = new EmailEntity();

            email.setFromEmail(
                    EmailnhacnoApplication.getProperty("spring.mail.username")
            );
            email.setRunDate(reportDate);
            email.setComponent(component);
            email.setCustomerNo(branch);
            email.setEmailType("CB");

            // TO recipients
            email.setToEmail(String.join(",", toEmails));

            // CC recipients
            email.setToCC(listCBB.toArray(new String[0]));

            // Email subject
            String tieuDe = templateCBOpt
                    .map(MailTemplate::getTITLE)
                    .orElse("Default Title");
            email.setTieuDe(tieuDe);

            // Email content
            String content = templateService.getTemplateForVayCB(
                    list,
                    templateCBOpt,
                    "OD_HET_HAN"
            );
            email.setBody(content);

            // Create MailHistoryKey
            MailHistoryKey mailHistoryKey = new MailHistoryKey();
            mailHistoryKey.setRUN_DATE(reportDate);
            mailHistoryKey.setMA_NV(branch);
            mailHistoryKey.setCOMPONENT(component);
            mailHistoryKey.setTIEU_DE(tieuDe);
            mailHistoryKey.setEMAIL_TYPE("CB");
            if (!mailHistoryRepo.existsById(mailHistoryKey)) {
                producerService.sendEmail(email);
            }
        });


    }


    private List<NhanVienEntity> getObjectCBQLFromMaCBB(String maCBB) {
        List<NhanVienEntity> result = new ArrayList<>();
        Optional<NhanVienEntity> objectCBBOpt = nhanVienRepo.getObjectByMaNV(maCBB);
        objectCBBOpt.ifPresent(result::add);
        return result;
    }
}
