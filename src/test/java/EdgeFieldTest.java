import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class EdgeFieldTest {
    EdgeField testField1;
    EdgeField testField2;
    EdgeField testField3;
    EdgeField testField4;

    @Before
    public void createFieldTestObj() throws Exception{

        testField1 = new EdgeField("4|Field a");
        testField2 = new EdgeField("10|Field b");
        testField3 = new EdgeField(" | ");
        testField4 = new EdgeField("string|Field d");

    } //end of createFieldTestObj

    //Working Cases of getNumFigures
    @Test
    public void testGetNumFigure_shouldReturnWorkingValues(){
        int expectedNumFig1 = 4;

        assertEquals(
                "Should return first value of inputString. Should return 4",
                Integer.toString(expectedNumFig1),
                Integer.toString(testField1.getNumFigure())
        );

        int expectedNumFig2 = 10;

        assertEquals(
                "Should return first value of inputString. Should return 10",
                Integer.toString(expectedNumFig2),
                Integer.toString(testField2.getNumFigure())
        );

    }// end testGetNumFigure_working

    //Fail Cases for getNumFigures
    @Test
    public void testGetNumFigure_shouldReturnFailingCases_ShouldCrash(){

        assertEquals(
                "Should crash. First value of InputValue should not be empty.",
                null,
                Integer.toString(testField3.getNumFigure())
        );


        assertEquals(
                "Should crash. First value of InputValue should not be string.",
                null,
                Integer.toString(testField4.getNumFigure())
        );

    }// end testGetNumFigure_failCases

    @Test
    public void testGetName_shouldReturnNamesOfWorkingCases_smile() {
        String expectedName1 = "Field a"; //random value to test empty string/null

        assertEquals(
                "Should return second token of input string. Expected value: 'Field a'",
                expectedName1,
                testField1.getName()
        );

        String expectedName2 = "Field b"; //random value to test empty string/null

        assertEquals(
                "Should return second token of input string. Expected value: 'Field b'",
                expectedName2,
                testField2.getName()
        );
        String expectedName4 = "Field d"; //random value to test empty string/null

        assertEquals(
                "Should return second token of input string. Expected value: 'Field d'",
                expectedName4,
                testField4.getName()
        );
    } //end of testGetName_working

    @Test
    public void testGetName_shouldReturnNamesOfFailingCases_notSmile() {

        assertEquals(
                "Should crash. InputField should not be empty/null",
                " ",
                testField3.getName()
        );

    } //end of testGetName_notWorking

    @Test
    public void setTableIDTest_workingCases_shouldSetTableIDToInputValue(){
        testField1.setTableID(4);

        assertEquals(
                "Should return set value for TableID. Expected value: 4",
                4,
                testField1.getTableID()

        );

    } //end of setTableIDTest_working

    @Test
    public void setTableIDTest_failingCases_shouldFailOrCrash(){
        testField2.setTableID(-4);

        assertEquals(
                "Should fail due to negative value. Expected value: -4",
                -4,
                testField2.getTableID()

        );

    } //end of setTableIDTest_failing

    @Test
    public void setVarCharValue_workingCases_ifStringNotEmptySetToValue(){
        testField1.setVarcharValue(6);

        assertEquals(
            "Value is greater than 0. Should return 6.",
                6,
                testField1.getVarcharValue()

        );
    } // end of setVarCharValue_working

    @Test //8
    public void setVarCharValue_failingCases_ifStringEmptyCrash(){
        testField2.setVarcharValue(-2);

        assertEquals(
                "Value is less than 0. Should return error and default value (1).",
                1,
                testField2.getVarcharValue()

        );
    } //end of SetVarCharValue_failing

    @Test
    public void setDataType_workingCase_shouldReturnIntValue(){
        testField1.setDataType(3);

        assertEquals(
                "Value passes if statement restraints. Should return 3.",
                3,
                testField1.getDataType()
        );
    }

    @Test //10
    public void setDataType_failingCase_shouldNotReturnIntValue(){
        testField3.setDataType(-2);

        assertEquals(
                "Value does NOT pass if statement restraints. Should return default value (0).",
                0,
                testField1.getDataType()
        );

        testField2.setDataType(4);

        assertEquals(
                "Value does NOT pass if statement restraints. Should return default value (0).",
                0,
                testField1.getDataType()
        );
    }

} //End of EdgeFieldTest
