package com.benjaminsimon.testweb;

import com.benjaminsimon.testconsole.FilterAndOrder;
import com.benjaminsimon.testconsole.TextList;
import com.benjaminsimon.testconsole.TextList.Order;
import com.benjaminsimon.testconsole.XmlReader;
import com.benjaminsimon.testweb.config.InputConfig;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet that handles /listview requests
 * @author simon
 */
@WebServlet(urlPatterns = {"/listview"})
public class Listview extends HttpServlet {

    /**
     * Run doGet on post request too.
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see doGet
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    
    /**
     * Reads the XML file according to the path stored in session.
     * Handles request parameters and sets filtering and ordering options accordingly.
     * Runs filtering and sorting on data read from XML.
     * Sends back the filtering and ordering options to client.
     * Sends back the filtered, ordered and formatted list to client.
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * @see FilterAndOrder
     * @see createFilterAndOrder
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String xmlFilePath = (String) session.getAttribute("xml");
        
        //If xmlFilePath could not be found, redirect to index.jsp
        if(xmlFilePath == null || xmlFilePath.isBlank()) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        //Get parameters
        String search = request.getParameter("search");
        String orderString = request.getParameter("order");
        String rev = request.getParameter("rev");
        
        //Setup filter and order options
        FilterAndOrder filterAndOrder = createFilterAndOrder(search, orderString, rev);
        
        try {
            //Read the xmlFile
            XmlReader xmlReader = new XmlReader();
            TextList textList = xmlReader.readXml(xmlFilePath);
            
            //Filtering and sorting
            textList.filterAndSort(filterAndOrder);
            
            //Get the formatted values
            List<String> list = textList.getFormattedText();
            
            //Send filterAndOrder to client, so it can set input values
            request.setAttribute("filterAndOrder", filterAndOrder);
            
            //Send list to client
            request.setAttribute("list", list);
            
            //Dispatch listview.jsp
            request.getRequestDispatcher("listview.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            
            //Send exception and dispatch error.jsp
            request.setAttribute("exception", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    /**
     * Creates a FilterAndOrder instance and sets its values according to parameters
     * provided.
     * @param search The String to filter by.
     * @param orderString The String parameter to set Order value by.
     * @param rev The String parameter to set reverse by.
     * @return A FilterAndOrder instance with its properties set.
     * @see FilterAndOrder
     */
    private FilterAndOrder createFilterAndOrder(String search, String orderString, String rev) {
        FilterAndOrder filterAndOrder = new FilterAndOrder();

        //Check search parameter and set filter option
        if(search.isBlank())
            search = null;
        
        filterAndOrder.setFilter(search);
        
        //Check orderString parameter and set order option to it
        if(orderString == null || orderString.isBlank())
            orderString = InputConfig.ORDER_BY_NAME_STRING;
        
        Order order = Order.NAME;
        if(orderString.equals(InputConfig.ORDER_BY_FREQUENCY_STRING))
            order = Order.FREQUENCY;
        
        filterAndOrder.setOrder(order);
        
        //Check rev parameter and set reverse option to it
        if(rev == null || rev.isBlank())
            rev = InputConfig.PLACEHOLDER_STRING;
        

        boolean reverse = false;
        if(rev.equals(InputConfig.REVERSE_ORDER_STRING))
            reverse = true;
        filterAndOrder.setReverse(reverse);
        
        return filterAndOrder;
    }
}
