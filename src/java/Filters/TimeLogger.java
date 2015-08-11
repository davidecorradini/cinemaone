
package Filters;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author enrico
 */
public class TimeLogger implements Filter {
    
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //the time the request arrives.
        long before = System.currentTimeMillis();
        //call to the other filters/servlets.
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException t) {
            log(t.toString());
        }
        //time after the processing.
        long after = System.currentTimeMillis();
        String name = "";
        if(request instanceof HttpServletRequest){
            name = ((HttpServletRequest)request).getRequestURI();
        }
        
        log("time of " + name + " : " + (after - before) + "ms.");
    }

    /**
     * Return the filter configuration object for this filter.
     * @return the configuration of the filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy(){
        filterConfig = null;
    }

    /**
     * Init method for this filter
     * @param filterConfig the filter configuration.
     */
    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }

    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
