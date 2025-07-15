package solbin.project.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solbin.project.salary.domain.worklog.Worklog;

import java.util.List;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {

    List<Worklog> findByUserId(Long userId);

}
