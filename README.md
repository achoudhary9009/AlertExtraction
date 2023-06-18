# Alert Extraction

This Scala project retrieves alerts from an API and extracts query terms from the alerts. It then matches the query terms with the alerts and stores the results in a data file.

## Prerequisites

- SBT installed on your machine
- API keys for the Alerts API and Query Terms API (obtained from the provider)

## Setup and Run

1. Clone the repository or download the source code files.
2. Change API key in application.conf file in resources directory.
3. Open a terminal and navigate to the project directory.
4. To execute run this command ```sbt run```

## Results

The results will be written in ```data.txt``` file in project root directory
