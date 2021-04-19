package com.mobiquity.packer;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
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
			System.out.println(e);
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
		BigDecimal maxLimit =null;
		List<Item> items = null;
		try {
			String[] maxWeightAndItemSplit = line.split(":");
			if (maxWeightAndItemSplit != null && maxWeightAndItemSplit.length > 0) {
				maxLimit = new BigDecimal(maxWeightAndItemSplit[0].trim());
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
						BigDecimal weight = new BigDecimal(indexWgtPriceSplit[1]);
						BigDecimal price = new BigDecimal(indexWgtPriceSplit[2]);
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
	public static List<Package> findPossiblePackages(List<Item> items, BigDecimal limit) {
		Comparator<Item> compareByPriceAndWeight = Comparator.comparing(Item::getPrice).reversed()
				.thenComparing(Item::getWeight);
		List<Item> sortedItems = items.stream().sorted(compareByPriceAndWeight).collect(Collectors.toList());
		List<Package> packages = new ArrayList<>();
		for (int i = 0; i <= sortedItems.size() - 1; i++) {
			BigDecimal maxWeight = sortedItems.get(i).getWeight();
			BigDecimal maxPrice = sortedItems.get(i).getPrice();
			List<Item> itemList = new ArrayList<>();
			if (maxWeight.compareTo(limit)== -1 || maxWeight.compareTo(limit)==0) {
				itemList.add(sortedItems.get(i));
				for (int j = i + 1; j <= sortedItems.size() - 1; j++) {
					BigDecimal wt = maxWeight.add(sortedItems.get(j).getWeight());
					if (wt.compareTo(limit)==-1 || wt.compareTo(limit) == 0) {
						maxWeight = maxWeight.add(sortedItems.get(j).getWeight());
						maxPrice =maxPrice.add( sortedItems.get(j).getPrice());
						itemList.add(sortedItems.get(j));
					}
				}
				packages.add(new Package(maxPrice, itemList));
			} else {
				packages.add(new Package(new BigDecimal(0), itemList));
			}
		}
		return packages;
	}

}
