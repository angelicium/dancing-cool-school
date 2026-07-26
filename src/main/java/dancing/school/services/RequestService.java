package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.dto.GetRequestDTO;
import dancing.school.dto.ReplyRequestDTO;
import dancing.school.entities.DanceGroupEntity;
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

    private RequestEntity getRequestById(Long id){
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такой заявки не существует"));
    }

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
                dto.getMessageStudent(),
                null,
                StatusRequestEnum.NOT_VIEWED
        );

        requestRepository.save(newRequestEntity);
    }

    @Override
    public List<GetRequestDTO> getRequests(Long idTeacher) {
       List<RequestEntity> requests = requestRepository.findAllByTeacherId(idTeacher);
       return requestMapper.toGetRequestDTOs(requests);
    }

    @Override
    public void replyRequest(Long idRequest, ReplyRequestDTO dto) throws ResponseStatusException {
        RequestEntity request = getRequestById(idRequest);
        request.setStatus(dto.getStatus());
        request.setMessageTeacher(dto.getMessageTeacher());
        requestRepository.save(request);

        DanceGroupEntity danceGroupEntity = teacherService.getDanceGroup(dto.getGroupId());
        //to do: добавление студентов в группу
    }
}
