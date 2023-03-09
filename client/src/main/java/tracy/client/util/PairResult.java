package tracy.client.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class PairResult {
    private String message;//异常信息
    private double score;
}
