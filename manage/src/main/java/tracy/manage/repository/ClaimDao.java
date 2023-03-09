package tracy.manage.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tracy.manage.entity.Claim;

@Repository
public interface ClaimDao extends BaseMapper<Claim> {
    @Update("truncate table claim")
    void deleteTable();
}
