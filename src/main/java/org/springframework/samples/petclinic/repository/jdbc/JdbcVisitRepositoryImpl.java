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
package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation of the {@link VisitRepository} interface.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @author Sam Brannen
 * @author Thomas Risberg
 * @author Mark Fisher
 * @author Michael Isvy
 */
@Repository
public class JdbcVisitRepositoryImpl implements VisitRepository {

	private JdbcTemplate jdbcTemplate;

	private SimpleJdbcInsert insertVisit;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private PetRepository petRepository;
	private ClinicService clinicService;
	private JdbcPetRepositoryImpl JdbcPetRepositoryImpl;

	@Autowired
	public JdbcVisitRepositoryImpl(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.insertVisit = new SimpleJdbcInsert(dataSource).withTableName("visits").usingGeneratedKeyColumns("id");
	}

	@Override
	public void save(Visit visit) throws DataAccessException {
		if (visit.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(createVisitParameterSource(visit));
			visit.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE visits SET id=:id, pet_id=:pet_id, visit_date=:visit_date, description=:description,vet=:vet, vet2=:vet2, diagnosis=:diagnosis, paid=:paid WHERE id=:id",
					createVisitParameterSource(visit));
		}
	}

	/**
	 * Creates a {@link MapSqlParameterSource} based on data values from the
	 * supplied {@link Visit} instance.
	 */
	private MapSqlParameterSource createVisitParameterSource(Visit visit) {
		return new MapSqlParameterSource().addValue("id", visit.getId())
				.addValue("visit_date", visit.getDate().toDate()).addValue("description", visit.getDescription())
				.addValue("pet_id", visit.getPet().getId()).addValue("vet", visit.getVet())
				.addValue("vet2", visit.getVet2()).addValue("diagnosis", visit.getDiagnosis())
				.addValue("paid", visit.getPaid());
	}

	@Override
	public List<Visit> findByPetId(Integer petId) {
		final List<Visit> visits = this.jdbcTemplate.query(
				"SELECT id, visit_date, description,vet, vet2, diagnosis, paid FROM visits WHERE pet_id=?",
				new BeanPropertyRowMapper<Visit>() {
					@Override
					public Visit mapRow(ResultSet rs, int row) throws SQLException {
						Visit visit = new Visit();
						visit.setId(rs.getInt("id"));
						Pet pet = new Pet();
						pet.setId(petId);
						visit.setPet(pet);
						java.sql.Date dates = rs.getDate("visit_date");
						DateTime date = new DateTime(dates);
						visit.setDate(date.minusDays(1));
						visit.setDescription(rs.getString("description"));
						visit.setDiagnosis(rs.getString("diagnosis"));
						Vet vet = new Vet();
						vet.setFirstName(rs.getString("vet"));
						visit.setVet(vet.getFirstName());
						Vet vet2 = new Vet();
						vet2.setFirstName(rs.getString("vet2"));
						visit.setVet(vet2.getFirstName());
						visit.setPaid(rs.getBoolean("paid"));
						return visit;
					}
				}, petId);
		return visits;
	}

	@Override
	public Collection<Visit> findAllVisits() throws DataAccessException {
		final List<Visit> visits = this.jdbcTemplate.query(
				"SELECT id, pet_id, visit_date, description, vet, vet2, diagnosis, paid FROM visits",
				new BeanPropertyRowMapper<Visit>() {
					@Override
					public Visit mapRow(ResultSet rs, int row) throws SQLException {
						Visit visit = new Visit();
						visit.setId(rs.getInt("id"));
						Pet pet = new Pet();
						pet.setId(rs.getInt("pet_id"));
						visit.setPet(pet);
						java.sql.Date dates = rs.getDate("visit_date");
						DateTime date = new DateTime(dates);
						visit.setDate(date.minusDays(1));
						visit.setDescription(rs.getString("description"));
						visit.setDiagnosis(rs.getString("diagnosis"));
						Vet vet = new Vet();
						vet.setFirstName(rs.getString("vet"));
						visit.setVet(vet.getFirstName());
						Vet vet2 = new Vet();
						vet2.setFirstName(rs.getString("vet2"));
						visit.setVet(vet2.getFirstName());
						visit.setPaid(rs.getBoolean("paid"));
						return visit;
					}
				});

		return visits;
	}

	@Override
	public Visit findVisitById(int id) {
		Visit visit;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			visit = this.namedParameterJdbcTemplate.queryForObject(
					"SELECT id, pet_id, visit_date, description, vet, vet2, diagnosis, paid FROM visits WHERE id=:id",
					params, new BeanPropertyRowMapper<Visit>() {
						@Override
						public Visit mapRow(ResultSet rs, int row) throws SQLException {
							Visit visit = new Visit();
							visit.setId(rs.getInt("id"));
							Pet pet = new Pet();
							pet.setId(rs.getInt("pet_id"));
							visit.setPet(pet);
							java.sql.Date dates = rs.getDate("visit_date");
							DateTime date = new DateTime(dates);
							visit.setDate(date.minusDays(1));
							visit.setDescription(rs.getString("description"));
							visit.setDiagnosis(rs.getString("diagnosis"));
							Vet vet = new Vet();
							vet.setFirstName(rs.getString("vet"));
							visit.setVet(vet.getFirstName());
							Vet vet2 = new Vet();
							vet2.setFirstName(rs.getString("vet2"));
							visit.setVet(vet2.getFirstName());
							visit.setPaid(rs.getBoolean("paid"));
							return visit;
						}
					});
		} catch (EmptyResultDataAccessException ex) {
			throw new ObjectRetrievalFailureException(Visit.class, new Integer(id));
		}
		return visit;
	}

	@Override
	public void unassign(Integer visitId) throws DataAccessException {
		Visit visit = findVisitById(visitId);
		visit.setVet("");
		visit.setVet2("");
		if (visit.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(createVisitParameterSource(visit));
			visit.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE visits SET id=:id, pet_id=:pet_id, visit_date=:visit_date, description=:description,vet=:vet, vet2=:vet2, diagnosis=:diagnosis, paid=:paid WHERE id=:id",
					createVisitParameterSource(visit));
		}
	}

	@Override
	public void paid(Integer visitId, Boolean b) throws DataAccessException {
		Visit visit = findVisitById(visitId);
		visit.setPaid(true);
		if (visit.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(createVisitParameterSource(visit));
			visit.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE visits SET id=:id, pet_id=:pet_id, visit_date=:visit_date, description=:description,vet=:vet, vet2=:vet2, diagnosis=:diagnosis, paid=:paid WHERE id=:id",
					createVisitParameterSource(visit));

		}
	}
}