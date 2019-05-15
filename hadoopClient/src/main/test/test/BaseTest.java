package test;

import java.nio.charset.Charset;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import liyu.test.hadoopClient.MainConfigure;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=MainConfigure.class)
public class BaseTest {
    public static void main(String[] args) {
        RestTemplate rt = new RestTemplate();
        rt.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String string = rt.getForObject("http://localhost:8080", String.class);
        Assert.isTrue("success".equals(string),"");
   }
};