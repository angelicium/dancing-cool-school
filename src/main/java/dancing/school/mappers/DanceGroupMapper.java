package dancing.school.mappers;

import dancing.school.dto.CreateDanceGroupDTO;
import dancing.school.dto.GetDanceGroupDTO;
import dancing.school.dto.GetShortDanceGroupDTO;
import dancing.school.dto.UpdateDanceGroupDTO;
import dancing.school.entities.DanceGroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DanceGroupMapper {
    @Mapping(target = "students", ignore = true)
    // после установки связи в базе игнор будет убран
    GetDanceGroupDTO toDto(DanceGroupEntity entity);

    List<GetShortDanceGroupDTO> toDtos(List<DanceGroupEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher",  ignore = true)
    DanceGroupEntity toEntity(CreateDanceGroupDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateEntityFromDto(UpdateDanceGroupDTO dto, @MappingTarget DanceGroupEntity entity);
}
