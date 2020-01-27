package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.UserRoleRepository;
import tim20.KTS_NVT.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/security")
public class SecurityController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository roleRepository;

    @RequestMapping(value = "/whoami", method = RequestMethod.GET)
    public UserDetails getUser() {
        Object a = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (a.equals("anonymousUser")) return null;
        return (UserDetails) a;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Boolean> login(@Valid @RequestBody User user) {
        try {
            userService.loginUser(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
