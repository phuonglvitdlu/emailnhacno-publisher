package vab.com.vn.emailnhacno;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = {
        "vab.com.vn",            // gói của bạn hiện tại
        "org.com.vab"            // gói chứa ProducerService
})
public class EmailnhacnoApplication {

//    final static Logger logger = LoggerFactory.getLogger(HrAutomailApplication.class);
    private static Properties configprop = new Properties();
    public static void main(String[] args) throws Exception {

        if (configprop.isEmpty()) {
            configprop = loadProperty(args[0]);
            if (configprop.isEmpty()) {
                throw new Exception("Không tìm thấy link file config.");
            }
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(getProperty("logfile.path"));
        }
        SpringApplication app = new SpringApplication(EmailnhacnoApplication.class);
        app.setDefaultProperties(configprop);
        app.setAllowCircularReferences(true);
        app.run(args);
    }

    public static Properties loadProperty(String pathfile) throws Exception {
        Properties configProp = null;

        File file = new File(pathfile);
        if (!file.exists()) {
            throw new Exception("Khong load duoc properties theo duong dan: " + pathfile);
        }

        FileInputStream in = new FileInputStream(file);
        configProp = new Properties();
        configProp.load(in);
        in.close();

        return configProp;
    }

    public static String getProperty(String name) {
        return configprop.getProperty(name);
    }
}
