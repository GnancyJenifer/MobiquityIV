package com.mobiquity.validator;

import java.math.BigDecimal;

import com.mobiquity.exception.APIException;

/**
 * The Class Validator.
 */
public class Validator {

	/**
	 * Validate package weight.
	 *
	 * @param weight the weight
	 * @return true, if successful
	 * @throws APIException the API exception
	 */
	public static boolean validatePackageWeight(BigDecimal weight) throws APIException{
		if(weight.compareTo(new BigDecimal(100))==1) {
			throw new APIException("Maximum weight for a package should be less than or equal to 100");
		}
		else {
			return true;
		}
		
	}
	
	/**
	 * Validate item count.
	 *
	 * @param items the items
	 * @return true, if successful
	 * @throws APIException the API exception
	 */
	public static boolean validateItemCount(String[] items) throws APIException{
		if(items.length>15) {
			throw new APIException("Maximum limit for items to choose from should be less than or equal to 15");
		}
		else {
			return true;
		}
		
	}
	
	/**
	 * Validate item weight and price.
	 *
	 * @param weight the weight
	 * @param price the price
	 * @return true, if successful
	 * @throws APIException the API exception
	 */
	public static boolean validateItemWeightAndPrice(BigDecimal weight,BigDecimal price) throws APIException{
		if(weight.compareTo(new BigDecimal(100))==1) {
			throw new APIException("Maximum weight for an item should be less than or equal to 100");
		}
		if(price.compareTo(new BigDecimal(100))==1) {
			throw new APIException("Maximum price for an item should be less than or equal to 100");
		}
		else {
			return true;
		}
		
	}
}
