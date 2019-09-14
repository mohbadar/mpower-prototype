package af.dfi.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "privilege")
public class Privilege{

    @Id
    private String id;
    @Field(value = "name")
    @Indexed(unique = true,dropDups = true)
    private String name;
//    private String description;

    public Privilege(String name)
    {
        this.name = name;
    }

}