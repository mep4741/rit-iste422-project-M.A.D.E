import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunEdgeConvert {

   // logger
   private static final Logger logger = LoggerFactory.getLogger(RunEdgeConvert.class);

   public static void main(String[] args) {
      logger.info("App Started");
      EdgeConvertGUI edge = new EdgeConvertGUI();
   } // main
} // RunEdgeConvert