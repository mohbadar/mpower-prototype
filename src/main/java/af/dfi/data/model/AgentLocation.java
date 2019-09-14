package af.dfi.data.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "agent_location_test")
public class AgentLocation {

    @Id
    private String id;

    @NotNull
    @Field(value = "city")
    private String city;

    @NotNull
    @Field(value = "district")
    private String district;

    @NotNull
    @Field(value = "area")
    private String area;

    @NotNull
    @Field(value = "cross_road")
    private String crossRoad;

    @NotNull
    @Field(value = "street_address")
    private String streetAddress;

}
