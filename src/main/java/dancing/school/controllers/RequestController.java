package dancing.school.controllers;

import dancing.school.dto.*;
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
    public ResponseEntity<ResponseDTO<List<GetShortRequestDTO>>> getRequests(@PathVariable ("idTeacher") Long idTeacher){
        List<GetShortRequestDTO> requestsDTO = requestService.getRequests(idTeacher);

        var response = new ResponseDTO<List<GetShortRequestDTO>>();
        response.setData(requestsDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ResponseDTO<GetRequestDTO>> getRequestById(@PathVariable("requestId") Long requestId) {
        GetRequestDTO requestDTO = requestService.getRequestById(requestId);
        var response = new ResponseDTO<GetRequestDTO>();
        response.setData(requestDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{requestId}/reply")
    public ResponseEntity<Void> replyRequest(@PathVariable ("requestId") Long idRequest,
                                             @RequestBody ReplyRequestDTO dto) {
        requestService.replyRequest(idRequest, dto);
        return ResponseEntity.ok().build();
    }
}
