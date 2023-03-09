package tracy.manage.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
 
    public static final String EXCHANGE_NAME = "direct-exchange";
    public static final String TEXT_QUEUE = "text-queue";
    public static final String CLAIM_QUEUE = "claim-queue";
    public static final String TEXT_RKEY = "text";
    public static final String CLAIM_RKEY = "claim";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    /**
     * 1.
     * 声明交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        /**
         * 参数说明:
         * 1. 交换机名称
         * 2. 是否持久化 true：持久化，交换机一直保留 false：不持久化，用完就删除
         * 3. 是否自动删除 false：不自动删除 true：自动删除
         */
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }
 
    /**
     * 2.
     * 声明队列
     * @retur     */
    @Bean
    public Queue textQueue() {
        /**
         * Queue构造函数参数说明
         * 1. 队列名
         * 2. 是否持久化 true：持久化 false：不持久化
         */
        return new Queue(TEXT_QUEUE, true);
    }

    @Bean
    public Queue claimQueue() {
        /**
         * Queue构造函数参数说明
         * 1. 队列名
         * 2. 是否持久化 true：持久化 false：不持久化
         */
        return new Queue(CLAIM_QUEUE, true);
    }

 
    /**
     * 3.
     * 队列与交换机绑定
     */
    @Bean
    public Binding textBinding() {
        return BindingBuilder.bind(textQueue()).to(directExchange()).with(TEXT_RKEY);
    }
 
    @Bean
    public Binding claimBinding() {
        return BindingBuilder.bind(claimQueue()).to(directExchange()).with(CLAIM_RKEY);
    }


    /**
     * 4.
     * 声明连接工厂
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * 5.
     * 将自定义的RabbitTemplate对象注入bean容器
     */
    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启消息推送结果回调
        rabbitTemplate.setMandatory(true);
        //设置ConfirmCallback回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//                log.info("==============ConfirmCallback start ===============");
//                log.info("回调数据：{}", correlationData);
//                log.info("确认结果：{}", ack);
//                log.info("返回原因：{}", cause);
//                log.info("==============ConfirmCallback end =================");

            }
        });
        //设置ReturnCallback回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//                log.info("==============ReturnCallback start ===============");
//                log.info("发送消息：{}", JSONUtil.toJsonStr(message));
//                log.info("结果状态码：{}", replyCode);
//                log.info("结果状态信息：{}", replyText);
//                log.info("交换机：{}", exchange);
//                log.info("路由key：{}", routingKey);
//                log.info("==============ReturnCallback end =================");
            }
        });
        return rabbitTemplate;
    }


}