package scari.corp.test_rest_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

@SpringBootApplication
@PWA(name = "test_rest_api", shortName = "test_rest_api")
@Theme("my-theme")
public class TestRestApiApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(TestRestApiApplication.class, args);
    }
}
