package dancing.school.controllers;

import dancing.school.dto.*;
import dancing.school.services.ITeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@AllArgsConstructor
public class TeacherController {

    private ITeacherService teacherService;

    @PostMapping("/auth/register")
    public ResponseEntity<ResponseDTO<GetJwtDTO>> createTeacher(@RequestBody CreateTeacherDTO dto) {
        GetJwtDTO jwt = teacherService.createTeacher(dto);
        var responseDTO = new ResponseDTO<GetJwtDTO>();
        responseDTO.setData(jwt);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<GetShortTeacherDTO>>> getTeachers() {
        List<GetShortTeacherDTO> teachers = teacherService.getTeachers();
        var responseDTO = new ResponseDTO<List<GetShortTeacherDTO>>();
        responseDTO.setData(teachers);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<GetTeacherDTO>> getTeacher(@PathVariable("id") Long id) {
        GetTeacherDTO teacher = teacherService.getTeacherById(id);
       var responseDTO =  new ResponseDTO<GetTeacherDTO>();
       responseDTO.setData(teacher);
       return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteTeacher(@PathVariable("id") Long id) {
        this.teacherService.deleteTeacherById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<GetTeacherDTO>> updateTeacher(@RequestBody UpdateTeacherDTO dto, @PathVariable("id") Long id) {
        GetTeacherDTO teacher = this.teacherService.updateTeacher(id, dto);
        var responseDTO = new ResponseDTO<GetTeacherDTO>();
        responseDTO.setData(teacher);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}/groups")
    public ResponseEntity<ResponseDTO<List<GetShortDanceGroupDTO>>> getTeacherGroups(@PathVariable("id") Long id) {
        List<GetShortDanceGroupDTO> groups = teacherService.getDanceGroupsByTeacherId(id);
        var responseDTO = new ResponseDTO<List<GetShortDanceGroupDTO>>();
        responseDTO.setData(groups);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/groups/{idGroup}")
    public ResponseEntity<ResponseDTO<GetDanceGroupDTO>> getDanceGroupById(@PathVariable("idGroup") Long idGroup) {
        GetDanceGroupDTO group = teacherService.getDanceGroupById(idGroup);
        var responseDTO = new ResponseDTO<GetDanceGroupDTO>();
        responseDTO.setData(group);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{idTeacher}/groups")
    public ResponseEntity<ResponseDTO<GetDanceGroupDTO>> createDanceGroup(@RequestBody CreateDanceGroupDTO dto,
                                                                          @PathVariable("idTeacher") Long idTeacher) {
        GetDanceGroupDTO group = teacherService.createDanceGroup(dto,idTeacher);
        var responseDTO = new ResponseDTO<GetDanceGroupDTO>();
        responseDTO.setData(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/groups/{idGroup}")
    public ResponseEntity<ResponseDTO<GetDanceGroupDTO>>  updateDanceGroup(@RequestBody UpdateDanceGroupDTO dto,
                                                                           @PathVariable("idGroup") Long idGroup) {
        GetDanceGroupDTO group = teacherService.updateDanceGroup(idGroup, dto);
        var responseDTO = new ResponseDTO<GetDanceGroupDTO>();
                responseDTO.setData(group);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/groups/{idGroup}")
    public ResponseEntity<ResponseDTO<Void>> deleteDanceGroup(@PathVariable("idGroup") Long id) {
        teacherService.deleteDanceGroupById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/groups/{idGroup}/student/{idStudent}")
    public ResponseEntity<Void> removeStudentFromDanceGroup(@PathVariable("idStudent") Long studentId,
                                                            @PathVariable("idGroup") Long danceGroupId,
                                                            @RequestBody RemovedStudentDTO dto) {
        teacherService.removeStudentFromDanceGroup(studentId, danceGroupId, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{teacherId}/templates")
    public ResponseEntity<ResponseDTO<List<GetMessageTemplateDTO>>> getTeacherTemplates(@PathVariable("teacherId") Long teacherId) {
       List<GetMessageTemplateDTO> templates = teacherService.getTeacherTemplates(teacherId);
       var responseDTO = new ResponseDTO<List<GetMessageTemplateDTO>>();
       responseDTO.setData(templates);
       return ResponseEntity.ok(responseDTO);
    }
}
