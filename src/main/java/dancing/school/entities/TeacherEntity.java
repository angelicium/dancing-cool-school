package dancing.school.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "first_name",  nullable = false, length = 50)
    private String firstName;

    @Column(name =  "last_name", nullable = false, length = 50)
    private String lastName;

    @Column (length = 50)
    private String patronymic;

    @Column(nullable = false)
    private float experience;

    @Column(name = "about_me", nullable = false, length = 150)
    private String aboutMe;

    @Column(nullable = false)
    private int age;

    @Column(unique = true, length = 50, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<DanceGroupEntity> groups = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<MessageTemplateEntity> templates = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
