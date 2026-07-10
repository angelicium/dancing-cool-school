package dancing.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherDTO {

    private String firstName;

    private String lastName;

    private String patronymic;

    private int age;

    private float experience;

    private String aboutMe;

    private String username;

    private String password;
}
