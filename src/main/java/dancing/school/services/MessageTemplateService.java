package dancing.school.services;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import dancing.school.entities.MessageTemplateEntity;
import dancing.school.entities.StudentEntity;
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

    private IStudentService studentService;

    private MessageTemplateEntity findTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Шаблон не найден с айди " + id
                ));
    }

    @Override
    public GetMessageTemplateDTO createTemplate(CreateMessageTemplateDTO dto) throws ResponseStatusException {
        MessageTemplateEntity entity = templateMapper.toEntity(dto);
        if (dto.getTeacherId() != null) {
            TeacherEntity teacher = teacherService.getTeacher(dto.getTeacherId());
            entity.setTeacher(teacher);
        } else if (dto.getStudentId() != null) {
            StudentEntity student = studentService.getStudent(dto.getStudentId());
            entity.setStudent(student);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Должен быть указан владелец шаблона"
            );
        }
        templateRepository.save(entity);
        return templateMapper.toDto(entity);
    }

    @Override
    public GetMessageTemplateDTO getTemplateById(Long templateId) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        return templateMapper.toDto(entity);
    }

    @Override
    public GetMessageTemplateDTO updateTemplate(Long templateId, UpdateMessageTemplateDTO dto) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        templateMapper.updateEntityFromDto(dto, entity);
        templateRepository.save(entity);
        return templateMapper.toDto(entity);
    }

    @Override
    public void deleteTemplate(Long templateId) throws ResponseStatusException {
        MessageTemplateEntity entity = findTemplateById(templateId);
        templateRepository.delete(entity);
    }
}
