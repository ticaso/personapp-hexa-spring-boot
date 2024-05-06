package co.edu.javeriana.as.personapp.application.port.out;

import java.util.List;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.domain.StudyId;

@Port
public interface StudyOutputPort {
    public Study save(Study profesion);

    public Boolean delete(StudyId id);

    public List<Study> find();

    public Study findById(StudyId id);
}
