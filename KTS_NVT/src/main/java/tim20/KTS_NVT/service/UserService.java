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
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.exceptions.*;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.UserRepository;
import tim20.KTS_NVT.repository.UserRoleRepository;
import tim20.KTS_NVT.security.TokenHelper;
import tim20.KTS_NVT.security.UserTokenState;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    @Autowired
    private EmailService emailService;

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

    public UserTokenState loginUser(UserDTO user) {
        if (user.getUsername() == null || user.getUsername().trim().equals("")
                || user.getPassword() == null || user.getPassword().trim().equals("")) {
            throw new FieldsRequiredException();
        }

        try {
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            if (authentication != null && authentication.getName() != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = tokenHelper.generateToken(authentication.getName());
                return new UserTokenState(jwt, 43200);
            }
        } catch (Exception e) {
            throw new WrongCredentialsException();
        }

        return null;
    }

    public void registerUser(UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().isEmpty() || userDTO.getSurname() == null || userDTO.getSurname().isEmpty()
                || userDTO.getUsername() == null || userDTO.getUsername().isEmpty() || userDTO.getEmail() == null || userDTO.getEmail().isEmpty()
                || userDTO.getPassword() == null || userDTO.getPassword().isEmpty()
                || userDTO.getPasswordConfirmation() == null || userDTO.getPasswordConfirmation().isEmpty()) {
            throw new FieldsRequiredException();
        }

        if (findByUsername(userDTO.getUsername()) != null) {
            throw new UsernameTakenException();
        }

        if (findByEmail(userDTO.getEmail()) != null) {
            throw new EmailInUseException();
        }

        if (!userDTO.getPassword().equals(userDTO.getPasswordConfirmation())) {
            throw new PasswordsNotMatchingException();
        }

        User user = new User(0, userDTO.getUsername(), userDTO.getPassword(), userDTO.getName(),
                userDTO.getSurname(), userDTO.getEmail(), false, null, null);

        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByRole("ADMIN"))));

        emailService.sendVerificationEmail(user);
        userRepository.save(user);
    }

    public boolean verifyAccount(String email, String token) {
        if (email == null || email.isEmpty() || token == null || token.isEmpty()) {
            throw new FieldsRequiredException();
        }

        try {
            // This decoding is added to decode email address passed from test case
            // where email has special characters ie. vule97+petar@gmail.com
            email = URLDecoder.decode(email, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = userRepository.findByEmailAndVerificationToken(email, token);
        if (user != null) {
            user.setConfirmed(true);
            user.setVerificationToken("");
            userRepository.save(user);
            return true;
        }

        throw new WrongVerificationTokenAndEmail();
    }
}