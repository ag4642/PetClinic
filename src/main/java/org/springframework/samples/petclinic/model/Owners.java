package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Owners {
	  private List<Owner> owners;

	    @XmlElement
	    public List<Owner> getOwnerList() {
	        if (owners == null) {
	            owners = new ArrayList<Owner>();
	        }
	        return owners;
	    }

}
