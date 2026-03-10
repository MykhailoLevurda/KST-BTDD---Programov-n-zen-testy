package cz.kst.btdd.api;

import cz.kst.btdd.persistence.EntityTool;
import cz.kst.btdd.persistence.ToolRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final ToolRepository toolRepository;

    public ToolController(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    @GetMapping
    public List<EntityTool> list() {
        return toolRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityTool> get(@PathVariable Long id) {
        return toolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
