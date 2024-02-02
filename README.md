# Test Task for Aspira

## Overview
This Java project focuses on implementing a selective data parser for prematch information from the LeonBet bookmaker's website. The chosen strategy involves using REST requests to obtain data efficiently. Selenium was not used due to assignment constraints, and HtmlUnit faced challenges with JavaScript execution delays, leading to the REST-based approach.

## Chosen Strategy for Parsing Prematch Data on LeonBets

### Pros

1. **Speed:** Utilizing REST API allows efficiently fetching only necessary data, improving the speed of data retrieval and processing.

2. **Structured Data:** REST API returns structured data in a format that is easy to process and utilize for further analysis.

3. **Ease of Integration:** The chosen approach facilitates easy integration with REST API and utilization of obtained data without complications.

4. **Reduced Dependency on HTML Structure:** Implementing API helps avoid dependency on the HTML structure of the page.

### Cons

1. **API Dependency:** Changes in the API may require modifications to your implementation.

2. **API Limitations:** API might not provide all the required information, acting as a potential limitation.

3. **Data Transfer Costs:** If a large amount of data is transferred over the network, it may incur some data transfer costs.

4. **Dependency on API Stability:** Your implementation may depend on the stability and reliability of the connected API.

## Features
- **Selective Data Parsing:** The project selectively parses prematch data for sports categories.
- **REST API Integration:** Utilizes REST requests to fetch and process data from the LeonBet API.
- **Multithreading:** Implements methods for data retrieval and processing in synchronous and asynchronous threads.
- **Configurability:** Easily expandable by adding new categories to the enum.
- **Display Configuration:** Visualizes the expected data using a dedicated configuration class.
- **Statistics Generation:** Gathers statistics on data retrieval and processing times.


During the course of this project, a selective data parser was implemented for a bookmaker's website, focusing on LeonBet. The data retrieval was accomplished using the REST request mechanism provided by [`LeonBetRestClientImpl`](src/main/java/com/example/aspira/client/impl/LeonBetRestClientImpl.java). To enhance the handling of the retrieved data, Data Transfer Objects (DTOs) were introduced.

The primary service responsible for processing and managing the data is [`LeonBetParsingServiceImpl`](src/main/java/com/example/aspira/service/impl/LeonBetParsingServiceImpl.java).

**Display Configuration.** For visualizing the expected data, a configuration class named [`TestTaskForAspiraDisplayingConfig`](src/main/java/com/example/aspira/config/TestTaskForAspiraDisplayingConfig.java) was created. In its post-construction phase, methods from `LeonBetParsingService` are invoked to display the processed data.

**Multithreading Methods.** In addition to the task of implementing a method that performs actions in three threads, two additional methods were developed. The first one involves obtaining and processing data in a synchronous thread, while the second one does the same in an asynchronous thread for each type of sport. It's worth noting that the latter method exceeds the specified thread limitations, as it performs calculations for four sports categories.

**Category Expansion.** Expanding categories is made simple by adding new values to the `CategoryName` enum, providing a flexible way to incorporate new categories into the system.

**Data Retrieval and Statistics.** After completing the primary task, data retrieval is executed by each method in a loop, with a 5-second pause to handle potential 429 too_many_request errors. This approach allows for data collection and the generation of statistics. For example, "The average time for data retrieval and processing in synchronous mode took - 7227 ms, in asynchronous mode for each category - 6764 ms, in asynchronous mode with splitting into three threads with an equal number of leagues - 6734 ms."

## Dependencies
- Spring Boot for building and running the application.
- Lombok for simplified code.
- Jackson Databind for JSON handling.
- Spring Web for web-related components.
## Author
Created by Vladyslav Shabanov. For inquiries or additional information, contact the author at [shabanoff025@gmail.com](mailto:shabanoff025@gmail.com).