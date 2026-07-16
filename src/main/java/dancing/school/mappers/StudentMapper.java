package dancing.school.mappers;

import dancing.school.dto.CreateStudentDTO;
import dancing.school.dto.GetShortStudentDTO;
import dancing.school.dto.GetStudentDTO;
import dancing.school.dto.UpdateStudentDTO;
import dancing.school.entities.StudentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    StudentEntity toEntity(CreateStudentDTO dto);

    GetStudentDTO toGetStudentDTO(StudentEntity studentEntity);

    List<GetShortStudentDTO> toGetShortStudentDTOs(List<StudentEntity> studentEntities);

    @Mapping(target = "id", source = "id")
    StudentEntity changeEntity (Long id, UpdateStudentDTO dto);
}
