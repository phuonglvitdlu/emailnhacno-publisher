package vab.com.vn.emailnhacno.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import vab.com.vn.emailnhacno.entity.EmailEntity;

@Service
public class SenderService {

    private final RestTemplate restTemplate;

    public SenderService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendToProducer(EmailEntity emal) {
        String url = "http://localhost:8080/api/producer/sendMessage"; // địa chỉ của service Producer (Service B)
        restTemplate.postForEntity(url, emal, String.class);
    }
}
