package vab.com.vn.emailnhacno.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vab.com.vn.emailnhacno.service.CacheService;
import vab.com.vn.emailnhacno.service.JobService;

@RestController
public class TextController {
    @Autowired
    JobService jobService1;

//    @Autowired
//    THAUCHIQH jobService;

//    @Autowired
//    VayDH jobService;

//    @Autowired
//    VAYQH jobService;

    @PostMapping("/send-mail")
    public String sendMail() {
        jobService1.executeService("111");
//        List<String> dates = Arrays.asList("27-JUN-2023", "09-SEP-2025", "24-FEB-2025", "15-JUL-2023", "27-JAN-2025");
//        List<String> dates = Arrays.asList("03-JUL-2025");
//        List<String> dates = Arrays.asList("27-JUN-2023");
//        //thau chi quá hạn
//        List<String> dates = Arrays.asList("27-jan-2025");
//        for (String date : dates) {
//            jobService.executeService(date);
//        }
        return "send-mail done!";
    }
    @Autowired
    private CacheService cacheService;

    @GetMapping("/get-static-text")
    public String getStaticText() {
        return cacheService.getStaticText();
    }

}
