package ait.cohort34_1.student.service;

import ait.cohort34_1.student.dao.StudentRepository;
import ait.cohort34_1.student.dto.ScoreDto;
import ait.cohort34_1.student.dto.StudentAddDto;
import ait.cohort34_1.student.dto.StudentDto;
import ait.cohort34_1.student.dto.StudentUpdateDto;
import ait.cohort34_1.student.dto.exceptions.StudentNotFoundException;
import ait.cohort34_1.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service // instead of @Component
@RequiredArgsConstructor  // instead of @Component
public class StudentServiceImpl implements StudentService {

//    @Autowired
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    public Boolean addStudent(StudentAddDto studentAddDto) {
        if (studentRepository.existsById(studentAddDto.getId())) {
            return false;
        }
//        Student student = new Student(studentAddDto.getId(), studentAddDto.getName(), studentAddDto.getPassword());
        Student student = modelMapper.map(studentAddDto, Student.class);
        studentRepository.save(student);
        return true;
    }

    @Override
    public StudentDto findStudent(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto removeStudent(Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.deleteById(id);
        return modelMapper.map(student, StudentDto.class);

    }

    @Override
    public StudentAddDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        if (studentUpdateDto.getName() != null) {
            student.setName(studentUpdateDto.getName());
        }
        if (studentUpdateDto.getName() != null) {
            student.setPassword(student.getPassword());
        }
        studentRepository.save(student);
        return modelMapper.map(student, StudentAddDto.class);
    }

    @Override
    public Boolean addScore(Integer id, ScoreDto scoreDto) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        boolean res = student.addScore(scoreDto.getExamName(), scoreDto.getScore());
        studentRepository.save(student);
        return res;
    }

    @Override
    public Iterable<StudentDto> findStudentsByName(String name) {
        return studentRepository
                .findByNameIgnoreCase(name)
                .map(s -> modelMapper.map(s, StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Long getStudentsNamesQuantity(Set<String> names) {
        return studentRepository.countByNameInIgnoreCase(names);
    }

    @Override
    public Iterable<StudentDto> findStudentsByExamMinScore(String exam, Integer minScore) {
        return studentRepository
                .findByExamAndScoreGreaterThan(exam, minScore)
                .map(s -> modelMapper.map(s, StudentDto.class))
                .collect(Collectors.toList());
    }
}