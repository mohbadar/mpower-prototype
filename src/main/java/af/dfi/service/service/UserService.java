package af.dfi.service.service;

import af.dfi.core.model.User;
import af.dfi.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll()
    {
        return userRepository.findAll();
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone)
    {
        return userRepository.findByPhone(phone);
    }

    public User save(User user)
    {
        return userRepository.save(user);
    }

    public void delete(String id)
    {
         userRepository.deleteById(id);
    }

    public User findById(String id)
    {
        return userRepository.findById(id).get();
    }

    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }

    public User getLoggedInUser(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return findByUsername(username);
    }

    public String getSecurityContextHolderUsername(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return username;
    }

    public boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> auths = ((UserDetails) principal).getAuthorities();
        if (auths.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return true;
        }

        return false;

    }

}
