package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.*;
import dancing.school.enums.RoleEnum;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.mappers.DanceGroupMapper;
import dancing.school.mappers.MessageTemplateMapper;
import dancing.school.mappers.TeacherMapper;
import dancing.school.repositories.DanceGroupRepository;
import dancing.school.repositories.RequestRepository;
import dancing.school.repositories.StudentDanceGroupRepository;
import dancing.school.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService implements ITeacherService {

    private TeacherRepository teacherRepository;

    private RequestRepository requestRepository;

    private TeacherMapper teacherMapper;

    private MessageTemplateMapper messageTemplateMapper;

    private DanceGroupRepository danceGroupRepository;

    private StudentDanceGroupRepository studentDanceGroupRepository;

    private DanceGroupMapper danceGroupMapper;

    private IStudentService studentService;

    private JwtService jwtService;

    public TeacherEntity getTeacher(Long id) throws ResponseStatusException {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Учитель не найден с айди " + id));
    }

    public DanceGroupEntity getDanceGroup(Long id) throws ResponseStatusException {
        return danceGroupRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Танцевальная группа не найдена с айди " + id));
    }

    @Override
    public GetJwtDTO createTeacher(CreateTeacherDTO dto) throws ResponseStatusException {
       TeacherEntity teacherEntity = teacherMapper.toEntity(dto);
       BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

       String hashPassword = bcrypt.encode(teacherEntity.getPassword());

       teacherEntity.setPassword(hashPassword);

       try {
           teacherRepository.save(teacherEntity);
       } catch(DataIntegrityViolationException ex) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                   "Такое имя пользователя уже существует");
       }

       String token = jwtService.generateToken(teacherEntity, RoleEnum.TEACHER);

       return new GetJwtDTO(token);
    }

    @Override
    public GetJwtDTO login(AuthUserDTO dto) throws ResponseStatusException {
        TeacherEntity teacherEntity = null;

        try {
            teacherEntity = getByUsername(dto.getUsername());
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        var bcrypt = new BCryptPasswordEncoder();

        if(bcrypt.matches(dto.getPassword(), teacherEntity.getPassword())) {
            var jwt = new GetJwtDTO();

           String token = jwtService.generateToken(teacherEntity, RoleEnum.TEACHER);

           jwt.setToken(token);

           return jwt;
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }


    @Override
    public List<GetShortTeacherDTO> getTeachers() {
        List<TeacherEntity> teachersEntities = teacherRepository.findAll();
        return teacherMapper.toGetShortTeacherDTOs(teachersEntities);
    }

    @Override
    public GetTeacherDTO getTeacherById(Long id) {
        TeacherEntity teacherEntity = getTeacher(id);
        return teacherMapper.toGetTeacherDTO(teacherEntity);
    }

    @Override
    public void deleteTeacherById(Long id) throws ResponseStatusException {
        TeacherEntity teacherEntity = getTeacher(id);
        teacherRepository.delete(teacherEntity);
    }

    @Override
    public GetTeacherDTO updateTeacher(Long id, UpdateTeacherDTO dto) throws ResponseStatusException {
        getTeacher(id);
        TeacherEntity changedEntity = this.teacherMapper.changeEntity(id, dto);
        try {
            this.teacherRepository.save(changedEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такое имя пользователя уже существует");
        }

        return this.teacherMapper.toGetTeacherDTO(changedEntity);
    }

    @Override
    public List<GetShortDanceGroupDTO> getDanceGroupsByTeacherId(Long id) {
        getTeacher(id);
        List<DanceGroupEntity> groups = danceGroupRepository.findAllByTeacherId(id);

        return danceGroupMapper.toDtos(groups);
    }

    @Override
    public GetDanceGroupDTO createDanceGroup(CreateDanceGroupDTO dto, Long idTeacher) throws ResponseStatusException {
        DanceGroupEntity danceGroupEntity = danceGroupMapper.toEntity(dto);
        TeacherEntity teacher = getTeacher(idTeacher);
        danceGroupEntity.setTeacher(teacher);
        DanceGroupEntity savedEntity = danceGroupRepository.save(danceGroupEntity);
        return danceGroupMapper.toDto(savedEntity);
    }

    @Override
    public GetDanceGroupDTO updateDanceGroup(Long id, UpdateDanceGroupDTO dto) throws ResponseStatusException {
        DanceGroupEntity existingGroup = getDanceGroup(id);
        danceGroupMapper.updateEntityFromDto(dto, existingGroup);
        DanceGroupEntity updatedEntity = danceGroupRepository.save(existingGroup);
        return danceGroupMapper.toDto(updatedEntity);
    }

    @Override
    public void deleteDanceGroupById(Long id) {
        DanceGroupEntity entity = getDanceGroup(id);
        danceGroupRepository.delete(entity);
    }

    @Override
    public GetDanceGroupDTO getDanceGroupById(Long id) throws ResponseStatusException {
        DanceGroupEntity entity = getDanceGroup(id);

       List<StudentDanceGroupEntity> studentDanceGroupEntities = studentDanceGroupRepository.findAllByDanceGroupEntity(entity);
       //studentDanceGroupEntities - список связей студентов и групп
       List<StudentEntity> studentEntities = studentDanceGroupEntities.stream().map(StudentDanceGroupEntity::getStudent)
               .toList();
        return danceGroupMapper.toDtoWithStudents(entity, studentEntities);
    }

    @Override
    @Transactional
    public void removeStudentFromDanceGroup(Long studentId, Long danceGroupId, RemovedStudentDTO dto) throws ResponseStatusException {
        StudentEntity studentEntity = studentService.getStudent(studentId);

        DanceGroupEntity danceGroupEntity = getDanceGroup(danceGroupId);

        StudentDanceGroupEntity studentDanceGroupEntity = studentDanceGroupRepository.findByDanceGroupEntityAndStudent(
                danceGroupEntity,
                studentEntity
        );

        if (studentDanceGroupEntity == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Студента нет в данной группе"
            );

        studentDanceGroupRepository.delete(studentDanceGroupEntity);

        RequestEntity requestEntity = requestRepository.findByTeacherIdAndStudentId(
                danceGroupEntity.getTeacher().getId(),
                studentId
        );

        if (requestEntity == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Такой заявки не существует"
            );

        requestEntity.setStatus(StatusRequestEnum.REMOVED);
        requestEntity.setMessageTeacher(dto.getMessageTeacher());
        requestRepository.save(requestEntity);
    }

    @Override
    public List<GetMessageTemplateDTO> getTeacherTemplates(Long id) throws ResponseStatusException {
        TeacherEntity teacher = getTeacher(id);
        List<MessageTemplateEntity> templates = teacher.getTemplates();
        return messageTemplateMapper.toDtos(templates);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public TeacherEntity getByUsername(String username) throws UsernameNotFoundException {
        TeacherEntity teacherEntity = teacherRepository.findTeacherEntityByUsername(username);

        if(teacherEntity == null)
            throw new UsernameNotFoundException("Преподаватель не найден");

        return teacherEntity;
    }


}
