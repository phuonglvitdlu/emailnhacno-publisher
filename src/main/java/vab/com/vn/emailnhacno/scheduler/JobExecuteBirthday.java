package vab.com.vn.emailnhacno.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vab.com.vn.emailnhacno.service.JobService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class JobExecuteBirthday implements Job {

    Logger logger = LoggerFactory.getLogger("JobExecute");

    @Autowired
    private JobService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        // Lấy ngày hiện tại theo định dạng "dd-MMM-yyyy"
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
        jobService.executeService(currentDate);
        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }
}
