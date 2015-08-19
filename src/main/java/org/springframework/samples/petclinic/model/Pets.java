package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Pets {
	   private List<Pet> pets;

	    @XmlElement
	    public List<Pet> getPetList() {
	        if (pets == null) {
	            pets = new ArrayList<Pet>();
	        }
	        return pets;
	    }
}
