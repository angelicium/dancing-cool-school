package dancing.school.repositories;

import dancing.school.entities.MessageTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageTemplateRepository extends JpaRepository<MessageTemplateEntity, Long> {
    List<MessageTemplateEntity> findAllByTeacherId(Long teacherId);
}
