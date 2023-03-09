package tracy.client.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(indexName = "claim",createIndex = false)
public class Claim implements Serializable {
    @Id
    @Field(name = "id",type = FieldType.Text)
    String id;
    @Field(name = "tree",type = FieldType.Text)
    String tree;

    @Override
    public String toString() {
        return "id:"+getId()+",tree:"+tree;
    }

    public String listToStr(List<Claim> list){
        StringBuilder builder=new StringBuilder();
        for(Claim obj:list){
            builder.append(obj.toString());
            builder.append("|");
        }
        return builder.toString();
    }

    public List<Claim> strToList(String str){
        String[] items=str.split("|");
        List<Claim> list=new ArrayList<>();
        for(String item:items){
            String[] obj=item.split(",");
            Claim c=new Claim();
            for(String o:obj){
                String[] temp=o.split(":");
                switch(temp[0]){
                    case "id":c.setId(temp[1]);
                    default:c.setTree(temp[1]);
                }
            }
            list.add(c);
        }
        return list;
    }

    public Claim strToClaim(String str){
        return null;
    }
}
