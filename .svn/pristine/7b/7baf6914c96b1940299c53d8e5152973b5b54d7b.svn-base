package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Supply;
import org.springframework.samples.petclinic.repository.SupplyRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaSupplyRepositoryImpl implements SupplyRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Supply> findAllSupplies() throws DataAccessException {
		return this.em.createQuery("SELECT supply FROM Supply supply").getResultList();
	}

	@Override
	public Supply findSupplyById(int id) throws DataAccessException {
		return this.em.find(Supply.class, id);
	}

	@Override
	@Transactional
	public void save(Supply supply) throws DataAccessException {
		if (supply.getId() == null) {
			this.em.persist(supply);
		} else {
			this.em.merge(supply);
		}
	}
}