package af.dfi.core.service;

import af.dfi.data.model.User;
import af.dfi.data.repository.UserRepository;
import af.dfi.lang.aspect.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Loggable
    @Retryable
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    @Loggable
    public List<User> findAll()
    {
        return userRepository.findAll();
    }
    @Loggable
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }
    @Loggable
    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }
    @Loggable
    public User findByPhone(String phone)
    {
        return userRepository.findByPhone(phone);
    }


    @Loggable
    public void delete(String id)
    {
         userRepository.deleteById(id);
    }
    @Loggable
    public User findById(String id)
    {
        return userRepository.findById(id).get();
    }
    @Loggable
    public User getLoggedInUser() {
        return getLoggedInUser(false);
    }
    @Loggable
    public User getLoggedInUser(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return findByUsername(username);
    }
    @Loggable
    public String getSecurityContextHolderUsername(Boolean forceFresh) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }

        return username;
    }
    @Loggable
    public boolean isAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> auths = ((UserDetails) principal).getAuthorities();
        if (auths.contains(new SimpleGrantedAuthority("ADMIN"))) {
            return true;
        }

        return false;

    }

}
