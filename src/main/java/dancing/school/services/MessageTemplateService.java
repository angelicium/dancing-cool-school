package dancing.school.services;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import dancing.school.entities.MessageTemplateEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.MessageTemplateMapper;
import dancing.school.repositories.MessageTemplateRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageTemplateService implements IMessageTemplateService {

    private MessageTemplateRepository templateRepository;

    private MessageTemplateMapper templateMapper;

    private ITeacherService teacherService;

    private MessageTemplateEntity findTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Шаблон не найден с айди " + id
                ));
    }

    @Override
    public GetMessageTemplateDTO createTemplate(Long teacherId, CreateMessageTemplateDTO dto) throws ResponseStatusException {
        TeacherEntity teacher = teacherService.getTeacher(teacherId);
        MessageTemplateEntity entity = templateMapper.toEntity(dto);
        entity.setTeacher(teacher);
        MessageTemplateEntity savedEntity = templateRepository.save(entity);
        return templateMapper.toDto(savedEntity);

    }

    @Override
    public GetMessageTemplateDTO getTemplateById(Long templateId) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        return templateMapper.toDto(entity);
    }

    @Override
    public List<GetMessageTemplateDTO> getAllTemplatesByTeacherId(Long teacherId) throws ResponseStatusException {
        List<MessageTemplateEntity> entities = templateRepository.findAllByTeacherId(teacherId);
        return templateMapper.toDtos(entities);
    }

    @Override
    public GetMessageTemplateDTO updateTemplate(Long templateId, UpdateMessageTemplateDTO dto) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        templateMapper.updateEntityFromDto(dto, entity);
        MessageTemplateEntity updatedEntity = templateRepository.save(entity);
        return templateMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteTemplate(Long templateId) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        templateRepository.delete(entity);
    }
}
