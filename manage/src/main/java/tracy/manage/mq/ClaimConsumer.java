package tracy.manage.mq;


import com.rabbitmq.client.Channel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tracy.manage.entity.Claim;
import tracy.manage.repository.ClaimDao;
import tracy.manage.repository.ClaimRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RabbitListener(queues = {"claim-queue"}) //监听队列
public class ClaimConsumer {
    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimDao claimDao;

    @RabbitHandler
    public void receive(String msg, Channel channel, Message message) throws IOException {
        try {
            //消费过程
            System.out.println("claim consumer接收到消息："+ msg);
            JSONObject json= (JSONObject) JSONObject.stringToValue(msg);
            String method=json.getString("method");
            if(method.equals("clear")){
                claimRepository.deleteAll();
                claimDao.deleteTable();
            }else if(method.equals("delete")){
                JSONArray params=json.getJSONArray("param");
                List<String> ids=new ArrayList<>();
                for(Object j:params){
                    ids.add(String.valueOf(j));
                }
                claimRepository.deleteClaimsByIdIn(ids);
                claimDao.deleteBatchIds(ids);
            }else if(method.equals("insert")){
                JSONArray params=json.getJSONArray("param");
                List<Claim> claims=new ArrayList<>();
                for(Object j:params){
                    claims.add((Claim)j);
                }
                claimRepository.save(claims);
                for(Claim c:claims)claimDao.insert(c);
            }else{
                Claim claim=(Claim)json.get("param");
                claimRepository.save(claim);
                claimDao.updateById(claim);
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
