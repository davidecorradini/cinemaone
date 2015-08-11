package Listeners;

/**
 *
 * @author enrico
 */

import Database.DBManager;
import java.sql.SQLException;
import java.util.logging.Logger;
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
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
// Il database Derby deve essere "spento" tentando di connettersi al database con shutdown=true
        DBManager.shutdown();
    }
}

