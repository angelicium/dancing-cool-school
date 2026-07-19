package dancing.school.services;

import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.GetShortTeacherDTO;
import dancing.school.dto.GetTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.TeacherMapper;
import dancing.school.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherService implements ITeacherService {

    private TeacherRepository teacherRepository;

    private TeacherMapper teacherMapper;

    public TeacherEntity getTeacher(Long id) throws ResponseStatusException {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Учитель не найден с айди " + id));
    }

    @Override
    public GetTeacherDTO createTeacher(CreateTeacherDTO dto) throws ResponseStatusException {
       TeacherEntity teacherEntity = teacherMapper.toEntity(dto);
       try {
           teacherRepository.save(teacherEntity);
       } catch(DataIntegrityViolationException ex) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                   "Такое имя пользователя уже существует");
       }

       return teacherMapper.toGetTeacherDTO(teacherEntity);
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
}
