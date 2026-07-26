package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRequestDTO {

    private String firstName;

    private String lastName;

    private String patronymic;

    private int age;

    private String description;
}
