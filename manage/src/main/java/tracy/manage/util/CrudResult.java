package tracy.manage.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CrudResult<T> {
    private String message;
    private List<T> failures;//插入失败的数据
}
