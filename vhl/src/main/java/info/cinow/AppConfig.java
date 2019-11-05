package info.cinow;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.bedatadriven.jackson.datatype.jts.JtsModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {
    @Autowired
    DataSourceProperties dataSourceProperties;

    @Bean
    public AmazonS3 amazonS3Client(AWSCredentialsProvider credentialsProvider,
            @Value("${cloud.aws.region.static}") String region) {
        return AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(region).build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*"); // TODO: change to allow local or aws connections via
                                                                // properties file
            }
        };
    }

    @Bean
    public JtsModule jtsModule() {
        return new JtsModule();
    }

}