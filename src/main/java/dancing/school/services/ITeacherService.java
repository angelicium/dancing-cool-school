package dancing.school.services;

import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.GetShortTeacherDTO;
import dancing.school.dto.GetTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;

import java.util.List;

public interface ITeacherService {

    GetTeacherDTO createTeacher(CreateTeacherDTO dto);

    List<GetShortTeacherDTO> getTeachers();

    GetTeacherDTO getTeacherById(Long id);

    void deleteTeacherById(Long id);

    GetTeacherDTO updateTeacher(Long id, UpdateTeacherDTO dto);
}
