package Listeners;



import Database.DBManager;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class WebappContextListener implements ServletContextListener {    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl"); //dal file xml.
        try {
            DBManager manager = new DBManager(dburl); //istanzio un DBManager.
            sce.getServletContext().setAttribute("dbmanager", manager);//pubblico l'attributo per il context
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
// Il database Derby deve essere "spento" tentando di connettersi al database con shutdown=true
        DBManager.shutdown();
    }
}

