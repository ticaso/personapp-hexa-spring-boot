package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;

@Mapper
public class ProfesionMapperMaria {

	@Autowired
	private EstudiosMapperMaria estudiosMapperMaria;

	public ProfesionEntity fromDomainToAdapter(Profesion profession) {
		ProfesionEntity profesionEntity = new ProfesionEntity();
		profesionEntity.setId(profession.getId());
		profesionEntity.setNom(profession.getNom());
		profesionEntity.setDes(validateDes(profession.getDes()));
		profesionEntity.setEstudios(validateEstudios(profession.getStudies()));
		return profesionEntity;
	}

	private String validateDes(String description) {
		return description != null ? description : "";
	}

	private List<EstudiosEntity> validateEstudios(List<Study> studies) {
		return studies != null && !studies.isEmpty() ? studies.stream()
				.map(study -> estudiosMapperMaria.fromDomainToAdapter(study)).collect(Collectors.toList())
				: new ArrayList<EstudiosEntity>();
	}

	public Profesion fromAdapterToDomain(ProfesionEntity profesionEntity) {
		Profesion profession = new Profesion();
		profession.setId(profesionEntity.getId());
		profession.setNom(profesionEntity.getNom());
		profession.setDes(validateDescription(profesionEntity.getDes()));
		profession.setStudies(validateStudies(profesionEntity.getEstudios()));
		return profession;
	}

	private String validateDescription(String des) {
		return des != null ? des : "";
	}

	private List<Study> validateStudies(List<EstudiosEntity> estudiosEntity) {
		return estudiosEntity != null && !estudiosEntity.isEmpty() ? estudiosEntity.stream()
				.map(estudio -> estudiosMapperMaria.fromAdapterToDomain(estudio)).collect(Collectors.toList())
				: new ArrayList<Study>();
	}
}
