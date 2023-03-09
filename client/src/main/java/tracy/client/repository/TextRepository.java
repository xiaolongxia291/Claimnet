package tracy.client.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tracy.client.entity.Text;
import java.util.List;

public interface TextRepository extends ElasticsearchRepository<Text,String> {
    List<Text> findTextsByIdOrApplicationNoOrContent(String id, String app, String keyword, PageRequest pageRequest);
    Text findTextById(String id);
}
