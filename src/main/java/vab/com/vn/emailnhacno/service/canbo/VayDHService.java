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
public class VayDHService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VayDHService.class);

    @Autowired
    private ProducerService producerService;

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
    private NhanVienRepository nhanVienRepo;

    public void executeService(String reportDate) {
        LOGGER.info("Start processing VAY_DEN_HAN CB emails for date: {}", reportDate);
//"OD_DEN_HAN",
        List<String> components = Arrays.asList("OD_DEN_HAN", "VAY_DEN_HAN");

        for (String component : components) {
            List<NhacNoVayEntity> nhacNoVayList = nhacNoVayRepository.getDataByComponent(component, reportDate);

            Map<String, List<NhacNoVayEntity>> branchToEntityList = nhacNoVayList.stream()
                    .collect(Collectors.groupingBy(NhacNoVayEntity::getACCOUNT_BRANCH));

            Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("VAY_DEN_HAN" + "_CBQL");

            processCBBList(branchToEntityList, component, reportDate, templateCBOpt);
        }

        LOGGER.info("Hoàn thành xử lý email cho ngày: {}", reportDate);
    }


    private void processCBBList(Map<String, List<NhacNoVayEntity>> branchToEntityList,
                                String component,
                                String reportDate,
                                Optional<MailTemplate> templateCBOpt) {
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

            // Lấy CC danh sách CBB của branch
            List<String> listCBB = nhacNoVayRepository.getListCBBByBranch(branch);

            EmailEntity email = new EmailEntity();
            email.setFromEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
            email.setRunDate(reportDate);
            email.setComponent(component);
            email.setCustomerNo(branch);

            // Gom tất cả email thành chuỗi , ngăn cách
            email.setToEmail(String.join(",", toEmails));

            // CC
            email.setToCC(listCBB.toArray(new String[0]));

            email.setTieuDe(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Title"));
            email.setBody(templateService.getTemplateForVayCB(list, templateCBOpt, "VAY_QUA_HAN"));

            MailHistoryKey mailHistoryKey = new MailHistoryKey();
            mailHistoryKey.setRUN_DATE(reportDate);
            mailHistoryKey.setMA_NV(branch);
            mailHistoryKey.setCOMPONENT(component);
            mailHistoryKey.setTIEU_DE(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Title"));

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
