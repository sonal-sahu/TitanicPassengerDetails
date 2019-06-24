package com.sonal.titanic.passenger.details.daoImpl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sonal.titanic.passenger.details.controller.FileController;
import com.sonal.titanic.passenger.details.dao.FileDao;
import com.sonal.titanic.passenger.details.entities.Passenger;

@Repository
@Transactional
public class FileDaoImpl implements FileDao {

	@Autowired
	SessionFactory session;

	private static final Logger logger = Logger.getLogger(FileDaoImpl.class);

	public void deleteExistingPassengers() throws SQLException {
		logger.debug("Inside deleteExistingPassengers Method in FileDaoImpl");
		String hql = "Delete From Passenger";
		Query query = session.getCurrentSession().createQuery(hql);
		query.executeUpdate();
	}

	public void savePassenger(Passenger passenger) throws SQLException {
		logger.debug("Inside savePassenger Method in FileDaoImpl");
		session.getCurrentSession().save(passenger);
	}

	public List<Passenger> getPassengerListByPaginationFilteringSorting(int pageNo, int pageSize, String genderValue,
			String sortDataField, String sortOrder, int filtersCount, String filterValue, String filterCondition,
			String filterDataField, String filterOperator) throws SQLException {
		logger.debug("Inside getPassengerListByPagination Method in FileDaoImpl");
		List<Passenger> passengeList = new ArrayList<Passenger>();
		String hql = "";
		if (filtersCount == 0) {
			if(sortDataField!=null && sortDataField.length()>0 && sortOrder!=null && sortOrder.length()>0)
				hql = "FROM Passenger where sex=:sex ORDER BY " + sortDataField + " " + sortOrder;
			else 
				hql = "FROM Passenger where sex=:sex";
		} else {
			
			hql = "FROM Passenger where sex=:sex and ";
			String condition = "";
			String value = "";

			if (filterCondition.equals("CONTAINS")) {
				condition = " LIKE ";
				value = "'%" + filterValue + "%'";
			} else if (filterCondition.equals("DOES_NOT_CONTAIN")) {
				condition = " NOT LIKE ";
				value = "'%" + filterValue + "%'";
			} else if (filterCondition.equals("EQUAL")) {
				condition = " = ";
				value = filterValue;
			} else if (filterCondition.equals("NOT_EQUAL")) {
				condition = " <> ";
				value = filterValue;
			} else if (filterCondition.equals("GREATER_THAN")) {
				condition = " > ";
				value = filterValue;
			} else if (filterCondition.equals("LESS_THAN")) {
				condition = " < ";
				value = filterValue;
			} else if (filterCondition.equals("GREATER_THAN_OR_EQUAL")) {
				condition = " >= ";
				value = filterValue;
			} else if (filterCondition.equals("LESS_THAN_OR_EQUAL")) {
				condition = " <= ";
				value = filterValue;
			} else if (filterCondition.equals("STARTS_WITH")) {
				condition = " LIKE ";
				value = "'" + filterValue + "%'";
			} else if (filterCondition.equals("ENDS_WITH")) {
				condition = " LIKE ";
				value = "'%" + filterValue + "'";
			} else if (filterCondition.equals("NULL")) {
				condition = " IS NULL ";
				value = "'%" + filterValue + "%'";
			} else if (filterCondition.equals("NULL")) {
				condition = " IS NOT NULL ";
				value = "'%" + filterValue + "%'";
			}
			if(sortDataField!=null && sortDataField.length()>0 && sortOrder!=null && sortOrder.length()>0) 
				hql += filterDataField + " " + condition + " " + value + " ORDER BY " + sortDataField + " " + sortOrder;
			else
				hql += filterDataField + " " + condition + " " + value ;
			
		}

		Query query = session.getCurrentSession().createQuery(hql);
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);
		query.setParameter("sex", genderValue);
		passengeList = query.list();
		return passengeList;
	}

	public List<Passenger> getPassengerCountByGenderAndFilter(String genderValue, int filtersCount, String filterValue,
			String filterCondition, String filterDataField) throws SQLException {
		logger.debug("Inside getPassengerCountByGenderAndFilter Method in FileDaoImpl");
		String hql = "FROM Passenger where sex=:sex and ";
		String condition = "";
		String value = "";

		if (filterCondition.equals("CONTAINS")) {
			condition = " LIKE ";
			value = "'%" + filterValue + "%'";
		} else if (filterCondition.equals("DOES_NOT_CONTAIN")) {
			condition = " NOT LIKE ";
			value = "'%" + filterValue + "%'";
		} else if (filterCondition.equals("EQUAL")) {
			condition = " = ";
			value = filterValue;
		} else if (filterCondition.equals("NOT_EQUAL")) {
			condition = " <> ";
			value = filterValue;
		} else if (filterCondition.equals("GREATER_THAN")) {
			condition = " > ";
			value = filterValue;
		} else if (filterCondition.equals("LESS_THAN")) {
			condition = " < ";
			value = filterValue;
		} else if (filterCondition.equals("GREATER_THAN_OR_EQUAL")) {
			condition = " >= ";
			value = filterValue;
		} else if (filterCondition.equals("LESS_THAN_OR_EQUAL")) {
			condition = " <= ";
			value = filterValue;
		} else if (filterCondition.equals("STARTS_WITH")) {
			condition = " LIKE ";
			value = "'" + filterValue + "%'";
		} else if (filterCondition.equals("ENDS_WITH")) {
			condition = " LIKE ";
			value = "'%" + filterValue + "'";
		} else if (filterCondition.equals("NULL")) {
			condition = " IS NULL ";
			value = "'%" + filterValue + "%'";
		} else if (filterCondition.equals("NULL")) {
			condition = " IS NOT NULL ";
			value = "'%" + filterValue + "%'";
		}
		hql += filterDataField + " " + condition + " " + value;
		Query query = session.getCurrentSession().createQuery(hql);
		query.setParameter("sex", genderValue);
		return query.list();

	}

	public void deletePassenger(int passengerId) throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("Inside getPassengerListByPagination Method in FileDaoImpl");
		String hql = "delete Passenger where passenger_id=:passengerId";
		Query query = session.getCurrentSession().createQuery(hql);
		query.setParameter("passengerId", passengerId);
		query.executeUpdate();

	}

	public void saveOrUpdate(Passenger passenger) throws SQLException {
		logger.debug("Inside saveOrUpdate Method in FileDaoImpl");
		session.getCurrentSession().saveOrUpdate(passenger);
	}

	public List<Passenger> getPassengerListByGender(String gender) throws SQLException {
		logger.debug("Inside getPassengerListByGender Method in FileDaoImpl");
		String hql = "from Passenger where sex=:sex";
		Query query = session.openSession().createQuery(hql);
		query.setParameter("sex", gender);
		return query.list();
	}

	public void updatePassenger(String passengerName, String passengerAge, int passengerId) throws SQLException {
		logger.debug("Inside updatePassenger Method in FileDaoImpl");
		String hql = "UPDATE Passenger SET name=:name, age=:age where passenger_id=:passenger_id";
		Query query = session.getCurrentSession().createQuery(hql);
		query.setParameter("name", passengerName);
		query.setParameter("age", passengerAge);
		query.setParameter("passenger_id", passengerId);
		query.executeUpdate();

	}

	
}
