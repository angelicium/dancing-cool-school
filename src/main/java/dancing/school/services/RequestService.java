package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.dto.GetRequestDTO;
import dancing.school.entities.RequestEntity;
import dancing.school.entities.StudentEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.mappers.RequestMapper;
import dancing.school.repositories.RequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestService implements IRequestService {
    private RequestRepository requestRepository;

    private IStudentService studentService;

    private ITeacherService teacherService;

    private RequestMapper requestMapper;

    @Override
    public void sendRequestTeacher(Long idStudent,
                                   Long idTeacher,
                                   CreateRequestDTO dto) throws ResponseStatusException {
        StudentEntity studentEntity = studentService.getStudent(idStudent);

        TeacherEntity teacherEntity = teacherService.getTeacher(idTeacher);

        RequestEntity requestEntity = requestRepository.findByTeacherIdAndStudentId(
                idTeacher,
                idStudent
        );

        if(requestEntity != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Вы уже отправили заявку!");


        var newRequestEntity = new RequestEntity(
                null,
                studentEntity,
                teacherEntity,
                dto.getDescription(),
                StatusRequestEnum.NOT_VIEWED
        );

        requestRepository.save(newRequestEntity);
    }

    @Override
    public List<GetRequestDTO> getRequests(Long idTeacher) {
       List<RequestEntity> requests = requestRepository.findAllByTeacherId(idTeacher);
       return requestMapper.toGetRequestDTOs(requests);
    }
}
