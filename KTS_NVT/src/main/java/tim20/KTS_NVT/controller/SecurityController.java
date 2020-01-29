package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.exceptions.*;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.repository.UserRoleRepository;
import tim20.KTS_NVT.security.UserTokenState;
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
    public ResponseEntity<UserTokenState> login(@Valid @RequestBody UserDTO user) {
        UserTokenState tokenState = userService.loginUser(user);
        return new ResponseEntity<>(tokenState, HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Boolean> register(@Valid @RequestBody UserDTO user) {
        userService.registerUser(user);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public ResponseEntity<Boolean> verifyAccount(@RequestParam String email, @RequestParam String token) {
        return new ResponseEntity<Boolean>(userService.verifyAccount(email, token), HttpStatus.OK);
    }

    @ExceptionHandler(FieldsRequiredException.class)
    public ResponseEntity<Error> fieldsRequired()
    {
        Error error = new Error(1,"All fields must be provided.");
        return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCredentialsException.class)
    public ResponseEntity<Error> wrongCredentials()
    {
        Error error = new Error(1,"Provided credentials don't match with any account.");
        return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<Error> usernameTaken()
    {
        Error error = new Error(1,"Username is taken, please choose another one.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<Error> emailInUse()
    {
        Error error = new Error(1,"Email is already in use, try logging in.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordsNotMatchingException.class)
    public ResponseEntity<Error> passwordsDontMatch()
    {
        Error error = new Error(1,"Password confirmation is incorrect.");
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
}
