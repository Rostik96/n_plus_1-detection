package dev.rost.datasource;

import dev.rost.datasource.book.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}


	@Bean
	ApplicationListener<ApplicationReadyEvent> onStartup(BookRepository bookRepository) {
	    return event -> {
			bookRepository.findAll().forEach(System.out::println);
		};
	}
}
