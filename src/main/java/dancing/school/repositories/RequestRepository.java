package dancing.school.repositories;

import dancing.school.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByTeacherId(Long teacherId);

    RequestEntity findByTeacherIdAndStudentId(Long teacherId, Long studentId);
}
