package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.dto.GetRequestDTO;
import dancing.school.dto.ReplyRequestDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IRequestService {
    void sendRequestTeacher(Long idStudent, Long idTeacher, CreateRequestDTO dto) throws ResponseStatusException;

    List<GetRequestDTO> getRequests(Long idTeacher);

    void replyRequest(Long idRequest, ReplyRequestDTO dto);
}
