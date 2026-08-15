package dancing.school.dto;

import dancing.school.enums.StatusRequestEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetShortRequestDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String patronymic;

    private int age;

    private String status;
}
