package dancing.school.controllers;

import dancing.school.dto.CreateDanceGroupDTO;
import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.GetDanceGroupDTO;
import dancing.school.dto.GetShortDanceGroupDTO;
import dancing.school.dto.GetShortTeacherDTO;
import dancing.school.dto.GetTeacherDTO;
import dancing.school.dto.ResponseDTO;
import dancing.school.dto.UpdateDanceGroupDTO;
import dancing.school.dto.UpdateTeacherDTO;
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

    @PostMapping
    public ResponseEntity<ResponseDTO<GetTeacherDTO>> createTeacher(@RequestBody CreateTeacherDTO dto) {
        GetTeacherDTO teacher = teacherService.createTeacher(dto);
        var responseDTO = new ResponseDTO<GetTeacherDTO>();
        responseDTO.setData(teacher);
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
}
