package dancing.school.controllers;

import dancing.school.services.IRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/requests")
@AllArgsConstructor
public class RequestController {
    private IRequestService requestService;

    @PostMapping("/student/{idStudent}/teacher/{idTeacher}")
    public ResponseEntity<Void> sendRequestTeacher(@PathVariable("idStudent") Long idStudent,
                                                   @PathVariable ("idTeacher") Long idTeacher) {
        requestService.sendRequestTeacher(idStudent, idTeacher);
        return ResponseEntity.ok().build();
    }
}
