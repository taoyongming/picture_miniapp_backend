package web;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
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

    @Autowired
    private MongoDbFactory mongoDbFactory;



    public static void main(String[] args) {
        SpringApplication.run(PictureApplication.class, args);
    }


    @Bean
    public GridFSBucket getGridFSBuckets() {
        MongoDatabase db = mongoDbFactory.getDb();
        return GridFSBuckets.create(db);
    }

}
