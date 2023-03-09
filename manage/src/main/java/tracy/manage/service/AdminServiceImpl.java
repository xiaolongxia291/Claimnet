package tracy.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tracy.manage.entity.Admin;
import tracy.manage.repository.AdminDao;

import java.io.Serializable;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    public boolean save(Admin admin) {
        return adminDao.insert(admin)!=0;
    }

    @Override
    public boolean removeById(Serializable id) {
        return adminDao.deleteById(id)!=0;
    }

    @Override
    public boolean updateById(String id,String role) {
        Admin admin=adminDao.selectById(id);
        admin.setRole(role);
        return adminDao.updateById(admin)!=0;
    }

    @Override
    public List<Admin> list() {
        return adminDao.selectList(new QueryWrapper<>());
    }
}

