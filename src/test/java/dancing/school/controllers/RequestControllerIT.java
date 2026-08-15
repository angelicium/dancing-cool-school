package dancing.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dancing.school.dto.CreateRequestDTO;
import dancing.school.entities.RequestEntity;
import dancing.school.entities.StudentEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.repositories.MessageTemplateRepository;
import dancing.school.repositories.RequestRepository;
import dancing.school.repositories.StudentRepository;
import dancing.school.repositories.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MessageTemplateRepository messageTemplateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        requestRepository.deleteAll();
        messageTemplateRepository.deleteAll();
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
    }

    @Test
    void testSendRequestTeacher_success() throws Exception {
        StudentEntity student = new StudentEntity();
        student.setFirstName("Имя");
        student.setLastName("Фамилия");
        student.setPatronymic("Отчество");
        student.setUsername("user");
        student.setPassword("password");
        student.setAge(22);
        student.setTemplates(new ArrayList<>());
        student = studentRepository.save(student);

        TeacherEntity teacher = new TeacherEntity();
        teacher.setFirstName("Name");
        teacher.setLastName("LastName");
        teacher.setPatronymic("Patronymic");
        teacher.setUsername("teacher");
        teacher.setPassword("password");
        teacher.setAge(40);
        teacher.setAboutMe("лалаалал");
        teacher.setExperience(10);
        teacher.setGroups(null);
        teacher.setTemplates(new ArrayList<>());
        teacher = teacherRepository.save(teacher);

        CreateRequestDTO createRequestDTO = new CreateRequestDTO("Здравствуйте! Хочу записаться к вам.");

        mockMvc.perform(post("/api/v1/requests/student/{idStudent}/teacher/{idTeacher}",
                        student.getId(), teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequestDTO)))
                .andExpect(status().isOk());

        List<RequestEntity> savedRequests = requestRepository.findAll();

        assertEquals(1, savedRequests.size());

        RequestEntity actualRequest = savedRequests.get(0);

        assertEquals(student.getId(), actualRequest.getStudent().getId());
        assertEquals(teacher.getId(), actualRequest.getTeacher().getId());
        assertEquals("Здравствуйте! Хочу записаться к вам.", actualRequest.getMessageStudent());
        assertEquals(StatusRequestEnum.NOT_VIEWED, actualRequest.getStatus());
    }

    @Test
    void testSendRequestTeacher_badRequest() throws Exception {
        Long studentId = 9999L;

        Long teacherId = 8888L;

        mockMvc.perform(post("/api/v1/requests/student/{idStudent}/teacher/{idTeacher}",
                        studentId, teacherId))
                .andExpect(status().isBadRequest());

        List<RequestEntity> savedRequests = requestRepository.findAll();
        assertEquals(0, savedRequests.size());
    }
}
