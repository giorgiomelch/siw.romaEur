package it.romaeur.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.romaeur.siw.model.Prestazione;

@Component
public class PrestazioneValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Prestazione.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		//Prestazione prestazione=(Prestazione)o;
		//if(prestazione!=null && prestazione.getGiocatore())
	}

}
