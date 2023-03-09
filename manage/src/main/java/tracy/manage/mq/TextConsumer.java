package tracy.manage.mq;


import com.rabbitmq.client.Channel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tracy.manage.entity.Text;
import tracy.manage.repository.TextDao;
import tracy.manage.repository.TextRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RabbitListener(queues = {"text-queue"}) //监听队列
public class TextConsumer {
    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TextDao textDao;

    @RabbitHandler
    public void receive(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("text consumer接收到消息："+ msg);
            //消费过程
            JSONObject json= (JSONObject) JSONObject.stringToValue(msg);
            String method=json.getString("method");
            if(method.equals("clear")){
                textRepository.deleteAll();
                textDao.deleteTable();
            }else if(method.equals("delete")){
                JSONArray params=json.getJSONArray("param");
                List<String> ids=new ArrayList<>();
                for(Object j:params){
                    ids.add(String.valueOf(j));
                }
                textRepository.deleteTextsByIdIn(ids);
                textDao.deleteBatchIds(ids);
            }else if(method.equals("insert")){
                JSONArray params=json.getJSONArray("param");
                List<Text> texts=new ArrayList<>();
                for(Object j:params){
                    texts.add((Text)j);
                }
                textRepository.save(texts);
                for(Text t:texts)textDao.insert(t);
            }else{
                Text text=(Text)json.get("param");
                textRepository.save(text);
                textDao.updateById(text);
            }
            //消费确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                //basicReject: 拒绝消息，与basicNack区别在于不能进行批量操作，其他用法很相似 false表示消息不再重新进入队列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {
                // basicNack:表示失败确认，一般在消费消息业务异常时用到此方法，可以将消息重新投递入队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}
