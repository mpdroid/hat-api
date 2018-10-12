package sortinghat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SortingHatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SortingHatApplication.class, args);
    }
}