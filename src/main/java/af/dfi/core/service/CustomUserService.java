package af.dfi.core.service;
//
import org.springframework.stereotype.Component;

@Component
public class CustomUserService {

}
//@Service
//public class CustomUserService  implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PrivilegeService privilegeService;
//
//    @Autowired
//    private RoleService roleService;
//
//
//    public User loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities() {
//        HashSet<Role> roles = new HashSet<>();
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    private List<String> getPrivileges(Collection<Role> roles) {
//        List<String> privileges = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
//        for (Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(Collection<Privilege> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Privilege priv : privileges) {
//            authorities.add(new SimpleGrantedAuthority(priv.getName()));
//        }
//        return authorities;
//    }
//
//}
