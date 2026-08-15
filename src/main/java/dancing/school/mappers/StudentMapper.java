package dancing.school.mappers;

import dancing.school.dto.*;
import dancing.school.entities.RequestEntity;
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

    UpdateStudentDTO updateDTO(StudentEntity studentEntity);

    CreateStudentDTO createDTO(StudentEntity entity);

    @Mapping(target = "firstName", source = "teacher.firstName")
    @Mapping(target = "lastName", source = "teacher.lastName")
    @Mapping(target = "patronymic", source = "teacher.patronymic")
    @Mapping(target = "age", source = "teacher.age")
    @Mapping(target = "status", expression = "java(requestEntity.getStatus().getValue())")
    GetShortRequestDTO toGetShortRequestDTO(RequestEntity requestEntity);

    List<GetShortRequestDTO> toGetRequestDTOs(List<RequestEntity> requests);
}
