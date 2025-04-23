package vab.com.vn.emailnhacno.scheduler;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class QuartzSchedulerConfig {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    @Qualifier("jobOne")
    private JobDetail jobDetailOne;

    @Autowired
    @Qualifier("jobTwo")
    private JobDetail jobDetailTwo;

    @Autowired
    @Qualifier("triggerOne")
    private Trigger triggerOne;

    @Autowired
    @Qualifier("triggerTwo")
    private Trigger triggerTwo;

    @PostConstruct
    public void scheduleJobs() throws SchedulerException {
        // Xóa job cũ nếu tồn tại
        JobKey jobKeyOne = new JobKey("Qrtz_Job_Detail_One", "DEFAULT");
        if (scheduler.checkExists(jobKeyOne)) {
            scheduler.deleteJob(jobKeyOne);
        }

        JobKey jobKeyTwo = new JobKey("Qrtz_Job_Detail_Two", "DEFAULT");
        if (scheduler.checkExists(jobKeyTwo)) {
            scheduler.deleteJob(jobKeyTwo);
        }

        // Lập kế hoạch jobOne với triggerOne
        scheduler.scheduleJob(jobDetailOne, triggerOne);

        // Lập kế hoạch jobTwo với triggerTwo
        scheduler.scheduleJob(jobDetailTwo, triggerTwo);
    }
}