package cz.kst.btdd.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<EntityTool, Long> {
}
