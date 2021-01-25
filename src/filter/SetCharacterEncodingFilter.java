package filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import javax.servlet.UnavailableException;

public class SetCharacterEncodingFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig filterConfig = null;
    protected boolean ignore = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        this.ignore = value == null || value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (ignore || (request.getCharacterEncoding() == null)) {
            String encoding = selectEncoding(request);
            if (encoding != null)
                request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        this.encoding = null;
        this.filterConfig = null;
    }

    protected String selectEncoding(ServletRequest request) {
        return this.encoding;
    }
}
