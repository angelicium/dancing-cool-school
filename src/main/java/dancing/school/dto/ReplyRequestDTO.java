package dancing.school.dto;

import dancing.school.enums.StatusRequestEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDTO {
    private StatusRequestEnum status;

    Long groupId;

    private String messageTeacher;
}
