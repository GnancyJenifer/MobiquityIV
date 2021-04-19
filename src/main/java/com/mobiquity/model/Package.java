package com.mobiquity.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Class Package.
 */
public class Package {
	private final BigDecimal maxPrice;
	private final List<Item> items;

	/**
	 * Instantiates a new package.
	 *
	 * @param maxPrice the max price
	 * @param items the items
	 */
	public Package(BigDecimal maxPrice, List<Item> items) {
		super();
		this.maxPrice = maxPrice;
		this.items = items;
	}

	/**
	 * Gets the max price.
	 *
	 * @return the max price
	 */
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Gets the index of items.
	 *
	 * @param separator the separator
	 * @return the index of items
	 */
	public String getIndexOfItems(String separator) {
		StringBuilder indexes = new StringBuilder();
		if (items != null && !items.isEmpty()) {
			for (Item i : items) {
				indexes.append(i.getIndex() + separator);
			}
			indexes.deleteCharAt(indexes.length() - 1);
		} else {
			indexes.append("-");
		}
		return indexes.toString();
	}
	
	/**
	 * Get index of items from package list.
	 *
	 * @param packages the packages
	 * @param separator the separator
	 * @return the string
	 */
	public static String getIndexOfItemsFromPackageList(List<Package> packages, String separator) {
		StringBuilder output = new StringBuilder();
		if (packages != null && !packages.isEmpty()) {
			for (Package i : packages) {
				output.append( i.getIndexOfItems(",")).append( separator);
			}
			output = output.deleteCharAt(output.length() - 1);
		} else {
			output.append("-");
		}
		return output.toString();
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Package [maxPrice=" + maxPrice + ", items=" + items + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((maxPrice == null) ? 0 : maxPrice.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (maxPrice == null) {
			if (other.maxPrice != null)
				return false;
		} else if (!maxPrice.equals(other.maxPrice))
			return false;
		return true;
	}

	

}
