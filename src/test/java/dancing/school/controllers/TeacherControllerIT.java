package dancing.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Long idCreatedTeacher = 702L;

    private UpdateTeacherDTO getTestUpdateTeacherDTO() {
        return new UpdateTeacherDTO(
                "Даниил",
                "Жадан",
                "Валерьевич",
                22,
                1.5F,
                "IT препод",
                "zhadina",
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
        ).andExpect(status().isCreated());
    }

    @Test
    void testGetTeachers_success() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers")
        ).andExpect(status().isOk());
    }

    @Test
    void testGetTeacher_success() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers/" + idCreatedTeacher)
        ).andExpect(status().isOk());
    }

    @Test
    void testGetTeacher_badRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers/1")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteTeacher_success() throws Exception {
        mockMvc.perform(
                delete("/api/v1/teachers/" +  idCreatedTeacher)
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
        var updateTeacherDTO = getTestUpdateTeacherDTO();
        mockMvc.perform(
                put("/api/v1/teachers/" + idCreatedTeacher)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(updateTeacherDTO)
                        )
        ).andExpect(status().isOk());
    }

    @Test
    void testUpdateTeacher_badRequest() throws Exception {
        var updateTeacherDTO = getTestUpdateTeacherDTO();
        mockMvc.perform(
                put("/api/v1/teachers/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(updateTeacherDTO)
                        )
        ).andExpect(status().isBadRequest());
    }
}
