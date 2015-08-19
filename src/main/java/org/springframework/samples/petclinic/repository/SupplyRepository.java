package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Supply;

public interface SupplyRepository {

	Collection<Supply> findAllSupplies() throws DataAccessException;

	Supply findSupplyById(int id) throws DataAccessException;

	void save(Supply supply) throws DataAccessException;

}
