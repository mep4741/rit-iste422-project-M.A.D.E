import java.awt.*;
import java.awt.event.*;
import javax.swing.*;   
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateDDLMySQL extends EdgeConvertCreateDDL {

   //The class logger.
   private static final Logger logger = LogManager.getLogger(CreateDDLMySQL.class);

   protected String databaseName;
   //this array is for determining how MySQL refers to datatypes
   protected String[] strDataType = {"VARCHAR", "BOOL", "INT", "DOUBLE"};
   protected StringBuffer sb;


   public CreateDDLMySQL(EdgeTable[] inputTables, EdgeField[] inputFields) {
      super(inputTables, inputFields);
      sb = new StringBuffer();
   } //CreateDDLMySQL(EdgeTable[], EdgeField[])
   
   public CreateDDLMySQL() { //default constructor with empty arg list for to allow output dir to be set before there are table and field objects
      
   }
   
   /**
    * Creates the necessary file to create the requisite SQL database.
    */
   public void createDDL() {
      EdgeConvertGUI.setReadSuccess(true);
      databaseName = generateDatabaseName();
      logger.info("Beginning Database Creation...");
      sb.append("DROP DATABASE IF EXISTS "+databaseName+";\r\n");
      sb.append("CREATE DATABASE " + databaseName + ";\r\n");
      sb.append("USE " + databaseName + ";\r\n");
      for (int boundCount = 0; boundCount <= maxBound; boundCount++) { //process tables in order from least dependent (least number of bound tables) to most dependent
         for (int i = 0; i < numBoundTables.length; i++) { //step through list of tables
            if (numBoundTables[i] == boundCount) { //
               createTable(i);
            }
         }
      }
      logger.debug("--- SQL commands ---\r\n"+sb.toString());
   }

   protected int convertStrBooleanToInt(String input) { //MySQL uses '1' and '0' for boolean types
      if (input.equals("true")) {
         return 1;
      } else {
         return 0;
      }
   }
   
   public String generateDatabaseName() { //prompts user for database name
      String dbNameDefault = "MySQLDB";
      //String databaseName = "";

      do {
         databaseName = (String)JOptionPane.showInputDialog(
                       null,
                       "Enter the database name:",
                       "Database Name",
                       JOptionPane.PLAIN_MESSAGE,
                       null,
                       null,
                       dbNameDefault);
         if (databaseName == null) {
            EdgeConvertGUI.setReadSuccess(false);
            return "";
         }
         if (databaseName.equals("")) {
            logger.warn("You must select a name for your database.");
            JOptionPane.showMessageDialog(null, "You must select a name for your database.");
         }
      } while (databaseName.equals(""));
      return databaseName;
   }
   
   public String getDatabaseName() {
      return databaseName;
   }
   
   public String getProductName() {
      return "MySQL";
   }

   public String getSQLString() {
      createDDL();
      return sb.toString(); //output of get ddl
   }



   /**
    * Adds the sql commands to create a necessary table and add them to the string
    * builder.
    * @param tableCount
    */
   private void createTable(int tableNum){
      EdgeTable table = tables[tableNum]; //Table to be converted into SQL statements
      sb.append("CREATE TABLE " + table.getName() + " (\r\n");
               int[] nativeFields = table.getNativeFieldsArray();
               int[] relatedFields = table.getRelatedFieldsArray();

               LinkedList<EdgeField> primaryKeys = addNativeFields(nativeFields); //A list of all primary keys.

               if (primaryKeys.size() > 0) {
                  addPrimaryKeyConstraints(primaryKeys, table.getName());
               }

               addForeignKeyConstraints(nativeFields, relatedFields, databaseName);

               sb.append("\r\n);\r\n\r\n"); //end of table
   }//CreateSQLTable

   /**
    * Append sql statements to create the native fields for a given table.
    * @param nativeFields - an array of all the native field numbers
    * @return a LinkedList<EdgeField> containing all primary key fields.
    */
   private LinkedList<EdgeField> addNativeFields(int[] nativeFields){
      LinkedList<EdgeField> pKeys = new LinkedList<EdgeField>(); //A list of all the primary keys.
      for (int i = 0; i < nativeFields.length; i++) { //print out the fields
         EdgeField currentField = getField(nativeFields[i]);
         sb.append("\t" + currentField.getName() + " " + strDataType[currentField.getDataType()]);
         if (currentField.getDataType() == 0) { //varchar
            sb.append("(" + currentField.getVarcharValue() + ")"); //append varchar length in () if data type is varchar
         }
         if (currentField.getDisallowNull()) {
            sb.append(" NOT NULL");
         }
         if (!currentField.getDefaultValue().equals("")) {
            if (currentField.getDataType() == 1) { //boolean data type
               sb.append(" DEFAULT " + convertStrBooleanToInt(currentField.getDefaultValue()));
            } else { //any other data type
               sb.append(" DEFAULT " + currentField.getDefaultValue());
            }
         }
         //Store this field as a primary key.
         if (currentField.getIsPrimaryKey()) {
            pKeys.push(currentField);
         }
         //Only add commas and line separators for each field other than the last
         if (i < nativeFields.length - 1) {
            sb.append(",\r\n");
         }
         //end of field
      }//for end
      return pKeys;
   }//addNativeFields

   /**
    * Adds primary key constraints
    * @param primaryKeys
    * @param tableName
    */
   private void addPrimaryKeyConstraints(LinkedList<EdgeField> primaryKeys, String tableName){
      sb.append(",\r\n"); //Append a comma and a line break
      sb.append("CONSTRAINT " + tableName + "_PK PRIMARY KEY (");
      Iterator<EdgeField> iter = primaryKeys.iterator();
      while (iter.hasNext()) {
         EdgeField currentField = iter.next();
         sb.append(currentField.getName());
         //If there are more primary keys, add commas between them.
         if (iter.hasNext()) {
            sb.append(", ");
         }
      }//End of while loop
      sb.append(")"); //no more primary keys to add
   }//addPrimaryKeyConstraints


   /**
    * Adds foreign key constraints to a table, if there are any.
    * @param nativeFieldNums
    * @param relatedFieldNums
    * @param tableName
    */
   private void addForeignKeyConstraints(int[] nativeFieldNums, int[] relatedFieldNums, String tableName){
      
      int len = relatedFieldNums.length;
      for(int i = 0; i< len; i++){
         int relatedFieldNumber = relatedFieldNums[i];
         int fkNumber = i+1;

         EdgeField nativeField = getField(nativeFieldNums[i]);//The nativefield the foreignkey is related to.
         EdgeField relatedField = getField(relatedFieldNumber);
         if (relatedFieldNumber != 0) {
            sb.append(",\r\n"); //Create a new line
            sb.append("CONSTRAINT " + tableName + "_FK" + fkNumber);
            sb.append(" FOREIGN KEY (" + nativeField.getName() + ") REFERENCES ");
            sb.append(getTable(nativeField.getTableBound()).getName() + "(" + relatedField.getName() + ")");
         }
      }//for loop
   }//addForeignKeyConstraints
   
}//EdgeConvertCreateDDL
