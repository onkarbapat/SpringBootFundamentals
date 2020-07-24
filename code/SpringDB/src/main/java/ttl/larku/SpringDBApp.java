package ttl.larku;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaRepositories
public class SpringDBApp extends SpringBootServletInitializer implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication springApp = new SpringApplication(SpringDBApp.class);

        springApp.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringDBApp.class);
    }

//    @Autowired
//    private StudentService studentService;

    @Override
    public void run(String... args) {
        System.out.println("Init completed");
    }

//    public void fakeData() {
//        for (int i = 0; i < 50; i++) {
//            studentService.createStudent("Fake #" + i);
//        }
//    }
}

@Configuration
class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
