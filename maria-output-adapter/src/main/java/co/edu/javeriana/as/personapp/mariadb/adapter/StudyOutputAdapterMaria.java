package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.StudyId;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.EstudiosRepositoryMaria;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("studyOutputAdapterMaria")
@Transactional
public class StudyOutputAdapterMaria implements StudyOutputPort {

    @Autowired
    private EstudiosRepositoryMaria estudiosRepositoryMaria;

    @Autowired
    private EstudiosMapperMaria estudiosMapperMaria;

    @Override
    public Study save(Study study) {
        log.debug("Saving study in MariaDB");
        EstudiosEntity estudioEntity = estudiosMapperMaria.fromDomainToAdapter(study);
        EstudiosEntity persistedEstudio = estudiosRepositoryMaria.save(estudioEntity);
        return estudiosMapperMaria.fromAdapterToDomain(persistedEstudio);
    }

    @Override
    public Boolean delete(StudyId studyId) {
        log.debug("Deleting study in MariaDB");
        estudiosRepositoryMaria.deleteById(new EstudiosEntityPK(studyId.getProfession(), studyId.getPerson()));
        return !estudiosRepositoryMaria.existsById(new EstudiosEntityPK(studyId.getProfession(), studyId.getPerson()));
    }

    @Override
    public List<Study> find() {
        log.debug("Finding all studies in MariaDB");
        return estudiosRepositoryMaria.findAll().stream()
                .map(estudiosMapperMaria::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Study findById(StudyId studyId) {
        log.debug("Finding study by ID in MariaDB");
        return estudiosRepositoryMaria.findById(new EstudiosEntityPK(studyId.getProfession(), studyId.getPerson()))
                .map(estudiosMapperMaria::fromAdapterToDomain)
                .orElse(null);
    }
}
