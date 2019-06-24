package com.sonal.titanic.passenger.details.serviceImpl;

import java.sql.SQLException;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.sonal.titanic.passenger.details.controller.FileController;
import com.sonal.titanic.passenger.details.dao.FileDao;
import com.sonal.titanic.passenger.details.entities.Passenger;
import com.sonal.titanic.passenger.details.service.FileService;

@Service
@ComponentScan("com.sonal.titanic.passenger.dao")
public class FileServiceImpl implements FileService{

	private static final Logger logger = Logger.getLogger(FileServiceImpl.class);
	
	@Autowired
	FileDao fileDao;
	
	
	public void savePassenger(Passenger passenger)throws SQLException {
		// TODO Auto-generated method stub
		 logger.debug("Inside savePassenger Method in FileServiceImpl");
		 fileDao.savePassenger(passenger);
	}

	/*
	 * public List<Passenger> getPassengersList() { // TODO Auto-generated method
	 * stub return fileDao.getPassengersList(); }
	 */
	public void deletePassenger(int passengerId) throws SQLException {
		// TODO Auto-generated method stub
		 logger.debug("Inside deletePassenger Method in FileServiceImpl");
		 fileDao.deletePassenger(passengerId);
	}

	public void saveOrUpdate(Passenger passenger) throws SQLException {
		// TODO Auto-generated method stub
		logger.debug("Inside saveOrUpdate Method in FileServiceImpl");
	    fileDao.saveOrUpdate(passenger);
	}

	public List<Passenger> getPassengerListByGender(String gender)  throws SQLException{
		logger.debug("Inside getPassengerListByGender Method in FileServiceImpl");
		return fileDao.getPassengerListByGender(gender);
	}

	public List<Passenger> getPassengerListByPaginationFilteringSorting(int pageNo, int pageSize, String genderValue,
			String sortDataField, String sortOrder, int filtersCount,String filterValue,String filterCondition,
			String filterDataField,String filterOperator) throws SQLException{
		logger.debug("Inside getPassengerListByPagination Method in FileServiceImpl");
		return fileDao.getPassengerListByPaginationFilteringSorting(pageNo, pageSize,genderValue,sortDataField,sortOrder,filtersCount,
				filterValue,filterCondition,filterDataField,filterOperator);
	}

	public void updatePassenger(String passengerName,String passengerAge,int passengerId)throws SQLException {
		logger.debug("Inside updatePassenger Method in FileServiceImpl");
		 fileDao.updatePassenger(passengerName, passengerAge, passengerId);
	}
	
	public void deleteExistingPassengers()throws SQLException{
		logger.debug("Inside deleteExistingPassengers Method in FileServiceImpl");
		fileDao.deleteExistingPassengers();
	}

	public  List<Passenger> getPassengerCountByGenderAndFilter(String genderValue, int filtersCount, String filterValue,
			String filterCondition, String filterDataField) throws SQLException {
		// TODO Auto-generated method stub
		return fileDao.getPassengerCountByGenderAndFilter(genderValue, filtersCount, filterValue, filterCondition, filterDataField);
	}

}
