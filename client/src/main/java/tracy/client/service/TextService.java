package tracy.client.service;

import tracy.client.entity.Text;
import tracy.client.util.PairResult;

import java.util.List;

public interface TextService {
    List<Text> text(String keyword,
                    Integer curPage,
                    Integer pageSize);

    PairResult pair(String id1, String id2);
}
