package af.dfi.core.service;


//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository repo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return repo
//                .findByUsername(username).map(u -> new org.springframework.security.core.userdetails.User(
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
//    }
//
//}
