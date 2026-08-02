package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.dto.GetRequestDTO;
import dancing.school.dto.GetShortRequestDTO;
import dancing.school.dto.ReplyRequestDTO;
import dancing.school.entities.*;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.mappers.RequestMapper;
import dancing.school.repositories.RequestRepository;
import dancing.school.repositories.StudentDanceGroupRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestService implements IRequestService {
    private RequestRepository requestRepository;

    private StudentDanceGroupRepository studentDanceGroupRepository;

    private IStudentService studentService;

    private ITeacherService teacherService;

    private RequestMapper requestMapper;

    private RequestEntity findRequestById(Long id){
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
    public List<GetShortRequestDTO> getRequests(Long idTeacher) {
       List<RequestEntity> requests = requestRepository.findAllByTeacherId(idTeacher);
       return requestMapper.toGetRequestDTOs(requests);
    }

    @Override
    public GetRequestDTO getRequestById(Long idRequest) throws ResponseStatusException {
        RequestEntity requestEntity = findRequestById(idRequest);
        return requestMapper.toGetRequestDTO(requestEntity);
    }

    private void checkStatus(StatusRequestEnum status) throws ResponseStatusException {
        switch(status) {
            case NOT_VIEWED:
            case REMOVED:
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Вы не можете ставить такой статус"
                );
        }
    }

    @Override
    @Transactional
    public void replyRequest(Long idRequest, ReplyRequestDTO dto) throws ResponseStatusException {
        checkStatus(dto.getStatus());
        RequestEntity request = findRequestById(idRequest);

        if(request.getStatus() != StatusRequestEnum.NOT_VIEWED)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Вы уже ответили на заявку!"
            );

        request.setStatus(dto.getStatus());
        request.setMessageTeacher(dto.getMessageTeacher());
        requestRepository.save(request);

        DanceGroupEntity danceGroupEntity = teacherService.getDanceGroup(dto.getGroupId());

        StudentDanceGroupEntity studentDanceGroupEntity = new StudentDanceGroupEntity(
                null,
                request.getStudent(),
                danceGroupEntity
        );

        studentDanceGroupRepository.save(studentDanceGroupEntity);
    }
}
