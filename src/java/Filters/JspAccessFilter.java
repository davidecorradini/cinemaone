/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspAccessFilter implements Filter {
    
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    
    public JspAccessFilter() {
    }    
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
       
        String uri = ((HttpServletRequest)request).getRequestURI();
        String destination;
        switch(uri) {
            case "/Multisala/jsp/dettaglio-film.jsp": destination= "/Multisala/dettaglio-film.html";
                break;
            case "/Multisala/jsp/film.jsp": destination= "/Multisala/film.html";
                break;
            case "/Multisala/jsp/index.jsp": destination= "/Multisala/index.html";
                break;
            case "/Multisala/jsp/prenotazione.jsp": destination= "/Multisala/prenotazione.html";
                break;
            case "/Multisala/jsp/spettacoli.jsp": destination= "/Multisala/spettacoli.html";
                break;
            case "/Multisala/jsp/admin-index.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/admin-spettacoli.jsp": destination= "/Multisala/admin/spettacoli.html";
                break;
            case "/Multisala/jsp/clienti-top.jsp": destination= "/Multisala/admin/clienti-top.html";
                break;
            case "/Multisala/jsp/dettaglio-spettacolo.jsp": destination= "/Multisala/admin/spettacoli.html";
                break;
            case "/Multisala/jsp/genera-spettacoli.jsp": destination= "/Multisala/generaSpettacoli";
                break;
            case "/Multisala/jsp/gestione-prenotazioni.jsp": destination= "/Multisala/admin/prenotazioni.html";
                break;
            case "/Multisala/jsp/gestisci-sale.jsp": destination= "/Multisala/admin/sale.html";
                break;
            case "/Multisala/jsp/incasso-film.jsp": destination= "/Multisala/admin/incassi-film.html";
                break;
            case "/Multisala/jsp/infoPrenotazioniUtente.jsp": destination= "/Multisala/profile.html";
                break;
            case "/Multisala/jsp/admin-error.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/error.jsp": destination= "/Multisala/index.html";
                break;
            case "/Multisala/jsp/footer-admin.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/footer-admin2.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/footer.jsp": destination= "/Multisala/index.html";
                break;
            case "/Multisala/jsp/header-admin.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/header-admin2.jsp": destination= "/Multisala/admin/index.html";
                break;
            case "/Multisala/jsp/header.jsp": destination= "/Multisala/index.html";
                break;
            case "/Multisala/jsp/menu.jsp": destination= "/Multisala/index.html";
                break;
            case "/Multisala/jsp/prenotazioneEffettuata.jsp": destination= "/Multisala/admin/conferma-prenotazione.html";
                break;
            case "/Multisala/jsp/recovery-password.jsp": destination= "/Multisala/admin/password-recovery.html";
                break;
            case "/Multisala/jsp/sala-admin.jsp": destination= "/Multisala/admin/sale.html";
                break;
            case "/Multisala/jsp/sala.jsp": destination= "/Multisala/prenotazione.html";
                break;
            default: 
                chain.doFilter(request, response);
                return;
            }
        System.out.println("URI: "+ uri + "     Destination: "+ destination);
         ((HttpServletResponse)response).sendRedirect(destination);
       // ((HttpServletRequest)request).getSession().getServletContext().getRequestDispatcher(destination).forward(request, response);
        
        
        
    }
    
    /**
     * Return the filter configuration object for this filter.
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
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("JspAccessFilter()");
        }
        StringBuffer sb = new StringBuffer("JspAccessFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);        
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);                
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");                
                pw.print(stackTrace);                
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }
    
}
