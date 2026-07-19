package dancing.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.TeacherMapper;
import dancing.school.repositories.TeacherRepository;
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
public class TeacherControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    @AfterEach
    void tearDown() {
        this.teacherRepository.deleteAll();
    }

    private UpdateTeacherDTO getTestUpdateTeacherDTO(String prefix) {
        return new UpdateTeacherDTO(
                "Имя",
                "Фамилия",
                "Отчество",
                22,
                1.5F,
                "обо мне",
                prefix + "_username",
                "12345"
        );
    }

    private TeacherEntity getTestTeacherEntity(String prefix) {
        return new TeacherEntity(
                null,
                prefix + " Имя",
                prefix + " Фамилия",
                "Отчество",
                1.5F,
                "обо мне",
                22,
                prefix + " _username",
                "12345"
        );
    }

    @Test
    void testCreateTeacher_success() throws Exception {
        var createTeacherDTO = new CreateTeacherDTO(
                "Даниил",
                "Жадан",
                "Валерьевич",
                22,
                1.5F,
                "IT препод",
                "zhadina",
                "12345"
        );

        mockMvc.perform(
                post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(createTeacherDTO)
                        )
        ).andExpect(status().isCreated())
                .andExpect(
                        jsonPath("$.data.firstName")
                                .value("Даниил")
                )
                .andExpect(
                        jsonPath("$.data.lastName")
                                .value("Жадан")
                );
    }

    @Test
    void testCreateTeacher_usernameExists_badRequest() throws Exception {
        TeacherEntity entity = getTestTeacherEntity("first");
        teacherRepository.save(entity);
        CreateTeacherDTO dto = teacherMapper.createDTO(getTestTeacherEntity("first"));
        mockMvc.perform(
                post("/api/v1/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(dto)
                        )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetTeachers_success() throws Exception {
        List<TeacherEntity> teachersEntities = List.of(
                getTestTeacherEntity("first"),
                getTestTeacherEntity("second")
                );
        this.teacherRepository.saveAll(teachersEntities);

        mockMvc.perform(
                get("/api/v1/teachers")
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
    void testGetTeacher_success() throws Exception {
        TeacherEntity teacherEntity = getTestTeacherEntity("first");
        teacherRepository.save(teacherEntity);
        mockMvc.perform(
                get("/api/v1/teachers/" + teacherEntity.getId())
        ).andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data.firstName")
                                .value("first Имя")
                );
    }

    @Test
    void testGetTeacher_badRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers/1")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTeacher_success() throws Exception {
        TeacherEntity teacherEntity = getTestTeacherEntity("first");
        teacherRepository.save(teacherEntity);
        mockMvc.perform(
                delete("/api/v1/teachers/" + teacherEntity.getId())
        ).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTeacher_badRequest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/teachers/1")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTeacher_success() throws Exception {
        TeacherEntity teacherEntity = getTestTeacherEntity("first");
        teacherRepository.save(teacherEntity);
        teacherEntity.setAge(25);
        UpdateTeacherDTO updateTeacherDTO = teacherMapper.updateDTO(teacherEntity);
        mockMvc.perform(
                put("/api/v1/teachers/" +  teacherEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(updateTeacherDTO)
                        )
        ).andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.data.age")
                                .value(25)
                );
    }

    @Test
    void testUpdateTeacher_notExistTeacher_badRequest() throws Exception {
        var updateTeacherDTO = getTestUpdateTeacherDTO("first");
        mockMvc.perform(
                put("/api/v1/teachers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(updateTeacherDTO)
                        )
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateTeacher_usernameExists_badRequest() throws Exception {
        List<TeacherEntity> teachersEntities = List.of(
                getTestTeacherEntity("first"),
                getTestTeacherEntity("second")
        );
        teacherRepository.saveAll(teachersEntities);
        UpdateTeacherDTO dto = teacherMapper.updateDTO(teachersEntities.get(0));
        dto.setUsername("second _username");
        mockMvc.perform(
                put("/api/v1/teachers/" + teachersEntities.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(dto)
                        )
        ).andExpect(status().isBadRequest());

    }
}
