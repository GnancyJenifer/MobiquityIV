package com.mobiquity.model;

import java.math.BigDecimal;

/**
 * The Class Item.
 */
public class Item {
	private final int index;
	private final BigDecimal weight;
	private final BigDecimal price;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param index the index
	 * @param weight the weight
	 * @param price the price
	 */
	public Item(int index, BigDecimal weight, BigDecimal price) {
		super();
		this.index = index;
		this.weight = weight;
		this.price = price;
	}
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * Gets the weight.
	 *
	 * @return the weight
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
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
		Item other = (Item) obj;
		if (index != other.index)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Item [index=" + index + ", weight=" + weight + ", price=" + price + "]";
	}
}
