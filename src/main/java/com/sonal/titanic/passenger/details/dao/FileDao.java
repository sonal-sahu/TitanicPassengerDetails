package com.sonal.titanic.passenger.details.dao;

import java.sql.SQLException;
import java.util.List;

import com.sonal.titanic.passenger.details.entities.Passenger;

public interface FileDao {

	public void savePassenger(Passenger passenger) throws SQLException;
	public void deletePassenger (int passengerId) throws SQLException;
	public void saveOrUpdate (Passenger passenger) throws SQLException;
	public List<Passenger> getPassengerListByGender(String gender) throws SQLException;
	public List<Passenger> getPassengerListByPaginationFilteringSorting(int pageNo, int pageSize, String genderValue,
			String sortDataField, String sortOrder, int filtersCount,String filterValue,String filterCondition,
			String filterDataField,String filterOperator) throws SQLException;
	public void updatePassenger(String passengerName,String passengerAge,int passengerId) throws SQLException;
	public void deleteExistingPassengers()throws SQLException;
	public List<Passenger> getPassengerCountByGenderAndFilter(String genderValue,int filtersCount,String filterValue,String filterCondition,
			String filterDataField)throws SQLException;
}
