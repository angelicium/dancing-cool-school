package dancing.school.controllers;

import dancing.school.dto.*;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.services.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {

    private IStudentService studentService;

    @GetMapping ("/{id}")
    public ResponseEntity<ResponseDTO<GetStudentDTO>> getStudent(@PathVariable("id") Long id) {
        GetStudentDTO student = studentService.getStudentById(id);
        var responseDTO =  new ResponseDTO<GetStudentDTO>();
        responseDTO.setData(student);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<GetShortStudentDTO>>> getAllStudents() {
        List<GetShortStudentDTO> students = studentService.getStudents();
        var responseDTO =  new ResponseDTO<List<GetShortStudentDTO>>();
        responseDTO.setData(students);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<GetStudentDTO>> createStudent(@RequestBody CreateStudentDTO dto) {
        GetStudentDTO student = studentService.createStudent(dto);
        var responseDTO = new ResponseDTO<GetStudentDTO>();
        responseDTO.setData(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<GetStudentDTO>>  updateStudent(@RequestBody UpdateStudentDTO dto, @PathVariable("id") Long id) {
        GetStudentDTO student = studentService.updateStudent(id, dto);
        ResponseDTO<GetStudentDTO> response = ResponseDTO
                .<GetStudentDTO>builder()
                .data(student)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>>  deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{studentId}/requests")
    public ResponseEntity<ResponseDTO<List<GetShortRequestDTO>>> getRequests(@PathVariable("studentId") Long studentId,
                                                                             @RequestParam("status") StatusRequestEnum status) {
        List<GetShortRequestDTO> requests = studentService.getRequests(studentId, status);

        var responseDTO = new ResponseDTO<List<GetShortRequestDTO>>();
        responseDTO.setData(requests);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{studentId}/templates")
    public ResponseEntity<ResponseDTO<List<GetMessageTemplateDTO>>> getMessageTemplates(@PathVariable("studentId") Long studentId) {
        List<GetMessageTemplateDTO> templates = studentService.getMessageTemplates(studentId);
        var responseDTO = new ResponseDTO<List<GetMessageTemplateDTO>>();
        responseDTO.setData(templates);
        return ResponseEntity.ok(responseDTO);
    }
}
