package solbin.project.salary.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import solbin.project.salary.domain.worklog.Worklog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) // createAt 이 작동을 하게 된다
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Worklog> workLogs =  new ArrayList<>();

    @Builder
    public User(LocalDateTime createdAt, String email, Long id, String name, String password, UserEnum role, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
        this.updatedAt = updatedAt;
    }

    //== 연관관계 메서드 ==//
    public void addWorkLog(Worklog workLog) {
        workLogs.add(workLog);
        workLog.assignUser(this);
    }

}
