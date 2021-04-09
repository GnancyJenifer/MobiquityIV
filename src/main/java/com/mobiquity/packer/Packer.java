package com.mobiquity.packer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.model.Package;
import com.mobiquity.validator.Validator;

/**
 * The Class Packer.
 */
public class Packer {

	private Packer() {
	}

	/**
	 * Pack.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws APIException the API exception
	 */
	public static String pack(String filePath) throws APIException {
		List<Package> selectedPackages = null;
		List<Line> lines = null;
		String output = "";
		lines = readFile(filePath);
		selectedPackages = lines.stream().map(line -> findOptimalPackage(line)).collect(Collectors.toList());
		output = Package.getIndexOfItemsFromPackageList(selectedPackages, "\n");
		return output;

	}

	/**
	 * Read the file.
	 *
	 * @param filePath the file path
	 * @return the list
	 * @throws APIException the API exception
	 * @throws IOException  Signals that an I/O exception has occurred.
	 */
	public static List<Line> readFile(String filePath) throws APIException {
		List<Line> lines = new ArrayList<>();
		List<String> linesList = null;
		Path file = Paths.get(filePath);
		try (BufferedReader bufferedReader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
			linesList = bufferedReader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			throw new APIException(e.getMessage());
		}
		for (String s : linesList) {
			Line line = parseLine(s);
			lines.add(line);
		}
		return lines;
	}

	/**
	 * Find the optimal package - the items with the maximum total price that can
	 * fit into the package.
	 *
	 * @param line the line
	 * @return the package
	 */
	public static Package findOptimalPackage(Line line) {
		List<Package> packages = findPossiblePackages(line.getItems(), line.getMaxWeight());
		Package packageWithMaxPrice = packages.stream().max(Comparator.comparing(Package::getMaxPrice))
				.orElseThrow(NoSuchElementException::new);
		return packageWithMaxPrice;
	}

	/**
	 * Parses the line and map it to the Line object with maximum weight and list of
	 * items.
	 *
	 * @param line the line
	 * @return the line
	 * @throws APIException the API exception
	 */
	public static Line parseLine(String line) throws APIException {
		double maxLimit = 0d;
		List<Item> items = null;
		try {
			String[] maxWeightAndItemSplit = line.split(":");
			if (maxWeightAndItemSplit != null && maxWeightAndItemSplit.length > 0) {
				maxLimit = Double.parseDouble(maxWeightAndItemSplit[0].trim());
				String itemsBeforeSplit = maxWeightAndItemSplit[1].trim();
				String[] itemsSplit = itemsBeforeSplit.split("\\)\\s*\\(");
				Validator.validatePackageWeight(maxLimit);
				Validator.validateItemCount(itemsSplit);
				items = new ArrayList<Item>();
				if (itemsSplit != null && itemsSplit.length > 0) {
					for (String item : itemsSplit) {
						String itemsAfterRemovingBraces = item.replaceAll("\\(", "").replaceAll("\\)", "")
								.replaceAll("\\u20ac", "").trim();
						String indexWgtPriceSplit[] = itemsAfterRemovingBraces.split(",");
						int index = Integer.parseInt(indexWgtPriceSplit[0]);
						double weight = Double.parseDouble(indexWgtPriceSplit[1]);
						double price = Double.parseDouble(indexWgtPriceSplit[2]);
						Validator.validateItemWeightAndPrice(weight, price);
						items.add(new Item(index, weight, price));
					}
				}
			}
		} catch (APIException e) {
			throw e;
		}
		return new Line(maxLimit, items);
	}

	/**
	 * Find all the possibilities of items that can fit into a package by comparing
	 * with the maximum weight limit that a package can take.
	 * 
	 * To minimize the number of iteration for find out all the possibilities,
	 * sorting the items based on the price and weight before the iteration
	 * 
	 *
	 * @param items the items
	 * @param limit the limit
	 * @return the list
	 */
	public static List<Package> findPossiblePackages(List<Item> items, double limit) {
		Comparator<Item> compareByPriceAndWeight = Comparator.comparing(Item::getPrice).reversed()
				.thenComparing(Item::getWeight);
		List<Item> sortedItems = items.stream().sorted(compareByPriceAndWeight).collect(Collectors.toList());
		List<Package> packages = new ArrayList<>();
		for (int i = 0; i <= sortedItems.size() - 1; i++) {
			double maxWeight = sortedItems.get(i).getWeight();
			double maxPrice = sortedItems.get(i).getPrice();
			List<Item> itemList = new ArrayList<>();
			if (maxWeight <= limit) {
				itemList.add(sortedItems.get(i));
				for (int j = i + 1; j <= sortedItems.size() - 1; j++) {
					double wt = maxWeight + sortedItems.get(j).getWeight();
					if (wt <= limit) {
						maxWeight += sortedItems.get(j).getWeight();
						maxPrice += sortedItems.get(j).getPrice();
						itemList.add(sortedItems.get(j));
					}
				}
				packages.add(new Package(maxPrice, itemList));
			} else {
				packages.add(new Package(0d, itemList));
			}
		}
		return packages;
	}

}
