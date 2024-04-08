package ait.cohort34_1.student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentAddDto {
    private int id;
    @Setter
    private String name;
    @Setter
    private String password;
}
