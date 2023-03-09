package tracy.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;


@TableName("admin")
@Data
@AllArgsConstructor
public class Admin {
    private String id;
    private String username;
    private String password;
    private String role;

}
