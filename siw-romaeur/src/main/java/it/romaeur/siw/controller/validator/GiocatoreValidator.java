package it.romaeur.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.repository.GiocatoreRepository;

@Component
public class GiocatoreValidator implements Validator {

	@Autowired GiocatoreRepository giocatoreRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Giocatore.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Giocatore giocatore=(Giocatore)target;
		if(giocatore.getNome()!=null && giocatore.getCognome()!=null && giocatore.getDataDiNascita()!=null
				&& this.giocatoreRepository.existsByNomeAndCognomeAndDataDiNascita
				(giocatore.getNome(), giocatore.getCognome(),giocatore.getDataDiNascita()))
			errors.reject("giocatore.duplicate");
	}

}
