package af.dfi.core.model;

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
@Document(collection = "tenant")
public class Tenant {

    @Id
    private String id;
    @Field(value = "tenant_name")
    @Indexed(unique = true, dropDups = true)
    private String tenantName;
    @Field(value = "tenant_name")
    @Indexed(unique = true, dropDups = true)
    private String schemaName;

}
