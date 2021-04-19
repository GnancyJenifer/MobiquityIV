package com.mobiquity.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * The Class Line.
 */
public class Line {
	private final BigDecimal maxWeight;
	private final List<Item> items;
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((maxWeight == null) ? 0 : maxWeight.hashCode());
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
		Line other = (Line) obj;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (maxWeight == null) {
			if (other.maxWeight != null)
				return false;
		} else if (!maxWeight.equals(other.maxWeight))
			return false;
		return true;
	}

	/**
	 * Instantiates a new line.
	 *
	 * @param maxWeight the max weight
	 * @param items the items
	 */
	public Line(BigDecimal maxWeight, List<Item> items) {
		super();
		this.maxWeight = maxWeight;
		this.items = items;
	}
	
	/**
	 * Gets the max weight.
	 *
	 * @return the max weight
	 */
	public BigDecimal getMaxWeight() {
		return maxWeight;
	}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}
	
}
