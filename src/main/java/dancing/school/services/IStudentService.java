package dancing.school.services;

import dancing.school.dto.CreateStudentDTO;
import dancing.school.dto.GetShortStudentDTO;
import dancing.school.dto.GetStudentDTO;
import dancing.school.dto.UpdateStudentDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


public interface IStudentService {

    GetStudentDTO getStudentById(Long id);

    GetStudentDTO createStudent(CreateStudentDTO dto) throws ResponseStatusException;

    List<GetShortStudentDTO> getStudents();

    GetStudentDTO updateStudent(Long id, UpdateStudentDTO dto) throws  ResponseStatusException;

    void deleteStudent(Long id);
}
