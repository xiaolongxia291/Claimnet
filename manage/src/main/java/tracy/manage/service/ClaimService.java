package tracy.manage.service;

import tracy.manage.entity.Claim;
import tracy.manage.util.CrudResult;

import java.util.List;

public interface ClaimService {
    void clear();

    CrudResult<Claim> insert(List<Claim> docs);

    void delete(List<String> ids);

    List<Claim> selectAll(int curPage, int pageSize);

    Claim selectClaimById(String id);

    void update(Claim claim);
}
