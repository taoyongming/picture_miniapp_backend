package web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tym
 */
@EnableSwagger2
@SpringBootApplication
@MapperScan(basePackages = {"web.dao"})
@EnableScheduling
public class PictureApplication {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PictureApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class, args);
    }

}
