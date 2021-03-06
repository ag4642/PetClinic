package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
/**
* @author Ashvarya Garg
*/
@Entity
@Table(name = "supplies")
public class Supply extends BaseEntity {

	@NotEmpty
	@Column(name = "item")
	private String item;

	@JoinColumn(name = "purpose")
	private String purpose;

	@JoinColumn(name = "quantity")
	private int quantity;

	@JoinColumn(name = "ordered")
	private int ordered;

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getPurpose() {
		return this.purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getOrdered() {
		return this.ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}
}
