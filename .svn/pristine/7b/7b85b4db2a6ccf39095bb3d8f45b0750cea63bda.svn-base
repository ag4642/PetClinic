package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
/**
* @author Ashvarya Garg
*/
public class Supplies {
	private List<Supply> supplies;

	@XmlElement
	public List<Supply> getSupplyList() {
		if (supplies == null) {
			supplies = new ArrayList<Supply>();
		}
		return supplies;
	}
}
