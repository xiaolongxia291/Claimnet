package tracy.manage.mq;


import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;


@Service
public class Producer {
    public static final String EXCHANGE_NAME = "direct-exchange";
    public static final String TEXT_RKEY = "text";
    public static final String CLAIM_RKEY = "claim";
 
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        HashMap<String,String> map1=new HashMap<>();
        HashMap<String,String> map2=new HashMap<>();
        map1.put("message","text test");
        map2.put("message","claim test");
        String message1=new JSONObject(map1).toString();
        String message2=new JSONObject(map2).toString();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,TEXT_RKEY ,message1);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,CLAIM_RKEY,message2);
    }
}