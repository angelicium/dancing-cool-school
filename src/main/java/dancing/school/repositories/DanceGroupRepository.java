package dancing.school.repositories;

import dancing.school.entities.DanceGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DanceGroupRepository extends JpaRepository<DanceGroupEntity, Long> {
    List<DanceGroupEntity> findAllByTeacherId(Long teacherId);
}
