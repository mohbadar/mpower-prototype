package af.dfi.core.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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
public class User implements UserDetails {

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
    private String password;
    private boolean enabled;
    @DBRef
    private List<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}