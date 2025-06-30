package vab.com.vn.emailnhacno.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vab.com.vn.emailnhacno.service.JobService1;

@Component
public class JobExecuteBirthday implements Job {

    Logger logger = LoggerFactory.getLogger("JobExecute");

    @Autowired
    private JobService1 jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        logger.info("Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());


        logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }
}
