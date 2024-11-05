package giza.client.platform.survey.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableJpaRepositories(basePackages = {"giza.client.platform.survey.core.repository", "giza.user.sync.core.repository"})
@EntityScan(basePackages = "giza.client.platform.survey.model.entity")
public class DatabaseConnection extends HikariConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return new HikariDataSource(this);
    }
}
