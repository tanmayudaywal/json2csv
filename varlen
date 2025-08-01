/*
 * =============================================================================
 *  JSON_2_CSV Utility - Java Class
 * =============================================================================
 *
 *  Description:
 *  This utility recursively parses and flattens JSON (including nested arrays)
 *  and writes output to a CSV-style file. It supports variable-length arguments
 *  to add dynamic metadata at runtime without changing code.
 *
 *  Output Format:
 *      <component> : <property> : <key-path> : <value> : <extra1> : <extra2> ...
 *
 *  Features:
 *  -  Supports nested JSON
 *  -  Handles arrays and deep structures
 *  -  Accepts dynamic extras (varargs)
 *  -  Recursive traversal with key-prefix formatting
 *
 *  Example Usage:
 *  -----------------------------------------------------------------------------
 *      JSON_2_CSV.convert("MyComponent", "MyProperty", "", json, "output.csv",
 *                          "env=prod", "user=admin", "timestamp=2025-08-01");
 *
 *  Sample Output Line:
 *      MyComponent : MyProperty : user-details-name : "Alice" : env=prod : user=admin
 *
 *  Dependencies:
 *      - Jackson Databind
 *
 *  License:
 *      MIT License
 *
 *  Author:
 *      Tanmay Udaywal
 * =============================================================================
 */

package routines;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

public class JSON_2_CSV {

    // Greeting method (for Talend testing or placeholder)
    public static void helloExample(String message) {
        if (message == null) {
            message = "TANMAY";
        }
        System.out.println("Hello " + message + " !");
    }

    // Traverse methods (for debugging key paths, not used in output)
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
        sb.append(keyName + ".");
        System.out.println(sb.toString());
    }

    /**
     * Core method: traverses a JSON node and writes CSV-like data to a file.
     *
     * @param componentName   First fixed field
     * @param property        Second fixed field
     * @param prefix          Nested key path prefix
     * @param node            The JsonNode to process
     * @param path            Output file path (e.g., "output.csv")
     * @param extras          Optional extra metadata (varargs)
     */
    public static void convert(String componentName, String property, String prefix,
                               JsonNode node, String path, String... extras) {
        if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            Iterator<JsonNode> node1 = arrayNode.elements();
            int index = 1;
            while (node1.hasNext()) {
                convert(componentName, property,
                        !prefix.isEmpty() ? prefix + "-" + index : String.valueOf(index),
                        node1.next(), path, extras);
                index += 1;
            }
        } else if (node.isObject()) {
            node.fields().forEachRemaining(entry -> {
                convert(componentName, property,
                        !prefix.isEmpty() ? prefix + "-" + entry.getKey() : entry.getKey(),
                        entry.getValue(), path, extras);
            });
        } else {
            try {
                StringBuilder line = new StringBuilder(componentName + " : " + property + " : " + prefix + " : " + node.toString());
                for (String extra : extras) {
                    line.append(" : ").append(extra);
                }
                line.append("\r\n");
                Files.write(Paths.get(path), line.toString().getBytes(),
                            StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            } catch (IOException e) {
                System.out.println("Exception: " + e);
                e.printStackTrace();
            }
        }
    }

    // Test main method
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Sample Input File (replace with your file)
        File inputFile = new File("input.json");
        if (!inputFile.exists()) {
            System.err.println("ERROR: input.json not found.");
            return;
        }

        JsonNode json = mapper.readTree(inputFile);
        String outputPath = "output.csv";

        // Clear the file first
        Files.write(Paths.get(outputPath), new byte[0],
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // Run the conversion with optional metadata
        convert("MyComponent", "UserData", "", json, outputPath,
                "env=dev", "user=admin", "timestamp=2025-08-01");

        System.out.println("Conversion completed. Output written to " + outputPath);
    }
}
