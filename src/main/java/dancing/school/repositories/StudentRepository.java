package dancing.school.repositories;

import dancing.school.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
   StudentEntity findStudentEntityByUsername(String username);
}
