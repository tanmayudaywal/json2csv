# JSON_2_CSV

`JSON_2_CSV` is a Java utility class that recursively parses and flattens JSON data (including nested structures and arrays) and writes it to a CSV-style output file. The method supports dynamic **variable-length arguments** to add flexible metadata without modifying the code.

---

##  Features

- Supports **nested JSON objects**
- Handles **JSON arrays and mixed depth**
- Allows **variable-length arguments** (`String... extras`)
- Recursively flattens keys using customizable prefixes
- Appends data to file in structured format
- Suitable for **ETL**, **log parsing**, **Talend routines**, etc.

---

##  Usage

## Method Signature

```java
public static void convert(
    String componentName,
    String property,
    String prefix,
    JsonNode node,
    String path,
    String... extras
)
