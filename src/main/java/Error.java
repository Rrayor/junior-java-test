import com.benjaminsimon.testweb.logging.WebLogger;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author simon
 */
@WebServlet(urlPatterns = {"/error"})
public class Error extends HttpServlet {
    
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
    private void setupLogger() {
        if(WebLogger.isSetUp)
            return;
        
        try {
                WebLogger.setup();
        } catch (IOException | IllegalArgumentException ex) {
            LOGGER.warning(ex.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setupLogger();
        
        Exception exception = (Exception) request.getAttribute("exception");
        
        LOGGER.severe(exception.getMessage());
        
        try {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
