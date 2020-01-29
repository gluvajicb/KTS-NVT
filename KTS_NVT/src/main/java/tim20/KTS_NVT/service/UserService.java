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
import tim20.KTS_NVT.exceptions.EmailInUseException;
import tim20.KTS_NVT.exceptions.FieldsRequiredException;
import tim20.KTS_NVT.exceptions.UsernameTakenException;
import tim20.KTS_NVT.exceptions.WrongCredentialsException;
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

    public UserTokenState loginUser(User user) {
        try {
            if (user.getUsername() == null || user.getUsername().trim().equals("")
                || user.getPassword() == null || user.getPassword().trim().equals("")) {
                throw new FieldsRequiredException();
            }

            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            User authenticatedUser = (User) authentication.getPrincipal();
            if (authenticatedUser != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

                String jwt = tokenHelper.generateToken(user.getUsername());

                return new UserTokenState(jwt, 43200);
            }
        } catch (Exception e) {
            throw new WrongCredentialsException();
        }

        return null;
    }

    public void registerUser(User user) {
        if (user.getName() == null || user.getName().isEmpty() || user.getSurname() == null || user.getSurname().isEmpty()
            || user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()
            || user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new FieldsRequiredException();
        }

        if (findByUsername(user.getUsername()) != null) {
            throw new UsernameTakenException();
        }

        if (findByEmail(user.getEmail()) != null) {
            throw new EmailInUseException();
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN"))));
        userRepository.save(user);
    }
}