package com.example.api.users;

import com.example.api.users.model.UserDTOConverter;
import com.example.api.users.model.User;
import com.example.api.users.model.UserDTO;
import com.example.api.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {


    public UserController(UserRepository userRepository, UserDTOConverter userDTOConverter) {
        this.userRepository = userRepository;
        this.userDTOConverter = userDTOConverter;
    }

    private final UserRepository userRepository;
    private final UserDTOConverter userDTOConverter;

    @GetMapping ("/all")
    public ResponseEntity<?> GetUsers() {
        List<User> Users= userRepository.findAll();

        if(Users.isEmpty()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There isn't any user yet.");

        }

        List<UserDTO> userDTOList = Users.stream()
                .map(userDTOConverter::makeUserDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDTOList);

    }

    @PostMapping("/")
    public  ResponseEntity<?> NewUser(@Valid @RequestBody User user) {
        User result = userRepository.save(user);
        result.setPassword("");
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
