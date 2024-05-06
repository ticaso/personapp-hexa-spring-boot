package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.StudyId;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.EstudiosMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.EstudioRepositoryMongo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("studyOutputAdapterMongo")
@Component
public class StudyOutputAdapterMongo implements StudyOutputPort {

    @Autowired
    private EstudioRepositoryMongo estudiosRepositoryMongo;

    @Autowired
    private EstudiosMapperMongo estudiosMapperMongo;

    @Override
    public Study save(Study study) {
        log.debug("Saving study in MongoDB");
        EstudiosDocument estudioDocument = estudiosMapperMongo.fromDomainToAdapter(study);
        EstudiosDocument persistedEstudio = estudiosRepositoryMongo.save(estudioDocument);
        return estudiosMapperMongo.fromAdapterToDomain(persistedEstudio);
    }

    @Override
    public Boolean delete(StudyId studyId) {
        log.debug("Deleting study in MongoDB");
        try {
            estudiosRepositoryMongo.deleteById(studyId.toString());
            return !estudiosRepositoryMongo.existsById(studyId.toString());
        } catch (Exception e) {
            log.error("Error deleting study: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Study> find() {
        log.debug("Finding all studies in MongoDB");
        return estudiosRepositoryMongo.findAll().stream()
                .map(estudiosMapperMongo::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Study findById(StudyId studyId) {
        log.debug("Finding study by ID in MongoDB");
        return estudiosRepositoryMongo.findById(studyId.toString())
                .map(estudiosMapperMongo::fromAdapterToDomain)
                .orElse(null);
    }
}