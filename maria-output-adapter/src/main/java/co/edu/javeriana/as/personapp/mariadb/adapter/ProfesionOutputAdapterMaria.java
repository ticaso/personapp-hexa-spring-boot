package co.edu.javeriana.as.personapp.mariadb.adapter;


import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.application.port.out.ProfesionOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.ProfesionMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.ProfesionRepositoryMaria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("profesionOutputAdapterMaria")
@Transactional
public class ProfesionOutputAdapterMaria implements ProfesionOutputPort {

    @Autowired
    private ProfesionRepositoryMaria profesionRepositoryMaria;

    @Autowired
    private ProfesionMapperMaria profesionMapperMaria;

    @Override
    public Profesion save(Profesion profesion) {
        log.debug("Into save on Adapter MariaDB");
        ProfesionEntity persistedProfesion = profesionRepositoryMaria.save(profesionMapperMaria.fromDomainToAdapter(profesion));
        return profesionMapperMaria.fromAdapterToDomain(persistedProfesion);
    }

    @Override
    public Boolean delete(Integer id) {
        log.debug("Into delete on Adapter MariaDB");
        profesionRepositoryMaria.deleteById(id);
        return profesionRepositoryMaria.findById(id).isEmpty();
    }

    @Override
    public List<Profesion> find() {
        log.debug("Into find on Adapter MariaDB");
        return profesionRepositoryMaria.findAll().stream()
                .map(profesionMapperMaria::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Profesion findById(Integer id) {
        log.debug("Into findById on Adapter MariaDB");
        return profesionRepositoryMaria.findById(id)
                .map(profesionMapperMaria::fromAdapterToDomain)
                .orElse(null);
    }
}
