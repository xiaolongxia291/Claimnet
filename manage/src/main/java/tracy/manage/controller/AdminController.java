package tracy.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tracy.manage.entity.Admin;
import tracy.manage.service.AdminService;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员管理模块")
public class AdminController {
    @Autowired
    private AdminService adminService;

    //用于测试权限的接口
    @GetMapping("/test")
    public String test(){
        return "test";
    }


    @ApiOperation(value = "新增管理员", response = String.class)
    @PostMapping("/register")
    public String approve(@ApiParam(value = "admin") @RequestBody Admin admin){
        return adminService.save(admin)?"":"新增失败";
    }

    @ApiOperation(value = "删除管理员", response = String.class)
    @GetMapping("/delete")
    public String delete(@ApiParam(value = "id")String id){
        return adminService.removeById(id)?"":"删除失败";
    }

    @ApiOperation(value = "修改管理员权限", response = String.class)
    @GetMapping("/authorize")
    public String authorize(@ApiParam(value = "id")String id,
                            @ApiParam(value = "role")String role){

        return adminService.updateById(id,role)?"":"修改失败";
    }
}
