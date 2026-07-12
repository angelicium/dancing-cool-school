package dancing.school.mappers;

import dancing.school.dto.CreateTeacherDTO;
import dancing.school.dto.GetShortTeacherDTO;
import dancing.school.dto.GetTeacherDTO;
import dancing.school.dto.UpdateTeacherDTO;
import dancing.school.entities.TeacherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    @Mapping(target = "id", ignore = true)
    TeacherEntity toEntity(CreateTeacherDTO dto);

    GetTeacherDTO toGetTeacherDTO(TeacherEntity teacherEntity);

    List<GetShortTeacherDTO> toGetShortTeacherDTOs(List<TeacherEntity> teacherEntities);

    @Mapping(target = "id", source = "id")
    TeacherEntity changeEntity(Long id, UpdateTeacherDTO dto);

    UpdateTeacherDTO updateDTO(TeacherEntity teacherEntity);
}
