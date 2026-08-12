package dancing.school.controllers;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.ResponseDTO;
import dancing.school.services.IMessageTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MessageTemplateController {

    private IMessageTemplateService templateService;

    @PostMapping("/teachers/{teacherId}/templates")
    public ResponseEntity<ResponseDTO<GetMessageTemplateDTO>> createTemplate(
                                            @PathVariable("teacherId") Long teacherId,
                                            @RequestBody CreateMessageTemplateDTO dto) {
        GetMessageTemplateDTO  templateDTO = templateService.createTemplate(teacherId, dto);
        var response = new ResponseDTO<GetMessageTemplateDTO>();
        response.setData(templateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
