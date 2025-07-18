package solbin.project.salary.domain.worklog;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import solbin.project.salary.domain.jobtype.JobType;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.dto.worklog.update.UpdateReqDto;
import solbin.project.salary.handler.ex.CustomApiException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Worklog {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_type_id")
    private JobType jobType;

    private int payRate;
    private int totalWage;

    @Builder
    public Worklog(LocalDateTime endTime, Long id, LocalDateTime startTime, User user, JobType jobType, int payRate ,int totalWage) {
        this.endTime = endTime;
        this.id = id;
        this.startTime = startTime;
        this.user = user;
        this.jobType = jobType;
        this.payRate = payRate;
        this.totalWage = totalWage;
    }


    //==연관관계 메서드==//
    public void assignUser(User user) {
        this.user = user;
    }

    public void assignJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public void checkOwner(Long userId) {
        if(!Objects.equals(userId, user.getId())) {
            throw new CustomApiException("사용자 정보가 일치하지 않습니다.");
        }
    }


    //== 서비스 메서드 ==//
    public void update(UpdateReqDto dto) {
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
    }

    public Long getWorktime() {
        return Duration.between(this.startTime, this.endTime).toHours();
    }

}
