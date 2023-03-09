package tracy.manage.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PairResult {
    private String message;//异常信息
    private double score;
}
