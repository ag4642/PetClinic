package org.springframework.samples.petclinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.samples.petclinic.model.Supply;
import org.springframework.samples.petclinic.repository.SupplyRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ashvarya Garg
 *
 */
@Repository
public class JdbcSupplyRepositoryImpl implements SupplyRepository {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertSupply;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public JdbcSupplyRepositoryImpl(DataSource dataSource) {
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.insertSupply = new SimpleJdbcInsert(dataSource).withTableName("supplies").usingGeneratedKeyColumns("id");
	}

	private MapSqlParameterSource createSupplyParameterSource(Supply supply) {
		return new MapSqlParameterSource().addValue("id", supply.getId()).addValue("item", supply.getItem())
				.addValue("purpose", supply.getPurpose()).addValue("quantity", supply.getQuantity())
				.addValue("ordered", supply.getOrdered());
	}

	@Override
	public Collection<Supply> findAllSupplies() throws DataAccessException {
		final List<Supply> supplies = this.jdbcTemplate.query(
				"SELECT id, item, purpose, quantity, ordered FROM supplies",
				BeanPropertyRowMapper.newInstance(Supply.class));
		return supplies;
	}

	@Override
	public Supply findSupplyById(int id) throws DataAccessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Supply supply = this.namedParameterJdbcTemplate.queryForObject(
				"SELECT id, item, purpose, quantity, ordered FROM supplies WHERE id=:id", params,
				BeanPropertyRowMapper.newInstance(Supply.class));
		return supply;
	}

	@Override
	public void save(Supply supply) throws DataAccessException {
		if (supply.isNew()) {
			Number newKey = this.insertSupply.executeAndReturnKey(createSupplyParameterSource(supply));
			supply.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE supplies SET item=:item, purpose=:purpose, quantity=:quantity, ordered=:ordered WHERE id=:id",
					createSupplyParameterSource(supply));
		}
	}
}