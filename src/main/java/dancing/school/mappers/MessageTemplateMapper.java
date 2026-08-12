package dancing.school.mappers;

import dancing.school.dto.CreateMessageTemplateDTO;
import dancing.school.dto.GetMessageTemplateDTO;
import dancing.school.dto.UpdateMessageTemplateDTO;
import dancing.school.entities.MessageTemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageTemplateMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    MessageTemplateEntity toEntity(CreateMessageTemplateDTO dto);

    GetMessageTemplateDTO toDto(MessageTemplateEntity entity);

    List<GetMessageTemplateDTO> toDtos(List<MessageTemplateEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateEntityFromDto(UpdateMessageTemplateDTO dto, @MappingTarget MessageTemplateEntity entity);
}
