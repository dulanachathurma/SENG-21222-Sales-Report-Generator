# Sales Reporter — CommandLine Product Sales Report Generator

Course: SENG 21222 - Software Construction 

Assignment: Group Assignment — CLI Sales Report Generator

This project has been fully built, compiled, and tested (all 10 JUnit 5 tests pass). No placeholders, no TODOs.
### 📂 Project Directory Structure

```text
SENG-21222-Sales-Report-Generator/
├── lib/
│   └── junit-platform-console-standalone-1.9.1.jar  # JUnit 5 runner
├── src/
│   ├── exception/
│   │   ├── InvalidArgumentException.java            # Member 3
│   │   └── InvalidCsvFormatException.java           # Member 3
│   ├── model/
│   │   ├── Product.java                             # Member 1
│   │   └── SalesReport.java                         # Member 1
│   ├── service/
│   │   ├── CsvReader.java                           # Member 1
│   │   └── SalesCalculator.java                     # Member 2
│   ├── strategy/
│   │   ├── ConsoleOutputStrategy.java               # Member 2
│   │   ├── FileOutputStrategy.java                  # Member 2
│   │   └── OutputStrategy.java                      # Strategy Interface
│   └── SalesReporter.java                           # Main CLI Class (Member 3)
├── test/
│   └── SalesCalculatorTest.java                     # JUnit 5 tests (Member 2)
├── README.md
└── sample_sales.csv                                 # Sample input file

```

2. How to Compile

From the SalesReporter/ project root:

mkdir -p out
javac -d out $(find src -name "*.java")


This compiles every class into the out/ folder, preserving the model, service, strategy and exception packages.

3. How to Run

cd out

# Output to console
java SalesReporter ../sample_sales.csv console

# Output to a file
java SalesReporter ../sample_sales.csv file ../report.txt


Command Line Syntax:
java SalesReporter <csv-file-path> <output-method> [output-file-path]
Argument	Required?	Values
csv-file-path	Yes	Path to input CSV
output-method	Yes	console or file
output-file-path	Only if output-method is file	Path to write the report

Error scenarios (all verified working):
Scenario	Exit Code	Example message
Wrong number of args	1	[Argument Error] Expected 2 or 3 arguments, but received 1.
Invalid output-method	1	[Argument Error] Invalid output-method 'screen'...
file method missing path	1	[Argument Error] Output-method 'file' requires a third argument...
CSV file not found	3	[File I/O Error] CSV file not found at path: ...
Malformed CSV row	2	[CSV Format Error] Line 2: quantity_sold is not a valid whole number...

4. How to Run the Unit Tests

The lib/ folder already contains the JUnit 5 standalone console jar, so no Maven/Gradle/internet connection is required.

# 1 Compile main source (if not already done)
mkdir -p out
javac -d out $(find src -name "*.java")

# 2 Compile the test file against the compiled classes + JUnit jar
mkdir -p out-test
javac -cp "out:lib/junit-platform-console-standalone-1.9.1.jar" -d out-test test/SalesCalculatorTest.java

# 3 Run the tests
java -jar lib/junit-platform-console-standalone-1.9.1.jar --class-path "out:out-test" --scan-class-path --details=tree


Expected result: 10 tests found, 10 successful, 0 failed.

If your team is using IntelliJ IDEA / Eclipse with Maven or Gradle instead, just add the 
org.junit.jupiter:junit-jupiter:5.10.x dependency and put test/SalesCalculatorTest.java 
under your usual test source root — no code changes needed.

5. Sample Input Format (sample_sales.csv)

product_id, product_name, category, quantity_sold, unit_price
P001, Wireless Mouse, Electronics, 12, 25.50
P002, Notebook, Stationery, 35, 3.75



