package org.springframework.samples.petclinic.web;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class VisitValidator2 {
	public void validate(Visit visit, Errors errors) {
		String description = visit.getDescription();

		String vet1 = visit.getVet();
		String vet2 = visit.getVet2();
		// description validation
		if (!StringUtils.hasLength(description)) {
			errors.rejectValue("description", "required", "required");
		}

		Date date = new Date();
		DateTime dt = new DateTime(date);
		// date validation
		if (visit.getDate() == null) {
			errors.rejectValue("date", "required", "required");
		}
		if (visit.getDate().plusDays(1).compareTo(dt) < 0) {
			errors.rejectValue("date", "required", "future date required");
		}
		if (vet2.compareTo(vet1) == 0) {
			errors.rejectValue("vet2", "Must be different", "Must be different");
		}
	}
}