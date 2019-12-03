package web.Configurator;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "wx")
@PropertySource(value = {"classpath:constant.properties"})
public class WxProperties {

    private String appid;
    private String secret;
    private String token;
    private String aesKey;
    private String msgDataFormat;
    private String gzhAppid;
    private String gzhSecret;


}
