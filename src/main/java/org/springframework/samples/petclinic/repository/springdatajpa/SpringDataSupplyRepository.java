package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Supply;
import org.springframework.samples.petclinic.repository.SupplyRepository;
/**
 * 
 * @author Ashvarya Garg
 *
 */
public interface SpringDataSupplyRepository extends SupplyRepository, Repository<Supply, Integer> {

	@Override
	@Query("SELECT supply FROM Supply supply ORDER BY supply.id")
	List<Supply> findAllSupplies() throws DataAccessException;
}
