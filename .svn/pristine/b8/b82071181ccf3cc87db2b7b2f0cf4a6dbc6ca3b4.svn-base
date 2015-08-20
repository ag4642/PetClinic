package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Supply;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
/**
 * 
 * @author Ashvarya Garg
 *
 */
public class SupplyValidator {
	public void validate(Supply supply, Errors errors) {
		String item = supply.getItem();
		String purpose = supply.getPurpose();
		int quantity = supply.getQuantity();
		int ordered = supply.getOrdered();

		if (!StringUtils.hasLength(item)) {
			errors.rejectValue("item", "required", "required");
		}

		if (!StringUtils.hasLength(purpose)) {
			errors.rejectValue("purpose", "required", "required");
		}

		if (quantity < 0) {
			errors.rejectValue("quantity", "Cannot have negative quantity!", "Cannot have negative quantity!");
		}
		if (ordered < 0) {
			errors.rejectValue("ordered", "Cannot order negative amount!", "Cannot order negative amount!");
		}
	}
}