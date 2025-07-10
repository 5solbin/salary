package solbin.project.salary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import solbin.project.salary.domain.worklog.Worklog;

public interface WorklogRepository extends JpaRepository<Worklog, Long> {
}
