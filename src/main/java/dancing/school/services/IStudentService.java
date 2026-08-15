package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.StudentEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public interface IStudentService {

    GetStudentDTO getStudentById(Long id);

    StudentEntity getStudent(Long id) throws ResponseStatusException;

    GetStudentDTO createStudent(CreateStudentDTO dto) throws ResponseStatusException;

    List<GetShortStudentDTO> getStudents();

    GetStudentDTO updateStudent(Long id, UpdateStudentDTO dto) throws  ResponseStatusException;

    void deleteStudent(Long id);

    List<GetShortRequestDTO> getRequests(Long studentId) throws ResponseStatusException;
}
