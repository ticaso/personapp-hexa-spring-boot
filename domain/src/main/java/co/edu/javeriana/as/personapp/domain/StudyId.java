package co.edu.javeriana.as.personapp.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyId implements Serializable {
    private Integer profession;
    private Integer person;
}
