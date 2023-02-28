package se.kth.iv1201.appserv.jobapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class used to start the Back End server.
 */
@SpringBootApplication
public class JobAppApplication {

    /**
     * The main method to start the Back End server application.
     *
     * @param args arguments that could be provided from potentially the CLI.
     */
    public static void main(String[] args) {
        SpringApplication.run(JobAppApplication.class, args);
    }

}
//fixa cors i denna och