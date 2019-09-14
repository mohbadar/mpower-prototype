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
@Document(collection = "agent_location")
public class Agent {

    @Id
    private String id;

    @Field(value = "name")
    @Indexed(unique = true,dropDups = true)
    private String name;

    @Field(value = "description")
    private String description;

    @Field(value = "ip")
    private String ip;

    @Field(value = "port")
    private String port;

    @DBRef
    private AgentLocation agentLocation;

}
