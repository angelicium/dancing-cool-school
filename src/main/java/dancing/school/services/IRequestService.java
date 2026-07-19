package dancing.school.services;

import org.springframework.web.server.ResponseStatusException;

public interface IRequestService {
    void sendRequestTeacher(Long idStudent, Long idTeacher) throws ResponseStatusException;
}
