package com.benjaminsimon.junior.java.test;

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
 *
 * @author simon
 */
@WebServlet(urlPatterns = {"/add-xml"})
public class AddXml extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Map<String, String> messages = new HashMap<>();
        final String xml = request.getParameter("xml");
        
        if(xml == null || xml.isBlank()) {
            messages.put("xml", "Invalid file path");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        try {
            session.setAttribute("xml", xml);
            request.getRequestDispatcher("listview").forward(request, response);
        } catch (IOException | ServletException e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    
}
