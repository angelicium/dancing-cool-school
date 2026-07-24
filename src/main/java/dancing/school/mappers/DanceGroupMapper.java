package dancing.school.mappers;

import dancing.school.dto.GetDanceGroupDTO;
import dancing.school.entities.DanceGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DanceGroupMapper {
    @Mapping(target = "teacherFirstName", source = "teacher.firstName")
    @Mapping(target = "teacherLastName", source = "teacher.lastName")
    @Mapping(target = "teacherPatronymic", source = "teacher.patronymic")
    GetDanceGroupDTO toDto(DanceGroupEntity entity);

    List<GetDanceGroupDTO> toDtos(List<DanceGroupEntity> entities);
}
