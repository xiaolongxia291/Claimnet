package tracy.manage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tracy.manage.entity.Claim;

import java.util.List;

public interface ClaimRepository extends ElasticsearchRepository<Claim,String> {
    void deleteAll();
    void save(List<Claim> docs);
    void deleteClaimsByIdIn(List<String> ids);
    Page<Claim> findAll(Pageable pageable);
    Claim findClaimById(String id);
}
