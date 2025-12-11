package myTunes.controller;

import lombok.AllArgsConstructor;
import myTunes.business.user.CreateUserUseCase;
import myTunes.business.user.DeleteUserUseCase;
import myTunes.business.user.GetUserUseCase;
import myTunes.business.user.UpdateUserUseCase;
import myTunes.domain.user.CreateUserRequest;
import myTunes.domain.user.CreateUserResponse;
import myTunes.domain.user.UpdateUserRequest;
import myTunes.domain.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final CreateUserUseCase createUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable("id") long id, @RequestBody @Valid UpdateUserRequest request){
        request.setId(id);
        updateUserUseCase.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        deleteUserUseCase.deleteUser(id);
        deleteUserUseCase.sendMessage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") final long id) {
        final Optional<User> userOptional = getUserUseCase.getUser(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(userOptional.get());
    }
}
