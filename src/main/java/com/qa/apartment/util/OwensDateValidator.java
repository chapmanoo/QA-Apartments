package com.qa.apartment.util;

import org.apache.log4j.Logger;

public class OwensDateValidator {

	private static final Logger LOGGER = Logger.getLogger(OwensDateValidator.class);
	
	public Boolean checkLogic(String[] dates) {
		
		Integer year = Integer.parseInt(dates[0]);
		Integer month = Integer.parseInt(dates[1]);
		Integer day = Integer.parseInt(dates[2]);

		LOGGER.info("Year: " + year + ". Month: " + month + ". Day: "
				+ day);

		if (year < 2015) {
			LOGGER.info("Year is less than 2015");
			return false;
		}
		
		if (month > 12 || month < 1) {
			LOGGER.info("Month is greater than 12 or less than 1");
			return false;
		}
		
		if (day < 1) {
			LOGGER.info("Day is less than 1");
			return false;
		}

		if (month == 1 || month == 3 || month == 5
				|| month == 7 || month == 8
				|| month == 10 || month == 12) {
			LOGGER.info("Month is 1/3/5/7/8/10/12");
			if (day > 31) {
				LOGGER.info("Days is greater than 31 on a month with 31 days");
				return false;
			}
		} else if (day > 30) {
			LOGGER.info("Days is greater than 30 on a month with 30 days");
			return false;
		}

		if (month == 2) {
			LOGGER.info("Month is 2, February");
			if ((year % 4) != 0 && (year % 200) != 0) {
				LOGGER.info("Year % 4 != 0  and Year % 200 != 0");
				if (day > 28) {
					LOGGER.info("Days is greater than 28 on a month with 28 days");
					return false;
				}
			} else if (day > 29) {
				LOGGER.info("Days is greater than 29 on a month with 29 days");
				return false;
			}
		}

		return true;

	}
}
