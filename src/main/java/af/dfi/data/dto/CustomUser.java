package af.dfi.data.dto;

import lombok.*;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
@Setter
public class CustomUser extends User {

    private static final long serialVersionUID = -3531439484732724601L;

    private String currentEnv;
    private String currentLang;

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean credentialsNonExpired, boolean accountNonLocked,
                      Collection<? extends GrantedAuthority> authorities, String currentEnv, String currentLang) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

        this.currentEnv = currentEnv;
        this.currentLang = currentLang;
    }


}
