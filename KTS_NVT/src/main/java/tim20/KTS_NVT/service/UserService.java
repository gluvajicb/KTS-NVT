package tim20.KTS_NVT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.UserRepository;
import tim20.KTS_NVT.repository.UserRoleRepository;
import tim20.KTS_NVT.security.TokenHelper;
import tim20.KTS_NVT.security.UserTokenState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenHelper tokenHelper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);
        return user;
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRoles().getName()));

//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean loginUser(User user) {
        try {
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            User authenticatedUser = (User) authentication.getPrincipal();
            if (authenticatedUser != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenHelper.generateToken(user.getUsername());

                UserTokenState tokenState = new UserTokenState(jwt, 43200);
                // return token state to user
                return true;
            }
        } catch (Exception e) {
            String a = e.getMessage();
        }

        return false;
    }

    public boolean registerUser(User user) {
        if (user.getName().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()
                || user.getSurname().isEmpty() || user.getUsername().isEmpty()) {
            //return "All fields are required!";
            return false;
        }

        if (findByUsername(user.getUsername()) != null) {
//            return "Username already taken!";
            return false;
        }

        if (findByEmail(user.getEmail()) != null) {
//            return "Email already taken!";
            return false;
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN"))));
        userRepository.save(user);

        return true;
    }
}