package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.DanceGroupEntity;
import dancing.school.entities.TeacherEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ITeacherService {

    GetJwtDTO createTeacher(CreateTeacherDTO dto) throws ResponseStatusException;

    TeacherEntity getTeacher(Long id) throws ResponseStatusException;

    List<GetShortTeacherDTO> getTeachers();

    GetTeacherDTO getTeacherById(Long id);

    void deleteTeacherById(Long id);

    GetTeacherDTO updateTeacher(Long id, UpdateTeacherDTO dto) throws ResponseStatusException;

    List<GetShortDanceGroupDTO> getDanceGroupsByTeacherId(Long id);

    DanceGroupEntity getDanceGroup(Long id) throws ResponseStatusException;

    GetDanceGroupDTO createDanceGroup(CreateDanceGroupDTO dto, Long idTeacher) throws ResponseStatusException;

    GetDanceGroupDTO updateDanceGroup(Long id, UpdateDanceGroupDTO dto) throws ResponseStatusException;

    void deleteDanceGroupById(Long id);

    GetDanceGroupDTO getDanceGroupById(Long id) throws ResponseStatusException;

    void removeStudentFromDanceGroup(Long studentId,
                                     Long danceGroupId,
                                     RemovedStudentDTO dto) throws ResponseStatusException;

    List<GetMessageTemplateDTO> getTeacherTemplates(Long id);

    UserDetailsService userDetailsService();

    TeacherEntity getByUsername(String username) throws UsernameNotFoundException;
}
