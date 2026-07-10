package dancing.school.services;

import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.GetShortTeacherDTO;
import dancing.school.dto.GetTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.TeacherEntity;
import dancing.school.mappers.TeacherMapper;
import dancing.school.repositories.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TeacherService implements ITeacherService {

    private TeacherRepository teacherRepository;

    private TeacherMapper teacherMapper;

    @Override
    public GetTeacherDTO createTeacher(CreateTeacherDTO dto) {
       TeacherEntity teacherEntity = teacherMapper.toEntity(dto);
       teacherRepository.save(teacherEntity);

       return teacherMapper.toGetTeacherDTO(teacherEntity);
    }

    @Override
    public List<GetShortTeacherDTO> getTeachers() {
        List<TeacherEntity> teachersEntities = teacherRepository.findAll();
        return teacherMapper.toGetShortTeacherDTOs(teachersEntities);
    }

    @Override
    public GetTeacherDTO getTeacherById(Long id) {
        Optional<TeacherEntity> optTeacher = teacherRepository.findById(id);
        TeacherEntity teacherEntity = optTeacher.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Такой учитель не найден"));
        return teacherMapper.toGetTeacherDTO(teacherEntity);
    }

    @Override
    public void deleteTeacherById(Long id) throws ResponseStatusException {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Учитель не найден с айди" + id));
        teacherRepository.delete(teacherEntity);
    }

    @Override
    public GetTeacherDTO updateTeacher(Long id, UpdateTeacherDTO dto) throws ResponseStatusException {
        if (!teacherRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Учитель не найден с айди " + id);
        }
        TeacherEntity changedEntity = this.teacherMapper.changeEntity(id, dto);

        TeacherEntity updatedEntity = this.teacherRepository.save(changedEntity);

        return this.teacherMapper.toGetTeacherDTO(updatedEntity);
    }
}
