package dancing.school.services;

import dancing.school.dto.*;
import dancing.school.entities.MessageTemplateEntity;
import dancing.school.entities.RequestEntity;
import dancing.school.entities.StudentEntity;
import dancing.school.entities.TeacherEntity;
import dancing.school.enums.RoleEnum;
import dancing.school.enums.StatusRequestEnum;
import dancing.school.mappers.MessageTemplateMapper;
import dancing.school.mappers.StudentMapper;
import dancing.school.repositories.RequestRepository;
import dancing.school.repositories.StudentRepository;
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
public class StudentService implements IStudentService {

    private StudentRepository studentRepository;

    private StudentMapper studentMapper;

    private MessageTemplateMapper messageTemplateMapper;

    private RequestRepository requestRepository;

    private JwtService jwtService;

    public StudentEntity getStudent(Long id) throws ResponseStatusException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ученик не найден с айди " + id));
    }

    @Override
    public GetStudentDTO getStudentById(Long id) {
        StudentEntity studentEntity = getStudent(id);
        return this.studentMapper.toGetStudentDTO(studentEntity);
    }

    @Override
    public GetJwtDTO createStudent(CreateStudentDTO dto) throws  ResponseStatusException {
        StudentEntity studentEntity = studentMapper.toEntity(dto);
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hashPassword = bcrypt.encode(studentEntity.getPassword());
        studentEntity.setPassword(hashPassword);

        try{
            studentRepository.save(studentEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такое имя пользователя уже существует");
        }

        String token = jwtService.generateToken(studentEntity, RoleEnum.STUDENT);
        return new GetJwtDTO(token);
    }

    @Override
    public List<GetShortStudentDTO> getStudents() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        return this.studentMapper.toGetShortStudentDTOs(studentEntities);
    }

    @Override
    public GetStudentDTO updateStudent(Long id, UpdateStudentDTO dto) throws ResponseStatusException {
        getStudent(id);
        StudentEntity changedEntity = this.studentMapper.changeEntity(id, dto);
        try {
            this.studentRepository.save(changedEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такое имя пользователя уже существует");
        }
        return this.studentMapper.toGetStudentDTO(changedEntity);
    }

    @Override
    public void deleteStudent(Long id) {
        StudentEntity studentEntity = getStudent(id);
        studentRepository.delete(studentEntity);
    }

    @Override
    public List<GetShortRequestDTO> getRequests(Long studentId, StatusRequestEnum status) throws ResponseStatusException {
       StudentEntity studentEntity = getStudent(studentId);
       List<RequestEntity> requestEntities = requestRepository.findByStudentAndStatus(studentEntity, status);
       return studentMapper.toGetRequestDTOs(requestEntities);
    }

    @Override
    public List<GetMessageTemplateDTO> getMessageTemplates(Long studentId) throws ResponseStatusException {
        StudentEntity entity = getStudent(studentId);
        List<MessageTemplateEntity> templates = entity.getTemplates();
        return messageTemplateMapper.toDtos(templates);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    @Override
    public StudentEntity getByUsername(String username) throws UsernameNotFoundException {
        StudentEntity studentEntity = studentRepository.findStudentEntityByUsername(username);

        if(studentEntity == null)
            throw new UsernameNotFoundException("Студент не найден");

        return studentEntity;
    }
}

