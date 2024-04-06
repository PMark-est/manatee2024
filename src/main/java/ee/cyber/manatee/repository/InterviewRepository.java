package ee.cyber.manatee.repository;

import ee.cyber.manatee.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {
}
