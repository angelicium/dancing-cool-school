package dancing.school.repositories;

import dancing.school.entities.DanceGroupEntity;
import dancing.school.entities.StudentDanceGroupEntity;
import dancing.school.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDanceGroupRepository extends JpaRepository<StudentDanceGroupEntity, Long> {
    List<StudentDanceGroupEntity> findAllByDanceGroupEntity (DanceGroupEntity danceGroupEntity);

    StudentDanceGroupEntity findByDanceGroupEntityAndStudent(DanceGroupEntity danceGroupEntity, StudentEntity student);
}
