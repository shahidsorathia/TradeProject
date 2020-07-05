TradeProject

Build Application
Please use mvn clean install to build this maven project

Run the application
java -jar <<"TradeProject">> to run TradeProject jar
Json file is commited in project itself.

Please hit http://localhost:8080/trade/processTradeData?numberOfThreads=1
this will trigger data processing.
after successful response from server, please check application.log
application.log will be created at same level of jar.
If data exceeds 10MB data will be in another file appended with sequence number
