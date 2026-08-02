package dancing.school.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemovedStudentDTO {

    @NotNull(message = "Не должно быть пустым")
    @Size(min = 1, max = 100, message = "Текст сообщения не может быть пустым и превышать 100 символов")
    private String messageTeacher;
}
