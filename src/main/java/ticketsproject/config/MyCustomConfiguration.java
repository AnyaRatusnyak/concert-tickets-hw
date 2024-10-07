package ticketsproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyCustomConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MyCustomConfiguration.class);

    @Bean
    @ConditionalOnProperty(name = "creationMyFirstConditionalBean", havingValue = "true")
    public String thisIsMyFirstConditionalBean() {
        logger.info("ThisIsMyFirstConditionalBean is created");
        return "This is my first conditional bean!";
    }
}
