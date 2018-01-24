package com.qa.apartment.util;

import org.apache.log4j.Logger;

public class OwensDateValidator {

	private static final Logger LOGGER = Logger.getLogger(OwensDateValidator.class);
	
	public Boolean checkLogic(String[] dates) {

		LOGGER.info("Year: " + Integer.parseInt(dates[0]) + ". Month: " + Integer.parseInt(dates[1]) + ". Day: "
				+ Integer.parseInt(dates[2]));

		if (Integer.parseInt(dates[0]) < 2015) {
			LOGGER.info("Year is less than 2015");
			return false;
		}
		
		if (Integer.parseInt(dates[1]) > 12 || Integer.parseInt(dates[1]) < 1) {
			LOGGER.info("Month is greater than 12 or less than 1");
			return false;
		}
		
		if (Integer.parseInt(dates[2]) < 1) {
			LOGGER.info("Day is less than 1");
			return false;
		}

		if (Integer.parseInt(dates[1]) == 1 || Integer.parseInt(dates[1]) == 3 || Integer.parseInt(dates[1]) == 5
				|| Integer.parseInt(dates[1]) == 7 || Integer.parseInt(dates[1]) == 8
				|| Integer.parseInt(dates[1]) == 10 || Integer.parseInt(dates[1]) == 12) {
			LOGGER.info("Month is 1/3/5/7/8/10/12");
			if (Integer.parseInt(dates[2]) > 31) {
				LOGGER.info("Days is greater than 31 on a month with 31 days");
				return false;
			}
		} else if (Integer.parseInt(dates[2]) > 30) {
			LOGGER.info("Days is greater than 30 on a month with 30 days");
			return false;
		}

		if (Integer.parseInt(dates[1]) == 2) {
			LOGGER.info("Month is 2, February");
			if ((Integer.parseInt(dates[0]) % 4) != 0 && ((Integer.parseInt(dates[0]) % 200) != 0)) {
				LOGGER.info("Year % 4 != 0  and Year % 200 != 0");
				if (Integer.parseInt(dates[2]) > 28) {
					LOGGER.info("Days is greater than 28 on a month with 28 days");
					return false;
				}
			} else if (Integer.parseInt(dates[2]) > 29) {
				LOGGER.info("Days is greater than 29 on a month with 29 days");
				return false;
			}
		}

		return true;

	}
}
