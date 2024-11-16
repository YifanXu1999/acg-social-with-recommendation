
import com.yifan.cache.CacheServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = CacheServiceApplication.class)
public class JedisTest {

    @Test
    void contextLoads() {
        System.out.println("Test");
    }
}
