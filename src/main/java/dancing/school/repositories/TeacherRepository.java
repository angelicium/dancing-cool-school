package dancing.school.repositories;

import dancing.school.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    TeacherEntity findTeacherEntityByUsername(String username);
}
