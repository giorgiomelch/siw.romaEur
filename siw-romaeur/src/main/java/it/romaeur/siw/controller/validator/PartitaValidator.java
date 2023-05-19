package it.romaeur.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.romaeur.siw.model.Partita;
import it.romaeur.siw.repository.PartitaRepository;

@Component
public class PartitaValidator implements Validator {

	@Autowired PartitaRepository partitaRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Partita.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Partita partita=(Partita)target;
		if(partita.getNomeSquadraAvversaria()!=null && partita.getData()!=null && 
				this.partitaRepository.existsByNomeSquadraAvversariaAndData
				(partita.getNomeSquadraAvversaria(), partita.getData()))
			errors.reject("partita.duplicate");
	}

}
