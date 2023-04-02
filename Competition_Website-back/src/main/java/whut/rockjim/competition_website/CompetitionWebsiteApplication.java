package whut.rockjim.competition_website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling//开启定时器注解
public class CompetitionWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompetitionWebsiteApplication.class, args);
    }

}
