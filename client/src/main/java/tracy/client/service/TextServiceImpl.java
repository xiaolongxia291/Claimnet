package tracy.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tracy.client.entity.Text;
import tracy.client.repository.TextRepository;
import tracy.client.util.PairResult;
import tracy.client.util.RedisUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TextServiceImpl implements TextService {
    @Autowired
    private TextRepository textRepository;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Text> text(String keyword, Integer curPage, Integer pageSize) {
        return textRepository.findTextsByIdOrApplicationNoOrContent(keyword, keyword, keyword, PageRequest.of(curPage - 1, pageSize));
    }

    @Override
    public PairResult pair(String id1, String id2) {
        Text text1 = textRepository.findTextById(id1);
        Text text2 = textRepository.findTextById(id2);
        //判断权利项是否都能检索到
        if (text1 == null || text2 == null || text1.getFeature() == null || text2.getFeature() == null) {
            return new PairResult("权利项信息缺失！", 0.0);
        }
        //获取特征向量
        String[] f1 = text1.getFeature().split(" ");
        double[] d1 = new double[f1.length];
        for (int i = 0; i < f1.length; ++i) {
            d1[i] = Double.parseDouble(f1[i]);
        }
        String[] f2 = text2.getFeature().split(" ");
        double[] d2 = new double[f2.length];
        for (int i = 0; i < f2.length; ++i) {
            d2[i] = Double.parseDouble(f2[i]);
        }
        //计算余弦相似度
        double A = 0.0D, B = 0.0D, sum = 0.0D;
        for (int i = 0; i < f1.length; ++i) {
            A += d1[i] * d1[i];
            B += d2[i] * d2[i];
            sum += d1[i] * d2[i];
        }
        double score = sum / (Math.sqrt(A) * Math.sqrt(B));
        return new PairResult("", score);
    }
}
