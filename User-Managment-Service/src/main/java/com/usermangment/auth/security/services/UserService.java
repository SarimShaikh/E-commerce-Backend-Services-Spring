package com.usermangment.auth.security.services;

import com.usermangment.auth.entities.*;
import com.usermangment.auth.exceptions.ResourceNotFoundException;
import com.usermangment.auth.messageDto.request.AddPrivilegeRequest;
import com.usermangment.auth.messageDto.request.LoginRequest;
import com.usermangment.auth.messageDto.request.SignUpRequest;
import com.usermangment.auth.messageDto.response.JwtResponse;
import com.usermangment.auth.repository.PrivilegeRepository;
import com.usermangment.auth.repository.RoleRepository;
import com.usermangment.auth.repository.UserRepository;
import com.usermangment.auth.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PrivilegeRepository privilegeRepository;

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        Long userId = (Long) ((UserPrinciple) ((UsernamePasswordAuthenticationToken) authentication).getPrincipal()).getId();
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userId, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    public ResponseEntity<String> registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()),signUpRequest.getContact(),(byte)1,signUpRequest.getIsCustomer());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch(role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);

                    break;
                case "user":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(pmRole);

                    break;
                default:
                     throw new RuntimeException("Fail! -> Cause: User Role not find.");
                    //roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }

    public ResponseEntity<User> getEmployeeById(Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.getUserByUserId(userId);
        if(user==null){
            new ResourceNotFoundException("User not found for this Id :: " + userId);
        }
        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity<User> updateUser(Long userId, User userDetail) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setUserId(userDetail.getUserId());
        user.setEmail(userDetail.getEmail());
        user.setUsername(userDetail.getUsername());
        user.setContact(userDetail.getContact());
        user.setAddress(userDetail.getAddress());
        user.setRoles(userDetail.getRoles());

        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    public ResponseEntity<String> deleteUser(Long userId)throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        userRepository.delete(user);
        return ResponseEntity.ok().body("User delete successfully!");
    }

    public List<User> getUserByType(String isCustomer){
        return userRepository.getAllByIsCustomer(isCustomer);
    }

    public List<Privilege> privilegeList(){
        return privilegeRepository.findAll();
    }

    public ResponseEntity<String> addRolePrivileges(AddPrivilegeRequest privilegeRequest) throws ResourceNotFoundException {

        List<Privilege> privilegeList = privilegeList();
        List<Privilege> rolePrivilegesList = new ArrayList<>();
        privilegeList.removeIf(x->!privilegeRequest.getPrivileges().contains(x.getName()));

        Role userRole = roleRepository.findByName(privilegeRequest.getRoleName())
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));

        for(int i=0; i<privilegeList.size(); i++){
            if(privilegeList.get(i).getName().equals(privilegeRequest.getPrivileges().get(i))){
                Privilege privilege =new Privilege();
                privilege.setPrivilegeId(privilegeList.get(i).getPrivilegeId());
                privilege.setName(privilegeList.get(i).getName());
                rolePrivilegesList.add(privilege);
            }
        }

        userRole.setPrivileges(rolePrivilegesList);
        roleRepository.save(userRole);

        return ResponseEntity.ok().body("Privileges successfully assign to this role");
    }

    public ResponseEntity<Role> getRoleByName(RoleName roleName) throws ResourceNotFoundException {
            Role userRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new ResourceNotFoundException("Fail! -> Cause: User Role not find."));
            return ResponseEntity.ok().body(userRole);
    }

}
