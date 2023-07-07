package routines;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Iterator;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.*;
/*
 * user specification: the function's comment should contain keys as follows: 1. write about the function's comment.but
 * it must be before the "{talendTypes}" key.
 * 
 * 2. {talendTypes} 's value must be talend Type, it is required . its value should be one of: String, char | Character,
 * long | Long, int | Integer, boolean | Boolean, byte | Byte, Date, double | Double, float | Float, Object, short |
 * Short
 * 
 * 3. {Category} define a category for the Function. it is required. its value is user-defined .
 * 
 * 4. {param} 's format is: {param} <type>[(<default value or closed list values>)] <name>[ : <comment>]
 * 
 * <type> 's value should be one of: string, int, list, double, object, boolean, long, char, date. <name>'s value is the
 * Function's parameter name. the {param} is optional. so if you the Function without the parameters. the {param} don't
 * added. you can have many parameters for the Function.
 * 
 * 5. {example} gives a example for the Function. it is optional.
 */
@SuppressWarnings("unused")
public class JSON_2_CSV {

    /**
     * Method Name 	1. helloExample: not return value, only print "hello" + message.
     * 		  		2. traverse: not return value, traverse JsonNode send value to traverseObject
     * 		  		3. traverseObject: not return value, get fieldName from JsonNode
     * 		 	 	4. traversable: not return value, childNode traverse
     * 				5. printNode: not return value, append keyName with traverseNode
     * 				6. process:	not return value, create CSV file into given path
     * 
     * 
     * Note:	path would be with file name 
     * Example: "C:\"+"json_2_csv.csv"
     * 
     * 
     * {talendTypes} String
     * 
     * {Category} User Defined
     * 
     * {param} string("world") input: The string need to be printed.
     * 
     * {example} helloExemple("world") # hello world !.
     */
	
	public static void helloExample(String message) {
        if (message == null) {
            message = "TANMAY"; //$NON-NLS-0$
        }
        System.out.println("Hello " + message + " !"); //$NON-NLS-1$ //$NON-NLS-2$
    }
	
	public static void traverse(JsonNode node, StringBuilder sb) {
	        if (node.getNodeType() == JsonNodeType.OBJECT) {
	            traverseObject(node, sb);
	        } else {
	            throw new RuntimeException("Not yet implemented");
	        }
	    }
	 
	private static void traverseObject(JsonNode node, StringBuilder sb1) {
	        node.fieldNames().forEachRemaining((String fieldName) -> {
	            StringBuilder sb2 = new StringBuilder(sb1);
	            JsonNode childNode = node.get(fieldName);
	            printNode(childNode, fieldName, sb2);
	            if (traversable(childNode)) {
	                traverse(childNode, sb2);
	            }
	        });
	    }
	 
	private static boolean traversable(JsonNode node) {
	        return node.getNodeType() == JsonNodeType.OBJECT || node.getNodeType() == JsonNodeType.ARRAY;
	    }
	  
	private static void printNode(JsonNode node, String keyName, StringBuilder sb) {
	        if (traversable(node)) {
	            sb.append(keyName + ".");
	            System.out.println(sb.toString());
	        } else {
	            sb.append(keyName + ".");
	            System.out.println(sb.toString());
	        }
	 }
	 
	
	public static void convert(String componentName, String property, String prefix, JsonNode node, String path) {
		//String operatingSyatem = System.getProperty("os.name").toLowerCase();
		 if (node.isArray()) {
			 ArrayNode arrayNode = (ArrayNode) node;
		     Iterator<JsonNode> node1 = arrayNode.elements();
		     int index = 1;
		     while (node1.hasNext()) {
		    	 convert(componentName, property, !prefix.isEmpty() ? prefix + "-" + index : String.valueOf(index), node1.next(), path);
		          index += 1;
		     }    
		 }
		 else if (node.isObject()) {
		    node.fields().forEachRemaining(entry -> {
		    	convert(componentName, property, !prefix.isEmpty() ? prefix + "-" + entry.getKey() : entry.getKey(), entry.getValue(), path);
			});
		 }
		 else {
			 try {
				 Files.write(Paths.get(path), (componentName+" : "+property+ " : " +prefix + " : " + node.toString()+"\r\n").getBytes(), StandardOpenOption.APPEND);
			 } 
			 catch (IOException e) {
				System.out.println("exception:" +e);
				e.printStackTrace();
			}
		 }
		
	} 
	
}
