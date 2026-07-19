package dancing.school.services;

import dancing.school.dto.CreateStudentDTO;
import dancing.school.dto.GetShortStudentDTO;
import dancing.school.dto.GetStudentDTO;
import dancing.school.dto.UpdateStudentDTO;
import dancing.school.entities.StudentEntity;
import dancing.school.mappers.StudentMapper;
import dancing.school.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {

    private StudentRepository studentRepository;

    private StudentMapper studentMapper;

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
    public GetStudentDTO createStudent(CreateStudentDTO dto) throws  ResponseStatusException {
        StudentEntity studentEntity = studentMapper.toEntity(dto);
        try{
            studentRepository.save(studentEntity);
        } catch(DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такое имя пользователя уже существует");
        }
        return studentMapper.toGetStudentDTO(studentEntity);
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
}

