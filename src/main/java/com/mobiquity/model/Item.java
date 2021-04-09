package com.mobiquity.model;

/**
 * The Class Item.
 */
public class Item {
	private final int index;
	private final double weight;
	private final double price;
	
	/**
	 * Instantiates a new item.
	 *
	 * @param index the index
	 * @param weight the weight
	 * @param price the price
	 */
	public Item(int index, double weight, double price) {
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
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weight);
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
		Item other = (Item) obj;
		if (index != other.index)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
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
