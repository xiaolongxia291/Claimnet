package tracy.manage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tracy.manage.mq.Producer;

@SpringBootTest
class ManageApplicationTests {
    @Autowired
    private Producer producer;

    @Test
    void testProducer() {
        producer.send();
    }

}
