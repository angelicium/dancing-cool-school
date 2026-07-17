package dancing.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dancing.school.dto.CreateStudentDTO;
import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.UpdateStudentDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.StudentEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.StudentMapper;
import dancing.school.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    private UpdateStudentDTO getTestUpdateStudentDTO(String prefix) {
        return new UpdateStudentDTO(
                "Имя",
                "Фамилия",
                "Отчество",
                22,
                "юзернейм",
                "12345"
        );
    }

    private StudentEntity getTestStudentEntity(String prefix) {
        return new StudentEntity(
                null,
                prefix + " Имя",
                prefix + " Фамилия",
                "Отчество",
                22,
                prefix + " _username",
                "12345"
        );
    }

    @Test
    void testGetStudent_success() throws Exception {
        StudentEntity studentEntity = getTestStudentEntity("ученик");
        studentRepository.save(studentEntity);
        mockMvc.perform(
                get("/api/v1/students/" + studentEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data.firstName")
                                .value("ученик Имя")
                );
    }

    @Test
    void testGetStudent_badRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/students/999")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllStudents_success() throws Exception {
        List<StudentEntity> studentsEntities = List.of(
                getTestStudentEntity("first"),
                getTestStudentEntity("second")
        );
        this.studentRepository.saveAll(studentsEntities);

        mockMvc.perform(
                        get("/api/v1/students")
                ).andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data[0].lastName")
                                .value("first Фамилия")
                )
                .andExpect(
                        jsonPath("$.data[1].lastName")
                                .value("second Фамилия")
                );
    }

    @Test
    void testCreateStudent_success() throws Exception {
        var createStudentDTO = new CreateStudentDTO(
                "Олег",
                "Олегов",
                "Олегович",
                15,
                "oleg",
                "1234"
        );

        mockMvc.perform(
                        post("/api/v1/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(createStudentDTO)
                                )
                ).andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.data.firstName")
                                .value("Олег")
                )
                .andExpect(
                        jsonPath("$.data.lastName")
                                .value("Олегов")
                );
    }

    @Test
    void updateStudent_success() throws Exception {
        StudentEntity studentEntity = getTestStudentEntity("first");
        studentRepository.save(studentEntity);
        studentEntity.setAge(18);
        UpdateStudentDTO updateStudentDTO = studentMapper.updateDTO(studentEntity);
        mockMvc.perform(
                        put("/api/v1/students/" +  studentEntity.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(updateStudentDTO)
                                )
                ).andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data.age")
                                .value(18)
                );
    }

    @Test
    void updateStudent_badRequest() throws Exception {
        var updateStudentDTO = getTestUpdateStudentDTO("first");
        mockMvc.perform(
                put("/api/v1/students/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(updateStudentDTO)
                        )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteStudent_success() throws Exception {
        StudentEntity studentEntity = getTestStudentEntity("first");
        studentRepository.save(studentEntity);
        mockMvc.perform(
                delete("/api/v1/students/" + studentEntity.getId())
        ).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteStudent_badRequest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/students/999")
        ).andExpect(status().isBadRequest());
    }
}
