package dancing.school.services;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IMessageTemplateService {

    GetMessageTemplateDTO createTemplate(Long teacherId, CreateMessageTemplateDTO dto) throws ResponseStatusException;

    GetMessageTemplateDTO getTemplateById(Long templateId)  throws ResponseStatusException;

    List<GetMessageTemplateDTO> getAllTemplatesByTeacherId(Long teacherId) throws ResponseStatusException;

    GetMessageTemplateDTO updateTemplate(Long teacherId, UpdateMessageTemplateDTO dto) throws ResponseStatusException;

    void  deleteTemplate(Long templateId) throws ResponseStatusException;


}
