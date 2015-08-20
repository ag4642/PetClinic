package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Visit;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
/**
 * 
 * @author Ashvarya Garg
 *
 */
public class VisitValidator {
	public void validate(Visit visit, Errors errors) {
		String description = visit.getDescription();

		String vet1 = visit.getVet();
		if (vet1 == null) {
			vet1 = "";
		}
		String vet2 = visit.getVet2();
		if (vet2 == null) {
			vet2 = "";
		}
		// description validation
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "required");
		}

		// date validation
		if (visit.getDate() == null) {
			errors.rejectValue("date", "required", "required");
		}
		if (vet2.compareTo(vet1) == 0 && StringUtils.hasLength(vet1)) {
			errors.rejectValue("vet2", "Must be different", "Must be different");
		}
	}
}