package ee.cyber.manatee.repository;

import ee.cyber.manatee.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Integer> {

    List<Interview> findAllByApplicationId(Integer applicationId);

}
