# junior-java-test
Created for a job application.

# The Purpose
The program is a web-application using Servlets and JSP. It asks for a file path to an XML file and when it receives a valid one, it creates a view where certain data from the file is displayed. It also provides the ability to filter the data, to order alphabetically or to reverse the ordering. It uses my other project "junior-java-test-console" as dependency.

# Usage
To use the program, you should launch it on a server. During development I used tomcat. When you navigate to the url of the application, you will see a form with a text input.
You should type or paste the file path to your xml file in the input field and submit the form. The app then should redirect you to the listview. The listview contains the items that match the conditions programmed in. Above the list there is a paragraph that tells you the number of items displayed and above that there is the form for searching and sorting.

If something goes wrong during running the application, it will redirect you to an error page, where you can see some info about the problem.

# For developers and reviewers
Now I will descripe the inner workings of the program further.

## Entry
The entry point for the program is index.jsp All it does is display a form. If it finds that an XML file path is already stored in the session, it redirects the user to listview.

## Listview Servlet
In its doPost method it just calls doGet().

In doGet() it does the most of the work. First it gets the XML file path from the session. If it can't be found, it redirects the user to index.jsp.

Then it reads search, order, and rev from the URL parameters, then creates a new FilterAndOrder instance buy calling createFilterAndOrder() method. This method validates search, order and rev in their respected validation functions and returns a FilterAndOrder instance with the right properties set.

Then it creates a new XmlReader and a textList by reading the XML file at the file path provided.

It calls filterAndSort on textList with the FilterAndOrder instance, sets request attribute("filterAndOrder") to it, so in the jsp we can set the input values.
Sets request attribute("list") to textList and dispatches listview.jsp.

If an exception is thrown, it removes the session attribute("xml") before redirecting to error page.

## listview.jsp
It displays the listview page.

It gets the filter and order values from request and sets inputs accordingly.

## AddXml Servlet
In its doPost method it gets the xml path from the request parameters. If it is not valid, it sends a message to the client through index.jsp.

It sets session attribute("xml") to the file path then dispatches listview.

## Logging
The WebLogger class is a simple logger class. It has a setup method and LOGFILE_PATH constant field. The setup method sets up the logger to use the provided filePath for logging in .txt format. If its boolean useConsole parameter is false, then it won't use console for logging at all. It has an overrided version, which doesn't require any parameters, instead calls it with true value.

The default behaviour of the logger is to use the console as well as the file for logging.

It is basically the same as the TestLogger class in my "junior-java-test-console" project. I just thought it better to handle the logging separately.

## Config
InputConfig class holds the constant values for filter and order parameters.

Creating real config files was out of the scope of the project. I may do it later. However This solution is still better than "magic strings".
