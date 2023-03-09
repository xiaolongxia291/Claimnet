package tracy.manage.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tracy.manage.entity.Text;

@Repository
public interface TextDao extends BaseMapper<Text> {
    @Update("truncate table text")
    void deleteTable();
}
