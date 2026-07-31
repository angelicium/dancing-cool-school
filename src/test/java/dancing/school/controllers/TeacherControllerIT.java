package dancing.school.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dancing.school.dto.CreateDanceGroupDTO;
import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.UpdateDanceGroupDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.DanceGroupEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.TeacherMapper;
import dancing.school.repositories.DanceGroupRepository;
import dancing.school.repositories.RequestRepository;
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

    @Autowired
    private DanceGroupRepository danceGroupRepository;

    @Autowired
    private RequestRepository requestRepository;

    @AfterEach
    void tearDown() {
        this.requestRepository.deleteAll();
        this.danceGroupRepository.deleteAll();
        this.teacherRepository.deleteAll();
    }

    private DanceGroupEntity getTestDanceGroupEntity(String name, TeacherEntity teacher) {
        return new DanceGroupEntity(
                null,
                name,
                teacher
        );
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
                "12345",
                null
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

    @Test
    void testGetTeacherGroups_success() throws Exception {
        TeacherEntity teacher = teacherRepository.save(getTestTeacherEntity("first"));

        DanceGroupEntity group1 = getTestDanceGroupEntity("Хип-хоп", teacher);
        DanceGroupEntity group2 = getTestDanceGroupEntity("Сальса", teacher);
        danceGroupRepository.saveAll(List.of(group1, group2));

        mockMvc.perform(
                        get("/api/v1/teachers/" + teacher.getId() + "/groups")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].danceGroupName").value("Хип-хоп"))
                .andExpect(jsonPath("$.data[1].danceGroupName").value("Сальса"));
    }

    @Test
    void testGetTeacherGroups_teacherNotFound_badRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers/999/groups")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testGetDanceGroupById_success() throws Exception {
        TeacherEntity teacher = teacherRepository.save(getTestTeacherEntity("second"));
        DanceGroupEntity group = danceGroupRepository.save(getTestDanceGroupEntity("Вог", teacher));

        mockMvc.perform(
                        get("/api/v1/teachers/groups/" + group.getId())
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.danceGroupName").value("Вог"));
    }

    @Test
    void testGetDanceGroupById_groupNotFound_badRequest() throws Exception {
        mockMvc.perform(
                get("/api/v1/teachers/groups/999")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateDanceGroup_success() throws Exception {
        TeacherEntity teacher = teacherRepository.save(getTestTeacherEntity("first"));

        var createDanceGroupDTO = new CreateDanceGroupDTO("Джаз-Фанк");

        mockMvc.perform(
                        post("/api/v1/teachers/" + teacher.getId() + "/groups")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createDanceGroupDTO))
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.danceGroupName").value("Джаз-Фанк"));
    }

    @Test
    void testCreateDanceGroup_teacherNotFound_badRequest() throws Exception {
        var createDanceGroupDTO = new CreateDanceGroupDTO("Джаз-Фанк");

        mockMvc.perform(
                post("/api/v1/teachers/999/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDanceGroupDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateDanceGroup_success() throws Exception {
        TeacherEntity teacher = teacherRepository.save(getTestTeacherEntity("first"));
        DanceGroupEntity group = danceGroupRepository.save(getTestDanceGroupEntity("Старое название", teacher));

        var updateDanceGroupDTO = new UpdateDanceGroupDTO("Новое название");

        mockMvc.perform(
                        put("/api/v1/teachers/groups/" + group.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDanceGroupDTO))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.danceGroupName").value("Новое название"));
    }

    @Test
    void testUpdateDanceGroup_groupNotFound_badRequest() throws Exception {
        var updateDanceGroupDTO = new UpdateDanceGroupDTO("Новое название");

        mockMvc.perform(
                put("/api/v1/teachers/groups/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDanceGroupDTO))
        ).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteDanceGroup_success() throws Exception {
        TeacherEntity teacher = teacherRepository.save(getTestTeacherEntity("first"));
        DanceGroupEntity group = danceGroupRepository.save(getTestDanceGroupEntity("Балет", teacher));

        mockMvc.perform(
                delete("/api/v1/teachers/groups/" + group.getId())
        ).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteDanceGroup_groupNotFound_badRequest() throws Exception {
        mockMvc.perform(
                delete("/api/v1/teachers/groups/999")
        ).andExpect(status().isBadRequest());
    }
}
