package dancing.school.services;

import dancing.school.dto.CreateRequestDTO;
import org.springframework.web.server.ResponseStatusException;

public interface IRequestService {
    void sendRequestTeacher(Long idStudent, Long idTeacher, CreateRequestDTO dto) throws ResponseStatusException;


}
