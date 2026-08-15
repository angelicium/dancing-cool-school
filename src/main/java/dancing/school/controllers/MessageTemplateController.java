package dancing.school.controllers;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.ResponseDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import dancing.school.services.IMessageTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
@AllArgsConstructor
public class MessageTemplateController {

    private IMessageTemplateService templateService;

    @PostMapping
    public ResponseEntity<ResponseDTO<GetMessageTemplateDTO>> createTemplate(@RequestBody CreateMessageTemplateDTO dto) {
        GetMessageTemplateDTO  templateDTO = templateService.createTemplate(dto);
        var response = new ResponseDTO<GetMessageTemplateDTO>();
        response.setData(templateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<ResponseDTO<GetMessageTemplateDTO>> getTemplateById(@PathVariable("templateId") Long templateId){
        GetMessageTemplateDTO templateDTO = templateService.getTemplateById(templateId);
        var response = new ResponseDTO<GetMessageTemplateDTO>();
        response.setData(templateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{templateId}")
    public ResponseEntity<ResponseDTO<GetMessageTemplateDTO>> updateTemplate(@PathVariable("templateId") Long templateId,
                                                                             @RequestBody UpdateMessageTemplateDTO dto) {
        GetMessageTemplateDTO templateDto = templateService.updateTemplate(templateId, dto);
        var response = new ResponseDTO<GetMessageTemplateDTO>();
        response.setData(templateDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void>  deleteTemplate(@PathVariable("templateId") Long templateId) {
        templateService.deleteTemplate(templateId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
