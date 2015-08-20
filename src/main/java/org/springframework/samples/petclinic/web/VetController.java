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

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Supplies;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.model.Visits;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Ashvarya Garg
 */
@Controller
public class VetController {

	private final ClinicService clinicService;

	@Autowired
	public VetController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}

	@ModelAttribute("status")
	public DateTime compareDates() {
		Date date = new Date();
		DateTime dt = new DateTime(date);
		return dt;
	}

	@RequestMapping(value = "/visits.html")
	public String showVisitList(Map<String, Object> model) {
		Visits visits = new Visits();
		visits.getVisitList().addAll(this.clinicService.findVisits());
		model.put("visits", visits);
		return "visits/visitList";
	}

	@RequestMapping(value = "/billing.html")
	public String showBillingList(Map<String, Object> model) {
		Visits visits = new Visits();
		visits.getVisitList().addAll(this.clinicService.findVisits());
		model.put("visits", visits);
		return "visits/billingList";
	}

	@RequestMapping(value = "/supplies.html")
	public String showSuppliesList(Map<String, Object> model) {
		Supplies supplies = new Supplies();
		supplies.getSupplyList().addAll(this.clinicService.findSupplies());
		model.put("supplies", supplies);
		return "supplies/supplyList";
	}

	@RequestMapping(value = { "/vets.xml", "/vets.html", })
	public String showVetList(Map<String, Object> model) {
		// Here we are returning an object of type 'Vets' rather than a
		// collection of Vet objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.put("vets", vets);
		return "vets/vetList";
	}

	@RequestMapping(value = "/assignVet.html")
	public String initAssignVetList(Map<String, Object> model) {
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		model.put("vets", vets);
		return "vets/assignVet";
	}

	@RequestMapping("/vets.json")
	public @ResponseBody Vets showResourcesVetList() {
		// Here we are returning an object of type 'Vets' rather than a
		// collection of Vet objects
		// so it is simpler for JSon/Object mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		return vets;
	}
}
