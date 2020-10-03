package com.benjaminsimon.testweb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Responsible for checking XML file path and setting session variable to it.
 * @author simon
 */
@WebServlet(urlPatterns = {"/add-xml"})
public class AddXml extends HttpServlet {
    
    /**
     * Handles Post request.Handles "xml" parameter.If it is ok, sets the session attribute "xml" to it.Dispatches listview
     * @param request request object
     * @param response response object
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     * 
     * @see Listview
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        //Variable to store error messages
        Map<String, String> messages = new HashMap<>();
        
        //Get parameter
        final String xml = request.getParameter("xml");
        
        //If xml is empty, send an error message
        if(xml == null || xml.isBlank()) {
            messages.put("xml", "Invalid file path");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        try {
            //Set xml session attribute
            session.setAttribute("xml", xml);
            
            //Forward to listview
            response.sendRedirect("listview");
        } catch (IOException e) {
            
            //Send exception and dispatch error.jsp
            request.setAttribute("exception", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    
}
