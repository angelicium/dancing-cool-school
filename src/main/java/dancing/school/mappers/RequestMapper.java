package dancing.school.mappers;

import dancing.school.dto.GetRequestDTO;
import dancing.school.entities.RequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(target = "lastName", source = "student.lastName")
    @Mapping(target = "firstName", source = "student.firstName")
    @Mapping(target = "patronymic", source = "student.patronymic")
    @Mapping(target = "age", source = "student.age")
    GetRequestDTO toGetRequestDTO(RequestEntity requestEntity);

    List<GetRequestDTO> toGetRequestDTOs(List<RequestEntity> requests);
}
