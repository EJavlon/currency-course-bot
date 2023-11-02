package uz.ejavlon.currencycourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ejavlon.currencycourse.entity.Course;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "SELECT c FROM Course AS c ORDER BY c.uniqueId DESC LIMIT 75")
    List<Course> findAllByFirst75();

    @Query(value = "SELECT c FROM Course AS c WHERE c.ccy=?1 ORDER BY c.uniqueId DESC LIMIT 1")
    Optional<Course> findByCcyJpaQuery(String ccy);

    @Query(value = "DELETE FROM Course AS c WHERE c.uniqueId<=(SELECT MAX(c.uniqueId)-75 FROM Course AS c)")
    void deleteJpaQuery();
}
