package af.dfi.core.service;


import af.dfi.data.model.Privilege;
import af.dfi.data.model.Role;
import af.dfi.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return repo
//                .findByUsername(username).map(u -> User(
//                u.getUsername(),
//                u.getPassword(),
//                u.isActive(),
//                u.isActive(),
//                u.isActive(),
//                u.isActive(),
//                AuthorityUtils.createAuthorityList(
//                        u.getRoles()
//                                .stream()
//                                .map(r -> "ROLE_" + r.getName().toUpperCase())
//                                .collect(Collectors.toList())
//                                .toArray(new String[]{}))))
//                .orElseThrow(() -> new UsernameNotFoundException("No user with "
//                + "the name " + username + "was found in the database"));

        af.dfi.data.model.User user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isEnabled(), true, true, true,
                //  getAuthorities(user.getGroups()));
                getAuthorities(user.getRoles()));

        return userDetails;
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        // make a hashset to remove the duplicated roles
        HashSet hs = new HashSet();
        hs.addAll(roles);
        roles.clear();
        roles.addAll(hs);

        return getGrantedAuthorities(getPermissions(roles));
    }

    private List<String> getPermissions(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
