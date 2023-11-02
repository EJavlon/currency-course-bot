package uz.ejavlon.currencycourse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.ejavlon.currencycourse.repository.CourseRepository;
import uz.ejavlon.currencycourse.entity.Course;

@Service
@RequiredArgsConstructor
public class CurrencyCourseService {
    private final CourseRepository courseRepository;

    @Cacheable(value = "course",key = "#ccy")
    public Course getCourse(String ccy) {
        return courseRepository.findByCcyJpaQuery(ccy).orElse(null);
    }
}
