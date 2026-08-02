package dancing.school.dto;

import dancing.school.enums.StatusRequestEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequestDTO {

    @NotNull(message = "Не должно быть пустым")
    private StatusRequestEnum status;

    @NotNull(message = "Не должно быть пустым")
    Long groupId;

    @NotNull(message = "Не должно быть пустым")
    @Size(min = 1, max = 100, message = "Текст ответа не может быть пустым и превышать 100 символов")
    private String messageTeacher;
}
