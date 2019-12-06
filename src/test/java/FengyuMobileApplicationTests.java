import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import web.PictureApplication;
import web.redis.RedisUtil;
import web.util.AccessTokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PictureApplication.class)
public class FengyuMobileApplicationTests {


    @Autowired
    private RedisUtil redisUtil;


    @Test
    public void testRedis() {
        redisUtil.set("test", "test");
        System.out.println(redisUtil.hget("token","1b89c709-3c65-4866-bec2-fdf03e96ba6f"));
    }

    @Test
    public void testMysql() {

    }


    @Test
    public void testGetAccessToken() {
//      AccessTokenUtil.getAccessToken();
      AccessTokenUtil.getGzhAccessToken();
    }





}
