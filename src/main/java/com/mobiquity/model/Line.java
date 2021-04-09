package com.mobiquity.model;

import java.util.List;

/**
 * The Class Line.
 */
public class Line {
	private final double maxWeight;
	private final List<Item> items;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maxWeight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (Double.doubleToLongBits(maxWeight) != Double.doubleToLongBits(other.maxWeight))
			return false;
		return true;
	}

	/**
	 * Instantiates a new line.
	 *
	 * @param maxWeight the max weight
	 * @param items the items
	 */
	public Line(double maxWeight, List<Item> items) {
		super();
		this.maxWeight = maxWeight;
		this.items = items;
	}
	
	/**
	 * Gets the max weight.
	 *
	 * @return the max weight
	 */
	public double getMaxWeight() {
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
