package dancing.school.controllers;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.services.IRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/requests")
@AllArgsConstructor
public class RequestController {
    private IRequestService requestService;

    @PostMapping("/student/{idStudent}/teacher/{idTeacher}")
    public ResponseEntity<Void> sendRequestTeacher(@PathVariable("idStudent") Long idStudent,
                                                   @PathVariable ("idTeacher") Long idTeacher,
                                                   @RequestBody CreateRequestDTO dto) {
        requestService.sendRequestTeacher(idStudent, idTeacher, dto);
        return ResponseEntity.ok().build();
    }


}
