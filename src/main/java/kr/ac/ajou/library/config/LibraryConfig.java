package kr.ac.ajou.library.config;

import kr.ac.ajou.library.service.LibraryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryConfig {
    @Bean
    public LibraryService createLibraryService() {
        return new LibraryService();
    }
}
