package tracy.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tracy.client.entity.Claim;
import tracy.client.repository.ClaimRepository;
import tracy.client.util.RedisUtil;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClaimServiceImpl implements ClaimService{
    @Autowired
    private ClaimRepository claimRepository;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Claim claim(String id){
        if (!redisUtil.hasKey(id)) {
            Claim claim = claimRepository.findClaimById(id);
            if(claim==null)redisUtil.set(id, "");
            else{
                redisUtil.set(id,claim.toString());
            }
            return claim;
        }else{
            String redis=redisUtil.get(id);
            if(redis.length()==0)return null;
            else return new Claim().strToClaim(redis);
        }
    }
}
