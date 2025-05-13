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

import java.util.List;
import java.util.Map;
import java.util.Optional;
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



    public void executeService(String reportDate) {
        LOGGER.info("Start processing VAY_DEN_HAN CB emails for date: {}", reportDate);
        String component = "OD_DEN_HAN";

        List<NhacNoVayEntity> nhacNoVayList = nhacNoVayRepository.getDataByComponent(component, reportDate);

        Map<String, List<NhacNoVayEntity>> branchToEntityList = nhacNoVayList.stream()
                .collect(Collectors.groupingBy(NhacNoVayEntity::getACCOUNT_BRANCH));
        Optional<MailTemplate> templateCBOpt = mailTemplateRepo.findById("VAY_DEN_HAN" + "_CBQL");
        processCBBList(branchToEntityList, component, reportDate, templateCBOpt);

        LOGGER.info("Hoàn thành xử lý email cho ngày: {}", reportDate);
    }

    private void processCBBList(Map<String, List<NhacNoVayEntity>> branchToEntityList, String component, String reportDate, Optional<MailTemplate> templateCBOpt) {
        EmailEntity email = new EmailEntity();
        branchToEntityList.forEach((branch, list) -> {
            List<String> listCBB = nhacNoVayRepository.getListCBBByBranch(branch);
            email.setFromEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
            email.setRunDate(reportDate);
            email.setComponent(component);
            email.setCustomerNo(branch);
            email.setToEmail(EmailnhacnoApplication.getProperty("spring.mail.username"));
            email.setToCC(listCBB.toArray(new String[0]));
            email.setTieuDe(templateCBOpt.map(MailTemplate::getTITLE).orElse("Default Title"));
            email.setBody(templateService.getTemplateForVayCB(list, templateCBOpt, component));

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
}
