package af.dfi.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "camera")
public class Camera {

    @Id
    private String id;

    @Field(value = "name")
    @Indexed(unique = true,dropDups = true)
    private String name;

    @Field(value = "description")
    private String description;

    @Field(value = "cordinate_longtitude")
    private String longtitute;

    @Field(value = "cordinate_latitude")
    private String latitude;

    @DBRef
    private TerminalAddress terminalAddress;

}
