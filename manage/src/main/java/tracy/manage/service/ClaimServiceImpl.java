package tracy.manage.service;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tracy.manage.entity.Claim;
import tracy.manage.repository.ClaimRepository;
import tracy.manage.util.CrudResult;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClaimServiceImpl implements ClaimService{
    public static final String EXCHANGE_NAME = "direct-exchange";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ClaimRepository claimDao;

    @Override
    public void clear() {
        //生成消息
        JSONObject json=new JSONObject();
        json.put("method","clear");
        //发送消息给xchange
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"claim",json.toString());
    }

    @Override
    public CrudResult<Claim> insert(List<Claim> docs) {
        List<Claim> failures=new ArrayList<>();
        List<Claim> success=new ArrayList<>();
        //先判定重复
        for(Claim doc:docs){
            if(claimDao.existsById(doc.getId())){
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
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"claim",json.toString());
        return new CrudResult<>(failures.size()==0?"":("有"+failures.size()+"条数据id已存在"),failures);
    }

    @Override
    public void delete(List<String> ids) {
        JSONObject json=new JSONObject();
        json.put("method","delete");
        json.put("param",ids);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"claim",json.toString());
    }

    @Override
    public List<Claim> selectAll(int curPage, int pageSize) {
        return claimDao.findAll(PageRequest.of(curPage-1,pageSize)).getContent();
    }

    @Override
    public Claim selectClaimById(String id) {
        return claimDao.findClaimById(id);
    }

    @Override
    public void update(Claim claim) {
        //修改
        JSONObject json=new JSONObject();
        json.put("method","update");
        json.put("param",claim);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME,"claim",json.toString());
    }
}
