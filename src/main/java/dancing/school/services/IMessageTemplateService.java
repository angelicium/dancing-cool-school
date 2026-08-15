package dancing.school.services;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IMessageTemplateService {

    GetMessageTemplateDTO createTemplate(CreateMessageTemplateDTO dto) throws ResponseStatusException;

    GetMessageTemplateDTO getTemplateById(Long templateId)  throws ResponseStatusException;

   GetMessageTemplateDTO updateTemplate(Long templateId, UpdateMessageTemplateDTO dto) throws ResponseStatusException;

    void  deleteTemplate(Long templateId) throws ResponseStatusException;
}
