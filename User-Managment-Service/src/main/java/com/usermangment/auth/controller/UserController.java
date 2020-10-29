package com.usermangment.auth.controller;

import com.usermangment.auth.entities.Privilege;
import com.usermangment.auth.entities.Role;
import com.usermangment.auth.entities.RoleName;
import com.usermangment.auth.entities.User;
import com.usermangment.auth.exceptions.ResourceNotFoundException;
import com.usermangment.auth.messageDto.request.AddPrivilegeRequest;
import com.usermangment.auth.messageDto.request.LoginRequest;
import com.usermangment.auth.messageDto.request.SignUpRequest;
import com.usermangment.auth.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> UserAuthentication(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> UserRegistration(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.registerUser(signUpRequest);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") Long userId)
            throws ResourceNotFoundException {
        return userService.getEmployeeById(userId);
    }

    @PostMapping("/update-user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") Long userId, @Valid @RequestBody User userDetails)
            throws ResourceNotFoundException {
        return userService.updateUser(userId,userDetails);
    }

    @GetMapping("/user/privileges")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Privilege> getAllPrivilege(){
        return userService.privilegeList();
    }

    @GetMapping("/user/role-privileges")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getPrivilegesByRoleName(@RequestParam(name = "roleName") String roleName) throws ResourceNotFoundException{
        RoleName roleEnum= RoleName.valueOf(roleName);
        return userService.getRoleByName(roleEnum);
    }

    @PostMapping("/assign/privileges")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addRolePrivileges(@Valid @RequestBody AddPrivilegeRequest privilegeRequest) throws ResourceNotFoundException{
        return userService.addRolePrivileges(privilegeRequest);
    }
}
