package cz.kst.btdd.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<EntityUser, Long> {
}
