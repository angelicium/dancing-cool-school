package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.TeacherEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ITeacherService {

    GetTeacherDTO createTeacher(CreateTeacherDTO dto) throws ResponseStatusException;

    TeacherEntity getTeacher(Long id) throws ResponseStatusException;

    List<GetShortTeacherDTO> getTeachers();

    GetTeacherDTO getTeacherById(Long id);

    void deleteTeacherById(Long id);

    GetTeacherDTO updateTeacher(Long id, UpdateTeacherDTO dto) throws ResponseStatusException;

    List<GetShortDanceGroupDTO> getDanceGroupsByTeacherId(Long id);

    GetDanceGroupDTO createDanceGroup(CreateDanceGroupDTO dto, Long idTeacher) throws ResponseStatusException;

    GetDanceGroupDTO updateDanceGroup(Long id, UpdateDanceGroupDTO dto) throws ResponseStatusException;

    void deleteDanceGroupById(Long id);

    GetDanceGroupDTO getDanceGroupById(Long id) throws ResponseStatusException;
}
