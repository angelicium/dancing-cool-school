package dancing.school.repositories;

import dancing.school.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
