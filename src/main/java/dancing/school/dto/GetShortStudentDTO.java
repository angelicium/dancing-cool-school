package dancing.school.dto;

import dancing.school.entities.TeacherEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShortStudentDTO {
    private String firstName;

    private String lastName;

    private String patronymic;
}
