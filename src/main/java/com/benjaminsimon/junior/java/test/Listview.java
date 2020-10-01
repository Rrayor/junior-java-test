/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.benjaminsimon.testconsole.Data;
import com.benjaminsimon.testconsole.Data.Order;
import java.io.IOException;
import java.util.List;
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
@WebServlet(urlPatterns = {"/listview"})
public class Listview extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        String xml = (String) session.getAttribute("xml");
        String search = request.getParameter("search");
        String order = request.getParameter("order");
        String rev = request.getParameter("rev");
        
        search = search != null && !search.isBlank() ? search : "";
        order = order != null && !order.isBlank() ? order : "_";
        rev = rev != null && !rev.isBlank() ? rev : "_";
        
        Order o = order.equals("f") ? Data.Order.FREQUENCY : Data.Order.NAME;
        
        boolean r = rev.equals("rev") ? true : false;
        
        if(xml == null || xml.isBlank()) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        try {
            Data.ReadXml(xml);
            System.out.println(search);
            List<String> names = Data.filter(Data.texts, search);
            names = Data.sort(names, o, r);
            names = Data.getFormattedText(names);
            request.setAttribute("names", names);
            request.getRequestDispatcher("listview.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("exception", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }


    

}
