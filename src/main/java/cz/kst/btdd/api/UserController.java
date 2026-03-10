package cz.kst.btdd.api;

import cz.kst.btdd.persistence.EntityUser;
import cz.kst.btdd.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<EntityUser> list() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityUser> get(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
