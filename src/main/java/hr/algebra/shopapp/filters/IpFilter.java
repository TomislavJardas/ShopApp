package hr.algebra.shopapp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@WebFilter("/*")
public class IpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ipAddress = httpRequest.getRemoteAddr();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        int statusCode = httpResponse.getStatus();
        System.out.println("Request Status: " + statusCode);
        System.out.println("Client IP Address: " + ipAddress);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            System.out.println("Authenticated User: " + username);
        }
        System.out.println();
        chain.doFilter(request, response);
    }
}

