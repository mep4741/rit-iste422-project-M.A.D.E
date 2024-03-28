import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class EdgeTableTest {
	EdgeTable testObj;

	@Before
	public void setUp() throws Exception {
		testObj = new EdgeTable("1|Table a");
	} // setUp

	// test AddRelatedTable
	@Test
	public void testAddRelatedTable_CanAddTablesThatDoNotExist_CanAddMultipleTables() {
		testObj.addRelatedTable(2);
		testObj.makeArrays();
		int[] expectedArray = {2};
		assertEquals(
				"Table that does not exist should be able to be added. Table with id 2 should be in the array",
				Arrays.toString(expectedArray),
				Arrays.toString(testObj.getRelatedTablesArray())
		);

		testObj.addRelatedTable(3);
		testObj.makeArrays();
		int[] expectedArray2 = {2, 3};
		assertEquals(
				"Table with id 3 should have been added to array",
				Arrays.toString(expectedArray2),
				Arrays.toString(testObj.getRelatedTablesArray())
		);
	} // TestAddRelatedTable

	// test SetRelatedField
	@Test
	public void testSetRelatedField_CanAddRelatedFieldDoesNotExist_CanAddMultipleFields() {
		// add values so that it can have a relating fields
		testObj.addNativeField(1);
		testObj.addNativeField(2);
		testObj.addNativeField(3);

		testObj.makeArrays();
		testObj.setRelatedField(0, 22);
		int[] expectedArray = {22, 0, 0};
		assertEquals(
				"Field that does not exist should be able to be added. " +
						"Field with index 2 and id 22 should be in the array",
				Arrays.toString(expectedArray),
				Arrays.toString(testObj.getRelatedFieldsArray())
		);

		testObj.setRelatedField(2, 3);
		int[] expectedArray2 = {22, 0, 3};
		assertEquals(
				"Field with index 2 and id 3 should have been added to array",
				Arrays.toString(expectedArray2),
				Arrays.toString(testObj.getRelatedFieldsArray())
		);
	} // testSetRelatedField

	// test MoveFieldUp
	@Test
	public void testMoveFieldUp() {
		testObj.addNativeField(1);
		testObj.addNativeField(2);
		testObj.addNativeField(3);
		testObj.makeArrays();

		testObj.moveFieldUp(2);
		int[] expectedArray = {1, 3, 2};
		assertEquals(
				"Should have changed [1,2,3] to [1,3,2] by moving the last element forward by one",
				Arrays.toString(expectedArray),
				Arrays.toString(testObj.getNativeFieldsArray())
		);
	} // testMoveFieldUp

	// test MoveFieldUp
	@Test
	public void testMoveFieldDown() {
		testObj.addNativeField(1);
		testObj.addNativeField(2);
		testObj.addNativeField(3);
		testObj.makeArrays();

		testObj.moveFieldDown(1);
		int[] expectedArray = {1, 3, 2};
		assertEquals(
				"Should have changed [1,2,3] to [1,3,2] by moving the second element down by one",
				Arrays.toString(expectedArray),
				Arrays.toString(testObj.getNativeFieldsArray())
		);
	} // testMoveFieldDown


	// Test MakeArrays
	@Test
	public void testMakeArrays() {
		testObj.makeArrays();

		int[] emptyArray = new int[0];
		assertEquals(
				"Should have initialized RelatedTablesArray to []",
				Arrays.toString(emptyArray),
				Arrays.toString(testObj.getRelatedTablesArray())
		);
		assertEquals(
				"Should have initialized RelatedFieldsArray to []",
				Arrays.toString(emptyArray),
				Arrays.toString(testObj.getRelatedFieldsArray())
		);
		assertEquals(
				"Should have initialized NativeFieldsArray to []",
				Arrays.toString(emptyArray),
				Arrays.toString(testObj.getNativeFieldsArray())
		);

		testObj.addNativeField(2);
		testObj.addNativeField(4);
		testObj.addNativeField(6);
		testObj.addNativeField(8);
		testObj.makeArrays();
		int[] expectedArray = {2, 4, 6, 8};
		assertEquals(
				"Should have added the 4 new values to array to make it [2, 4, 6, 8]",
				Arrays.toString(expectedArray),
				Arrays.toString(testObj.getNativeFieldsArray())
		);

	} // testMakeArrays()

} // EdgeConnectorTest
