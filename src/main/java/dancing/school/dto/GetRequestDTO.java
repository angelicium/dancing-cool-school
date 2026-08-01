package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestDTO {

    private Long id;

    private Enum status;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String messageStudent;

    private String messageTeacher;

    private int age;
}
