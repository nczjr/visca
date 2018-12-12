package webvisca.webviscaApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "webvisca")
public class WebviscaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebviscaApplication.class, args);
	}

}
