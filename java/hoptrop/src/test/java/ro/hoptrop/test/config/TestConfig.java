package ro.hoptrop.test.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ro.hoptrop.service.MailService;
import ro.hoptrop.test.mock.MailServiceMock;

/**
 * Created by Luci on 04-Mar-17.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"ro.hoptrop"})
public class TestConfig {

    @Bean
    @Primary
    public MailService getMailServiceMock() {
        return new MailServiceMock();
    }


}
