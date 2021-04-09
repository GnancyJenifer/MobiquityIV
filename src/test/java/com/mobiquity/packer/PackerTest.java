package com.mobiquity.packer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.mobiquity.exception.APIException;
import com.mobiquity.model.Item;
import com.mobiquity.model.Line;
import com.mobiquity.model.Package;

public class PackerTest {
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	static List<Line> lines;
	List<Package> packs;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		lines=new ArrayList<>();
		List<Item> items=new ArrayList<Item>();
		items.add(new Item(1,53.38,45));
		items.add(new Item(2,88.62,98));
		items.add(new Item(3,78.48,3));
		items.add(new Item(4,72.30,76));
		items.add(new Item(5,30.18,9));
		items.add(new Item(6,46.34,48));
		List<Item> items2=new ArrayList<Item>();
		items2.add(new Item(1,15.3,34));
		List<Item> items3=new ArrayList<Item>();
		items3.add(new Item(1,90.72,13));
		items3.add(new Item(2,33.80,40));
		items3.add(new Item(3,43.15,10));
		items3.add(new Item(4,37.97,16));
		items3.add(new Item(5,46.81,36));
		items3.add(new Item(6,48.77,79));
		items3.add(new Item(7,81.80,45));
		items3.add(new Item(8,19.36,79));
		items3.add(new Item(9,6.76,64));
		lines.add(new Line(81, items));
		lines.add(new Line(8, items2));
		lines.add(new Line(56, items3));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Validate the read file method.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void testReadFile() throws APIException {
		String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input6";
		assertEquals(lines,Packer.readFile(filePath));		
	}
	
	/**
	 * Pack items test.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void packItemsTest() throws APIException {
		String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input";
		String output=Packer.pack(filePath);
		String expected="4\n"
				+ "-\n"
				+ "2,7\n"
				+ "8,9";		
		assertEquals(output,expected);		
	}

	/**
	 * When no of items for package exceeds maximum limit.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void whenNoOfItemsForPackageExceedsMax() throws APIException {
	    exceptionRule.expect(APIException.class);
	    exceptionRule.expectMessage("Maximum limit for items to choose from should be less than or equal to 15");
	    String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input3";
		Packer.pack(filePath);	
	}
	
	/**
	 * When weight for package exceeds the maximum limit.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void whenWeightForPackageExceedsMax() throws APIException {
	    exceptionRule.expect(APIException.class);
	    exceptionRule.expectMessage("Maximum weight for a package should be less than or equal to 100");
	    String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input2";
		Packer.pack(filePath);	
	}
	
	
	/**
	 * When weight for item exceeds maximum limit.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void whenWeightForItemExceedsMaxLimit() throws APIException {
	    exceptionRule.expect(APIException.class);
	    exceptionRule.expectMessage("Maximum weight for an item should be less than or equal to 100");
	    String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input5";
		Packer.pack(filePath);	
	}
	
	
	/**
	 * When price for item exceeds max.
	 *
	 * @throws APIException the API exception
	 */
	@Test
	public void whenPriceForItemExceedsMax() throws APIException {
	    exceptionRule.expect(APIException.class);
	    exceptionRule.expectMessage("Maximum price for an item should be less than or equal to 100");
	    String filePath="D:/Backend code assignment - Mobiquity 2021/src/main/test/resources/example_input4";
		Packer.pack(filePath);	
	}
	
	/**
	 * Gets the index of items from package list test.
	 *
	 * @return the index of items from package list test
	 */
	@Test
	public void getIndexOfItemsFromPackageListTest() {
		Item item1=new Item(2,14.34,123);
		Item item2=new Item(3,14.34,123);
		List<Item> items=new ArrayList<>();
		items.add(item1);
		items.add(item2);
		Item item3=new Item(4,14.34,123);
		Item item4=new Item(5,14.34,123);
		List<Item> items2=new ArrayList<>();
		items2.add(item3);
		items2.add(item4);
		List<Item> items3=new ArrayList<>();
		Package pack=new Package(0, items);
		Package pack1=new Package(0, items2);
		Package pack2=new Package(0, items3);
		List<Package> packs=new ArrayList<>();
		packs.add(pack);
		packs.add(pack1);
		packs.add(pack2);
		String expectedOutput="2,3\n4,5\n-";
		assertEquals(expectedOutput, Package.getIndexOfItemsFromPackageList(packs, "\n"));;
	}
	
	/**
	 * Gets the index of items test.
	 *
	 * @return the index of items test
	 */
	@Test
	public void getIndexOfItemsTest() {
		Item item1=new Item(2,14.34,123);
		Item item2=new Item(3,14.34,123);
		List<Item> items=new ArrayList<>();
		items.add(item1);
		items.add(item2);
		Package pack=new Package(0, items);
		String expectedOutput="2,3";
		assertEquals(expectedOutput, pack.getIndexOfItems(","));
	}
	
	/**
	 * Test optimal package.
	 */
	@Test
	public void testOptimalPackage() {
		Package pack=Packer.findOptimalPackage(PackerTest.lines.get(0));
		List<Item> items =new ArrayList<Item>();
		items.add(new Item(4,72.30,76));
		Package expectedPackage=new Package(76d,items);
		assertEquals(expectedPackage, pack);
	}	
	
	

}
