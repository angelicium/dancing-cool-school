package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.entities.RequestEntity;
import dancing.school.entities.StudentEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.repositories.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class RequestService implements IRequestService {
    private RequestRepository requestRepository;

    private IStudentService studentService;

    private ITeacherService teacherService;

    @Override
    public void sendRequestTeacher(Long idStudent,
                                   Long idTeacher,
                                   CreateRequestDTO dto) throws ResponseStatusException {
        StudentEntity studentEntity = studentService.getStudent(idStudent);

        TeacherEntity teacherEntity = teacherService.getTeacher(idTeacher);

        var requestEntity = new RequestEntity(
                null,
                studentEntity,
                teacherEntity,
                dto.getDescription(),
                StatusRequestEnum.NOT_VIEWED
        );

        requestRepository.save(requestEntity);
    }
}
