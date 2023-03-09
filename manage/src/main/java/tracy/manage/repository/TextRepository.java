package tracy.manage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import tracy.manage.entity.Text;

import java.util.List;

public interface TextRepository extends ElasticsearchRepository<Text,String> {
    void deleteAll();
    void save(List<Text> docs);
    boolean existsById(String id);
    void deleteTextsByIdIn(List<String> ids);
    Page<Text> findAll(Pageable pageable);
    List<Text> findTextsByIdOrApplicationNo(String id, String app, PageRequest pageRequest);
}
