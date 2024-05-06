package co.edu.javeriana.as.personapp.terminal.model;

import java.time.LocalDate;

import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profesion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioModelCli {
    private Person person;
    private Profesion profession;
    private LocalDate graduationDate;
    private String universityName;
}
