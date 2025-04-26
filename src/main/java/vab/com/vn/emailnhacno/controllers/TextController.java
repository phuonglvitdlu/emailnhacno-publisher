package vab.com.vn.emailnhacno.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vab.com.vn.emailnhacno.service.JobService;
import vab.com.vn.emailnhacno.service.canbo.VayDH;

import java.util.Arrays;
import java.util.List;

@RestController
public class TextController {
//    @Autowired
//    JobService jobService;

    @Autowired
    VayDH vaydh;

    @PostMapping("/send-mail")
    public String sendMail() {
//        List<String> dates = Arrays.asList("09-SEP-2025", "24-FEB-2025", "15-JUL-2023", "27-JAN-2025");
        List<String> dates = Arrays.asList("27-JUN-2023");
        for (String date : dates) {
            vaydh.executeService(date);
        }
        return "send-mail done!";
    }

}
