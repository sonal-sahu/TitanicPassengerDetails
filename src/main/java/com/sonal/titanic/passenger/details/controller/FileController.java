package com.sonal.titanic.passenger.details.controller;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sonal.titanic.passenger.details.entities.Passenger;
import com.sonal.titanic.passenger.details.service.FileService;

/**
 * @author Sonal Sahu A controller that maps all request
 *
 */
@Controller
@ComponentScan("com.sonal.titanic.passenger.service")
public class FileController {

	@Autowired
	FileService fileService;

	private static final Logger logger = Logger.getLogger(FileController.class);

	/*
	 * a method to map /home request that sends fileUpload.jsp file in view
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView getHomePage() {

		ModelAndView view = new ModelAndView("fileUpload");
		logger.debug("Welcome to Home page");
		return view;
	}

	/*
	 * a method to map /uploadFile request that uploads file to the server
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadFile(MultipartHttpServletRequest request,
			HttpServletResponse response) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		List<Passenger> passengers = new ArrayList<Passenger>();
		String name = "";
		String error = "";
		String line = "";
		String temp = "";
		int flag = 0;

		try {
			logger.debug("Inside uploadFile method");
			fileService.deleteExistingPassengers();
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			InputStream input = mpf.getInputStream();
			// Create a buffered reader to read the file
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			reader.readLine();
			// Reading from the second line
			while ((line = reader.readLine()) != null) {
				String[] passengerDetails = line.split(",");
				Passenger passenger = new Passenger();

				if (passengerDetails.length > 0) {
					passenger.setPassenger_id(Integer.parseInt(passengerDetails[0]));
					passenger.setPassenger_class(passengerDetails[1]);
					temp = passengerDetails[2] + ", " + passengerDetails[3];
					passenger.setName(temp.replaceAll("^\"|\"$", ""));
					passenger.setSex(passengerDetails[4]);
					passenger.setAge(passengerDetails[5]);
					passenger.setSib_sp(passengerDetails[6]);
					passenger.setParch(passengerDetails[7]);
					passenger.setTicket(passengerDetails[8]);
					passenger.setFare(passengerDetails[9]);
					passenger.setCabin(passengerDetails[10]);
					passenger.setEmbarked(passengerDetails[11]);
					passengers.add(passenger);
				}
			}

			for (Passenger passenger : passengers) {
				fileService.saveOrUpdate(passenger);
			}
			map.put("filename", mpf.getOriginalFilename());
			map.put("status", "200");
			map.put("message", "file uploaded successfully");

		} catch (IOException ioException) {
			logger.error("Inside uploadFile method in Controller IOException", ioException);
			map.put("status", "500");
			map.put("message", "File Cannot Be Uploaded.Please try again");
		} catch (SQLException sqlException) {
			logger.error("Inside uploadFile method in Controller SQLException", sqlException);
			map.put("status", "500");
			map.put("message", "File Cannot Be Uploaded.Please try again");
		} catch (Exception e) {
			logger.error("Inside uploadFile method in Controller Exception", e);
			map.put("status", "500");
			map.put("message", "File Cannot Be Uploaded.Please try again");
		}

		return map;
	}

	/*
	 * a method to map /search request that searches records based on gender
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSearch(@RequestParam("searchByGender") String gender) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Passenger> passengerList = new ArrayList<Passenger>();
		try {
			logger.debug("Inside getSearch method");
			if (gender != null && gender.length() > 0) {
				passengerList = fileService.getPassengerListByGender(gender);
				if (passengerList != null) {
					map.put("status", "200");
					map.put("message", "Data Found");
					map.put("data", passengerList);
				} else {
					map.put("status", "404");
					map.put("message", "Data Not found");

				}
			}
		} catch (SQLException sqlException) {
			logger.error("Inside getSearch method in Controller with SQLException", sqlException);
			map.put("status", "500");
			map.put("message", "Data cannot be search.Please try again");
		} catch (Exception e) {
			logger.error("Inside getSearch method in Controller with Exception", e);
			map.put("status", "500");
			map.put("message", "Data cannot be search.Please try again");
		}
		return map;
	}

	/*
	 * a method to map /getPassengers request that shows records based on
	 * genderValue,sortDataField,
	 * sortOrder,pageNo,pageSize,filtersCount,filterValue,filterCondition,
	 * filterDataField, and filterOperator
	 */
	@RequestMapping(value = "/getPassengers", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPassengersListInGrid(@RequestParam("searchByGender") String genderValue,
			@RequestParam("sortdatafield") String sortDataField, @RequestParam("sortorder") String sortOrder,
			@RequestParam("pagenum") int pageNo, @RequestParam("pagesize") int pageSize,
			@RequestParam("filterscount") int filtersCount, @RequestParam("filtervalue0") String filterValue,
			@RequestParam("filtercondition0") String filterCondition,
			@RequestParam("filterdatafield0") String filterDataField,
			@RequestParam("filteroperator0") String filterOperator) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.debug("Inside getPassengersListInGrid method");
			if (filtersCount == 0) {
				filterValue = "";
				filterCondition = "";
				filterDataField = "";
				filterOperator = "";
				if (genderValue != null && genderValue.length() > 0) {
					logger.debug("Inside getPassengersListInGrid method without filtering");
					List<Passenger> passengerList = fileService.getPassengerListByPaginationFilteringSorting(pageNo,
							pageSize, genderValue, sortDataField, sortOrder, filtersCount, filterValue, filterCondition,
							filterDataField, filterOperator);
					int totalRows = fileService.getPassengerListByGender(genderValue).size();
					if (totalRows != 0 && passengerList != null && passengerList.size() > 0) {
						map.put("status", "200");
						map.put("totalRows", totalRows);
						map.put("rows", passengerList);
					} else {
						map.put("status", "404");
						map.put("message", "Data Not found");

					}
				} else {

					map.put("status", "404");
					map.put("message", "Data Not found");

				}

			} else {

				if (genderValue != null && genderValue.length() > 0 && filterValue != null && filterValue.length() > 0
						&& filterCondition != null && filterCondition.length() > 0 && filterDataField != null
						&& filterDataField.length() > 0 && filterOperator != null && filterOperator.length() > 0) {
					logger.debug("Inside getPassengersListInGrid method with filtering");
					int totalRows = fileService.getPassengerCountByGenderAndFilter(genderValue, filtersCount,
							filterValue, filterCondition, filterDataField).size();
					List<Passenger> passengerList = fileService.getPassengerListByPaginationFilteringSorting(pageNo,
							pageSize, genderValue, sortDataField, sortOrder, filtersCount, filterValue, filterCondition,
							filterDataField, filterOperator);
					if (totalRows != 0 && passengerList != null && passengerList.size() > 0) {
						map.put("status", "200");
						map.put("totalRows", totalRows);
						map.put("rows", passengerList);
					} else {
						map.put("status", "404");
						map.put("message", "Data Not found");

					}
				} else {
					map.put("status", "404");
					map.put("message", "Data Not found");

				}
			}

		} catch (SQLException sqlException) {
			logger.error("Inside getPassengersListInGrid method in Controller with SQLException", sqlException);
			map.put("status", "500");
			map.put("message", "Data cannot be search.Please try again");
		} catch (Exception e) {
			logger.error("Inside getPassengersListInGrid method in Controller with Exception", e);
			map.put("status", "500");
			map.put("message", "Data cannot be search.Please try again");
		}
		return map;
	}

	/*
	 * a method to map /addPassenger request that add Passenger record to the
	 * database
	 */
	@RequestMapping(value = "/addPassenger", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addPassenger(@RequestParam("data[passenger_id]") int passengerId,
			@RequestParam("data[passenger_class]") String passengerClass,
			@RequestParam("data[name]") String passengerName, @RequestParam("data[sex]") String passengerGender,
			@RequestParam("data[age]") String passengerAge, @RequestParam("data[sib_sp]") String passengerSib_sp,
			@RequestParam("data[parch]") String passengerParch, @RequestParam("data[ticket]") String passengerTicket,
			@RequestParam("data[fare]") String passengerFare, @RequestParam("data[cabin]") String passengerCabin,
			@RequestParam("data[embarked]") String passengerEmbarked,
			@RequestParam("searchByGender") String genderValue, @RequestParam("filterValue") String filterValue,
			@RequestParam("filterDataField") String filterDataField,
			@RequestParam("filterCondition") String filterCondition) {

		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("Inside addPassenger Method in Controller");
		try {
			if (passengerId != 0 && passengerClass != null && passengerName != null && passengerGender != null
					&& passengerSib_sp != null && passengerParch != null && passengerTicket != null
					&& passengerFare != null && passengerCabin != null && passengerEmbarked != null
					&& passengerAge != null && genderValue != null && genderValue.length() > 0) {
				Passenger passenger = new Passenger();
				passenger.setPassenger_id(passengerId);
				passenger.setPassenger_class(passengerClass);
				passenger.setName(passengerName);
				passenger.setSex(passengerGender);
				passenger.setAge(passengerAge);
				passenger.setSib_sp(passengerSib_sp);
				passenger.setParch(passengerParch);
				passenger.setTicket(passengerTicket);
				passenger.setFare(passengerFare);
				passenger.setCabin(passengerCabin);
				passenger.setEmbarked(passengerEmbarked);
				passenger.setAge(passengerAge);
				fileService.savePassenger(passenger);
				if (filterValue != null && filterValue.length() > 0 && filterDataField != null
						&& filterDataField.length() > 0 && filterCondition != null && filterCondition.length() > 0) {
					List<Passenger> passengerList = fileService.getPassengerCountByGenderAndFilter(genderValue, 1,
							filterValue, filterCondition, filterDataField);
					if (passengerList != null && passengerList.size() > 0) {
						map.put("data", passengerList);
					}
				} else {

					List<Passenger> passengerList = fileService.getPassengerListByGender(genderValue);
					if (passengerList != null && passengerList.size() > 0) {
						map.put("data", passengerList);
					}
				}
				map.put("status", "200");
				map.put("newId", passengerId);
				map.put("message", "Your record has been created successfully");

			}
		} catch (SQLException sqlExcecption) {
			logger.error("Inside addPassenger Method in Controller SQLException", sqlExcecption);
			map.put("status", "500");
			map.put("message", "Your record has not been saved.Please try again");
		} catch (Exception exception) {
			logger.error("Inside addPassenger Method in Controller Exception", exception);
			map.put("status", "500");
			map.put("message", "Your record has not been saved.Please try again");
		}
		return map;
	}

	/*
	 * a method to map /deletePassenger request that delete Passenger record from
	 * the database
	 */
	@RequestMapping(value = "/deletePassenger", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deletePassenger(@RequestParam("row") int passengerId,
			@RequestParam("searchByGender") String genderValue, @RequestParam("filterValue") String filterValue,
			@RequestParam("filterDataField") String filterDataField,
			@RequestParam("filterCondition") String filterCondition) {
		System.out.println("Row Value" + passengerId);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			logger.debug("Inside deletePassenger Method in Controller");
			if (passengerId != 0 && genderValue != null && genderValue.length() > 0) {
				fileService.deletePassenger(passengerId);
				if (filterValue != null && filterValue.length() > 0 && filterDataField != null
						&& filterDataField.length() > 0 && filterCondition != null && filterCondition.length() > 0) {
					List<Passenger> passengerList = fileService.getPassengerCountByGenderAndFilter(genderValue, 1,
							filterValue, filterCondition, filterDataField);
					if (passengerList != null && passengerList.size() > 0) {
						map.put("data", passengerList);
					}
				} else {

					List<Passenger> passengerList = fileService.getPassengerListByGender(genderValue);
					if (passengerList != null && passengerList.size() > 0) {
						map.put("data", passengerList);
					}

				}
				map.put("status", "200");
				map.put("message", "Your record has been deleted successfully");
			} else {
				map.put("status", "500");
				map.put("message", "Your record has not been deleted.Please try again.");
			}
		} catch (SQLException sqlExcecption) {
			logger.error("Inside deletePassenger Method in Controller with SQLException", sqlExcecption);
			map.put("status", "500");
			map.put("message", "Your record has not been deleted.Please try again");
		} catch (Exception exception) {
			logger.error("Inside deletePassenger Method in Controller with Exception", exception);
			map.put("status", "500");
			map.put("message", "Your record has not been deleted.Please try again");
		}
		return map;
	}

	/*
	 * a method to map /updatePassenger request that update Passenger record based
	 * on passengerId
	 */
	@RequestMapping(value = "/updatePassenger", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> updatePassenger(@RequestParam("update") String update,
			@RequestParam("passenger_id") int passengerId, @RequestParam("name") String passengerName,
			@RequestParam("age") String passengerAge) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(
				"update" + update + " Name " + passengerName + " Age" + passengerAge + "passenger id" + passengerId);
		try {
			logger.debug("Inside updatePassenger Method in Controller");
			if (update.equals("true") && passengerId != 0 && passengerName != null && passengerName.length() > 0
					&& passengerAge != null && passengerAge.length() > 0) {
				fileService.updatePassenger(passengerName, passengerAge, passengerId);
				map.put("status", "200");
				map.put("message", "Your record has been updated successfully");
			} else {
				map.put("status", "500");
				map.put("message", "Your record has not been updated.Please enter valid values");

			}
		} catch (SQLException sqlExcecption) {
			logger.error("Inside updatePassenger Method in Controller with SQLException", sqlExcecption);
			map.put("status", "500");
			map.put("message", "Your record has not been updated.Please try again");
		} catch (Exception exception) {
			logger.error("Inside updatePassenger Method in Controller with Exception", exception);
			map.put("status", "500");
			map.put("message", "Your record has not been updated.Please try again");
		}
		return map;

	}

	/*
	 * A method to get list of Passengers when any filter is applied.
	 */
	@RequestMapping(value = "/getFilteredPassengers", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getFilteredPassengers(@RequestParam("searchByGender") String genderValue,
			@RequestParam("filterValue") String filterValue, @RequestParam("filterDataField") String filterDataField,
			@RequestParam("filterCondition") String filterCondition) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Passenger> passengerList = fileService.getPassengerCountByGenderAndFilter(genderValue, 1, filterValue,
					filterCondition, filterDataField);
			if (passengerList != null && passengerList.size() > 0) {
				map.put("status", "200");
				map.put("message", "Data Found");
				map.put("data", passengerList);
			} else {
				map.put("status", "404");
				map.put("message", "Data Not found");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;

	}

}
