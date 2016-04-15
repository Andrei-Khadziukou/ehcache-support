package com.epam.ehcache.support.fi;

import com.epam.ehcache.support.common.domain.User;
import com.epam.ehcache.support.common.service.UserService;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Date: 04/13/2016
 *
 * @author Andrei_Khadziukou
 */
@Component
public class Runner implements Runnable {

    private static List<String> USER_IDS = Lists.newArrayList(
        "1174e48c-ec66-4748-8c7e-d56162e7dbbf", "5806f2f6-a9b7-4128-a2ba-3b4ceb874697",
        "fb23835b-974e-4105-a7f9-6adef1c9091f", "276b296f-5688-46c7-a985-330dd654a6c6",
        "9dab93da-2d28-490c-a6f6-d1f849ff2c18", "1508ddac-d410-40fe-beec-15fdfd956681",
        "b235c1b0-a8d5-42e6-bd51-1ccf23165970", "244c2728-4c79-440d-9c2a-645b1da8629d",
        "d7f1f1cf-7590-4cee-a406-fe4a6125015a", "275bdf4e-e8a2-4aaa-82eb-01ab9051f414",
        "6cceffae-0ea5-4e65-80dc-6b2922345ff6", "6cceffae-0ea5-4e65-80dc-6b2922345ff6",
        "8ca3b757-6c4f-43e0-92c4-0eb5905cd23a", "fd977049-7d6c-4f41-8ae7-838cd131a525",
        "54f944d1-decf-48de-bdaa-18b4d8b05bd9", "b2cde645-9ec2-4f4e-8be4-9676475a364b"
    );

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void init() throws URISyntaxException, IOException {
        jdbcTemplate.execute("CREATE TABLE users (id varchar(255), first_name varchar(255), last_name varchar(255))");
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(this.getClass().getClassLoader().getResourceAsStream("users.sql"), stringWriter);
        String sql = stringWriter.toString();

        jdbcTemplate.execute(sql);
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring-config.xml");
        Thread thread = new Thread(context.getBean(Runner.class));
        thread.start();
        thread.join();
    }

    @Override
    public void run() {
        while (true) {
            long time = System.currentTimeMillis();
            for (String id : USER_IDS) {
                User user = userService.find(id);
                System.out.println(user);
            }
            System.out.println("Millis: " + (System.currentTimeMillis() - time));
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
