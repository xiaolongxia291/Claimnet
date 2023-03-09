package tracy.manage.service;

import tracy.manage.entity.Text;
import tracy.manage.util.CrudResult;
import tracy.manage.util.PageQuery;

import java.util.List;

public interface TextService {
    void clear();

    CrudResult<Text> insert(List<Text> docs);

    void delete(List<String> ids);

    List<Text> selectAll(int curPage, int pageSize);

    List<Text> selectCondition(PageQuery<Text> pageQuery);

    void update(Text text);
}
