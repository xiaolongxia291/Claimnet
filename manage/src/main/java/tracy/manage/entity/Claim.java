package tracy.manage.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Document(indexName = "claim",createIndex = false)
@TableName("claim")
public class Claim implements Serializable {
    @Id
    @Field(name = "id",type = FieldType.Text)
    String id;
    @Field(name = "tree",type = FieldType.Text)
    String tree;
}
