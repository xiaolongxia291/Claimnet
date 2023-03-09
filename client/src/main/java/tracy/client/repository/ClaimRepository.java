package tracy.client.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tracy.client.entity.Claim;

public interface ClaimRepository  extends ElasticsearchRepository<Claim,String> {
    Claim findClaimById(String id);
}
