package tracy.manage.service;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tracy.manage.entity.Text;
import tracy.manage.repository.TextRepository;
import tracy.manage.util.CrudResult;
import tracy.manage.util.PageQuery;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TextServiceImpl implements TextService{
    public static final String EXCHANGE_NAME = "direct-exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TextRepository textRepository;

    @Override
    public void clear() {
        //生成消息
        JSONObject json=new JSONObject();
        json.put("method","clear");
        //发送消息给xchange
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"text",json.toString());
    }

    @Override
    public CrudResult<Text> insert(List<Text> docs) {
        List<Text> failures=new ArrayList<>();
        List<Text> success=new ArrayList<>();
        //先判定重复
        for(Text doc:docs){
            if(textRepository.existsById(doc.getId())){
                failures.add(doc);
            }
            else{
                success.add(doc);
            }
        }
        //将未重复的插入，重复的返回
        JSONObject json=new JSONObject();
        json.put("method","insert");
        json.put("param",docs);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"text",json.toString());
        return new CrudResult<>(failures.size()==0?"":("有"+failures.size()+"条数据id已存在"),failures);
    }

    @Override
    public void delete(List<String> ids) {
        JSONObject json=new JSONObject();
        json.put("method","delete");
        json.put("param",ids);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"text",json.toString());
    }

    @Override
    public List<Text> selectAll(int curPage, int pageSize) {
        return textRepository.findAll(PageRequest.of(curPage-1,pageSize)).getContent();
    }

    @Override
    public List<Text> selectCondition(PageQuery<Text> pageQuery) {
        return textRepository.findTextsByIdOrApplicationNo(pageQuery.getCondition().getId(), pageQuery.getCondition().getApplicationNo(), PageRequest.of(pageQuery.getCurPage()-1,pageQuery.getPageSize()));
    }

    @Override
    public void update(Text text) {
        //修改
        JSONObject json=new JSONObject();
        json.put("method","update");
        json.put("param",text);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"text",json.toString());
    }
}
