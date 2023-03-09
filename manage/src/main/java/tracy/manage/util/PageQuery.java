package tracy.manage.util;

import lombok.Data;

@Data
public class PageQuery<T> {
    int curPage;
    int pageSize;
    T condition;
    public PageQuery(int curPage,int pageSize,T condition){
        this.curPage=curPage<=0?1:curPage;
        this.pageSize=pageSize<=0?10:pageSize;
        this.condition=condition;
    }
}
