package tracy.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tracy.manage.entity.Admin;

import java.io.Serializable;
import java.util.List;

public interface AdminService extends IService<Admin> {
    boolean save(Admin entity);
    boolean removeById(Serializable id);
    boolean updateById(String id,String role);
    List<Admin> list();
}

