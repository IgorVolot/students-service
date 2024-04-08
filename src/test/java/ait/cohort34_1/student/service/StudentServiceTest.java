package ait.cohort34_1.student.service;

import ait.cohort34_1.student.dao.StudentRepository;
import ait.cohort34_1.student.dto.StudentAddDto;
import ait.cohort34_1.student.dto.StudentDto;
import ait.cohort34_1.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @Mock
    StudentRepository studentRepository;
    @InjectMocks
    StudentServiceImpl studentService;

    final int studentId = 1000;
    Student student;
    @BeforeEach
    public void setUp (){
        student = new Student(studentId, "John", "1234");
    }

    @Test
    void testAddStudent (){
        StudentAddDto studentAddDto = new StudentAddDto(studentId, "John", "1234");
        when(studentRepository.save(Mockito.any(Student.class))).thenReturn(student);
        assertThat(studentService.addStudent(studentAddDto)).isTrue();
    }

    @Test
    void testFindStudent () {
        when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));
        StudentDto studentDto = studentService.findStudent(studentId);
        assertThat(studentDto).isNotNull();
        assertEquals(studentId, studentDto.getId());
    }
}
