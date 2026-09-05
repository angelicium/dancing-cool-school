package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.StudentEntity;
import dancing.school.enums.StatusRequestEnum;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public interface IStudentService {

    GetStudentDTO getStudentById(Long id);

    StudentEntity getStudent(Long id) throws ResponseStatusException;

    GetJwtDTO createStudent(CreateStudentDTO dto) throws ResponseStatusException;

    List<GetShortStudentDTO> getStudents();

    GetStudentDTO updateStudent(Long id, UpdateStudentDTO dto) throws  ResponseStatusException;

    void deleteStudent(Long id);

    List<GetShortRequestDTO> getRequests(Long studentId, StatusRequestEnum status) throws ResponseStatusException;

    List<GetMessageTemplateDTO> getMessageTemplates(Long studentId) throws ResponseStatusException;

    UserDetailsService userDetailsService();

    StudentEntity getByUsername(String username) throws UsernameNotFoundException;
}
