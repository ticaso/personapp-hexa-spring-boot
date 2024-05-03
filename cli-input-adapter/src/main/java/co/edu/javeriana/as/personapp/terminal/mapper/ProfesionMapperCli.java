package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;

@Mapper
public class ProfesionMapperCli {

    public ProfesionModelCli fromDomainToAdapterCli(Profesion profesion) {
        ProfesionModelCli profesionModelCli = new ProfesionModelCli();
        profesionModelCli.setId(profesion.getId());
        profesionModelCli.setNom(profesion.getNom());
        profesionModelCli.setDes(profesion.getDes());
        return profesionModelCli;
    }
}
