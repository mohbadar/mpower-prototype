package af.dfi.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
@Document(collection = "role")
public class Role {

    @Id
    private String id;
    @NotNull
    @Field(value = "name")
    @Indexed(unique = true,dropDups = true)
    private String name;
    @NotNull
    @Field(value = "description")
    private String description;


    @DBRef
//    @CascadeSave
    private List<Privilege> privileges;

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, List<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

}