/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@SessionAttributes("visits")
public class VisitController {

	private final ClinicService clinicService;

	@Autowired
	public VisitController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	/**
	 * Called before each and every @RequestMapping annotated method. 2 goals: -
	 * Make sure we always have fresh data - Since we do not use the session
	 * scope, make sure that Pet object always has an id (Even though id is not
	 * part of the form fields)
	 * 
	 * @param petId
	 * @return Pet
	 */
	@ModelAttribute("visits")
	public Visit loadPetWithVisit(@PathVariable("petId") int petId) {
		Pet pet = this.clinicService.findPetById(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		return visit;
	}

	@ModelAttribute("vets")
	public Collection<String> populateVets() {
		ArrayList<Vet> vets = (ArrayList<Vet>) this.clinicService.findVets();
		ArrayList<String> vetnames = new ArrayList<String>();
		for (Vet vet : vets) {
			vetnames.add(vet.getFirstName() + " " + vet.getLastName());
		}
		return vetnames;
	}

	@ModelAttribute("vets2")
	public Collection<String> populateVets2() {
		ArrayList<Vet> vets = (ArrayList<Vet>) this.clinicService.findVets();
		ArrayList<String> vetnames = new ArrayList<String>();
		for (Vet vet : vets) {
			vetnames.add(vet.getFirstName() + " " + vet.getLastName());
		}
		return vetnames;
	}

	@RequestMapping(value = "/owners/*/pets/{petId}/visits", method = RequestMethod.GET)
	public String showVisits(@PathVariable int petId, Map<String, Object> model) {
		model.put("visits", this.clinicService.findPetById(petId).getVisits());
		return "visitList";
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
	// called
	@RequestMapping(value = "/owners/*/pets/{petId}/visits/new", method = RequestMethod.GET)
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "visits/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm
	// is called
	@RequestMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new", method = { RequestMethod.POST,
			RequestMethod.PUT })
	public String processNewVisitForm(@ModelAttribute("visits") Visit visit, BindingResult result,
			SessionStatus status) {
		new VisitValidator2().validate(visit, result);
		if (result.hasErrors()) {
			return "visits/createOrUpdateVisitForm";
		} else {
			if (visit.getVet() == null) {
				visit.setVet("");
			}
			this.clinicService.saveVisit(visit);
			status.setComplete();
			return "redirect:/owners/{ownerId}";
		}
	}

	@RequestMapping(value = "/pets/{petId}/visits/{visitId}/edit", method = RequestMethod.GET)
	public String initEditVisitForm(@PathVariable("visitId") int visitId, @PathVariable("petId") int petId,
			Map<String, Object> model) {
		model.put("visits", this.clinicService.findVisitById(visitId));
		return "visits/createOrUpdateVisitForm";
	}

	@RequestMapping(value = "/pets/{petId}/visits/{visitId}/edit", method = { RequestMethod.PUT, RequestMethod.POST })
	public String processEditVisitForm(@ModelAttribute("visits") Visit visit, BindingResult result,
			SessionStatus status) {
		new VisitValidator().validate(visit, result);
		if (result.hasErrors()) {
			return "visits/createOrUpdateVisitForm";
		} else {
			this.clinicService.saveVisit(visit);
			status.setComplete();
			return "redirect:/visits.html";
		}
	}

	@RequestMapping(value = "/pets/{petId}/visits/{visitId}/unassign", method = RequestMethod.GET)
	public String initUnassignVetForm(@PathVariable("visitId") int visitId, @PathVariable("petId") int petId,
			Map<String, Object> model) {
		this.clinicService.unassignVet(visitId);
		return "redirect:/visits.html";
	}

	@RequestMapping(value = "/pets/{petId}/visits/{visitId}/paid", method = RequestMethod.GET)
	public String initPaidVisitForm(@PathVariable("visitId") int visitId, @PathVariable("petId") int petId,
			Map<String, Object> model) {
		this.clinicService.markPaid(visitId, true);
		return "redirect:/billing.html";
	}
}