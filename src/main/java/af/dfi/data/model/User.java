package af.dfi.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Document(collection = "user")
//@CompoundIndexes({
//        @CompoundIndex(name = "username_idx", def = "{'username': 1, 'password': -1}")
//})
public class User {

    @Id
    private String id;
    private String name;
    @Field
    @Indexed(unique = true, name = "email")
    private String email;
    @Field
    @Indexed(unique = true, name = "phone")
    private String phone;
    @Field
    @Indexed(unique = true, name = "username")
    private String username;
    @JsonIgnore
    private String password;
    private boolean enabled;
    @DBRef
    private List<Role> roles;

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }
}