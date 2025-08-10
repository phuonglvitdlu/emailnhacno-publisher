package vab.com.vn.emailnhacno.scheduler;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.quartz.CronScheduleBuilder;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import vab.com.vn.emailnhacno.EmailnhacnoApplication;
import vab.com.vn.emailnhacno.config.AutoWiringSpringBeanJobFactory;

@Configuration
@EnableAutoConfiguration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='true'")
public class SpringQrtzScheduler {

    Logger logger = LoggerFactory.getLogger("SpringQrtzScheduler");

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        logger.info("Hello world from Spring...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        logger.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource) {

        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));

        logger.debug("Setting the Scheduler up");
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);

        // Comment the following line to use the default Quartz job store.
        schedulerFactory.setDataSource(quartzDataSource);

        return schedulerFactory;
    }

    @Bean(name = "jobOne")
    @Primary
    public JobDetailFactoryBean jobDetailTwo() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(JobExecuteBirthday.class);
        jobDetailFactory.setName("Qrtz_Job_Detail_One");
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }


//    @Bean
//    @Primary
//    public Trigger triggerOne(@Qualifier("jobOne")JobDetail job) {
////        Trigger trigger = TriggerBuilder.newTrigger()
////                .withIdentity("fire7AM", "HRAUTOMAIL")
////                .forJob(job)
////                .withSchedule(CronScheduleBuilder.cronSchedule("0 45 6  * * ?"))
////                .build();
////.withSchedule(CronScheduleBuilder.cronSchedule("*/30 * * * * ?"))
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("fire7AM", "HRAUTOMAIL")
//                .forJob(job)
//                .withSchedule(CronScheduleBuilder.cronSchedule(HrAutomailApplication.getProperty("cron")))
//                .build();
//        return trigger;
//    }
//    @Bean
//    public Trigger triggerTwo(@Qualifier("jobTwo")JobDetail job) {
////        Trigger trigger = TriggerBuilder.newTrigger()
////                .withIdentity("fire7AM", "HRAUTOMAIL")
////                .forJob(job)
////                .withSchedule(CronScheduleBuilder.cronSchedule("0 45 6  * * ?"))
////                .build();
////.withSchedule(CronScheduleBuilder.cronSchedule("*/30 * * * * ?"))
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("fire7AM1", "HRAUTOMAIL")
//                .forJob(job)
//                .withSchedule(CronScheduleBuilder.cronSchedule(HrAutomailApplication.getProperty("cron1")))
//                .build();
//        return trigger;
//    }

    @Bean
    @Primary
    public Trigger triggerOne(@Qualifier("jobOne") JobDetail job) {
        return TriggerBuilder.newTrigger()
                .withIdentity("triggerOne", "groupOne")
                .forJob(job)
                .withSchedule(CronScheduleBuilder.cronSchedule(EmailnhacnoApplication.getProperty("cron-sendmail")))  // Cron expression for jobOne
                .build();
    }



    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

}
