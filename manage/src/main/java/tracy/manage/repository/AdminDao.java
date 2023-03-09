package tracy.manage.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tracy.manage.entity.Admin;

import java.io.Serializable;
import java.util.List;

@Repository
public interface AdminDao extends BaseMapper<Admin> {
    @Override
    int insert(Admin entity);

    @Override
    int deleteById(Serializable id);

    @Override
    Admin selectById(Serializable id);

    @Override
    int updateById(Admin admin);

    @Override
    List<Admin> selectList(@Param(Constants.WRAPPER)Wrapper<Admin> ew);
}
