package ait.cohort34_1.student.dao;

import ait.cohort34_1.student.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;
import java.util.stream.Stream;

public interface StudentRepository extends MongoRepository<Student, Integer> {
    Stream<Student> findByNameIgnoreCase(String name);

    long countByNameInIgnoreCase(Set<String> names);

    @Query("{'scores.?0': {'$gt': ?1}}")
    Stream<Student> findByExamAndScoreGreaterThan(String exam, int score);
}
