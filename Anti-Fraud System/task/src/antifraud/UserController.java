package antifraud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerUser(@RequestBody NewUserDTO newUserDTO) {
        if (newUserDTO.name() == null || newUserDTO.name().isEmpty() ||
                newUserDTO.username() == null || newUserDTO.username().isEmpty() ||
                newUserDTO.password() == null || newUserDTO.password().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid data"));
        }

        try {
            User user = userService.registerUser(newUserDTO.name(), newUserDTO.username(), newUserDTO.password());
            UserDto responseDto = new UserDto();
            responseDto.setId(user.getId());
            responseDto.setName(user.getName());
            responseDto.setUsername(user.getUsername());
            responseDto.setRole(user.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> listUsers() {
        List<User> users = userService.listUsers();
        List<UserDto> userDtos = users.stream().map(user -> {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setUsername(user.getUsername());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        boolean deleted = userService.deleteUser(username);
        if (deleted) {
            return ResponseEntity.ok(Map.of("username", username, "status", "Deleted successfully!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
    }
}


/*Gebruikerscontroller (UserController):
Deze controller is verantwoordelijk voor alles wat met gebruikers te maken heeft.
Het kan bijvoorbeeld nieuwe gebruikers registreren, een lijst van gebruikers tonen,
en gebruikers verwijderen.
 */