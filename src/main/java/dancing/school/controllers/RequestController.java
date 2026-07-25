package dancing.school.controllers;

import dancing.school.dto.CreateRequestDTO;
import dancing.school.dto.GetRequestDTO;
import dancing.school.dto.ResponseDTO;
import dancing.school.services.IRequestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/teacher/{idTeacher}")
    public ResponseEntity<ResponseDTO<List<GetRequestDTO>>> getRequests(@PathVariable ("idTeacher") Long idTeacher){
        List<GetRequestDTO> requestsDTO = requestService.getRequests(idTeacher);

        var response = new ResponseDTO<List<GetRequestDTO>>();
        response.setData(requestsDTO);
        return ResponseEntity.ok(response);
    }
}
