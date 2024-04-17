import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SaveFile extends EdgeConvertFileParser {

    private static final Logger logger = LogManager.getLogger(SaveFile.class);
    public static final String SAVE_ID = "EdgeConvert Save File"; //first line of save files should be this

    private int numFields, numTables;
    private String tableName;
    private String fieldName;

    public SaveFile(File constructorFile) {
        super(constructorFile);
        openFile(constructorFile);
    }


    public void openFile(File inputFile) {
        try {
            fr = new FileReader(inputFile);
            br = new BufferedReader(fr);
            //test for what kind of file we have
            currentLine = br.readLine().trim();
            numLine++;

            if (!currentLine.startsWith(SAVE_ID)) {
                logger.warn("File: \'"+inputFile.getName()+"\' is not a recognized file format.");
                JOptionPane.showMessageDialog(null, "Unrecognized file format");
                return;
            }
            this.parseSaveFile(); //parse the file
            br.close();
            this.makeArrays(); //convert ArrayList objects into arrays of the appropriate Class type

        } // try
        catch (FileNotFoundException fnfe) {
            logger.error("Cannot find \"" + inputFile.getName() + "\".");
            logger.debug("stacktrace:\n"+fnfe.getStackTrace());
            System.exit(0);
        } // catch FileNotFoundException
        catch (IOException ioe) {
            logger.error("Experienced an Input/Output exception. Error:\n"+
                    ioe+"\n\nstacktrace:\n"+ioe.getStackTrace());
            System.exit(0);
        } // catch IOException
    } // openFile()

    public void parseSaveFile() throws IOException { //this method is unclear and confusing in places
        StringTokenizer stTables, stNatFields, stRelFields, stNatRelFields, stField;
        EdgeTable tempTable;
        EdgeField tempField;
        currentLine = br.readLine();
        currentLine = br.readLine(); //this should be "Table: "
        while (currentLine.startsWith("Table: ")) {
            numFigure = Integer.parseInt(currentLine.substring(currentLine.indexOf(" ") + 1)); //get the Table number
            currentLine = br.readLine(); //this should be "{"
            currentLine = br.readLine(); //this should be "TableName"
            tableName = currentLine.substring(currentLine.indexOf(" ") + 1);
            tempTable = new EdgeTable(numFigure + EdgeConvertFileParser.DELIM + tableName);

            currentLine = br.readLine(); //this should be the NativeFields list
            stNatFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), EdgeConvertFileParser.DELIM);
            numFields = stNatFields.countTokens();
            for (int i = 0; i < numFields; i++) {
                tempTable.addNativeField(Integer.parseInt(stNatFields.nextToken()));
            }

            currentLine = br.readLine(); //this should be the RelatedTables list
            stTables = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), EdgeConvertFileParser.DELIM);
            numTables = stTables.countTokens();
            for (int i = 0; i < numTables; i++) {
                tempTable.addRelatedTable(Integer.parseInt(stTables.nextToken()));
            }
            tempTable.makeArrays();

            currentLine = br.readLine(); //this should be the RelatedFields list
            stRelFields = new StringTokenizer(currentLine.substring(currentLine.indexOf(" ") + 1), EdgeConvertFileParser.DELIM);
            numFields = stRelFields.countTokens();

            for (int i = 0; i < numFields; i++) {
                tempTable.setRelatedField(i, Integer.parseInt(stRelFields.nextToken()));
            }

            alTables.add(tempTable);
            currentLine = br.readLine(); //this should be "}"
            currentLine = br.readLine(); //this should be "\n"
            currentLine = br.readLine(); //this should be either the next "Table: ", #Fields#
        }
        while ((currentLine = br.readLine()) != null) {
            stField = new StringTokenizer(currentLine, EdgeConvertFileParser.DELIM);
            numFigure = Integer.parseInt(stField.nextToken());
            fieldName = stField.nextToken();
            tempField = new EdgeField(numFigure + EdgeConvertFileParser.DELIM + fieldName);
            tempField.setTableID(Integer.parseInt(stField.nextToken()));
            tempField.setTableBound(Integer.parseInt(stField.nextToken()));
            tempField.setFieldBound(Integer.parseInt(stField.nextToken()));
            tempField.setDataType(Integer.parseInt(stField.nextToken()));
            tempField.setVarcharValue(Integer.parseInt(stField.nextToken()));
            tempField.setIsPrimaryKey(Boolean.parseBoolean(stField.nextToken()));
            tempField.setDisallowNull(Boolean.parseBoolean(stField.nextToken()));
            if (stField.hasMoreTokens()) { //Default Value may not be defined
                tempField.setDefaultValue(stField.nextToken());
            }
            alFields.add(tempField);
        }
    } // parseSaveFile()

} // SaveFile
