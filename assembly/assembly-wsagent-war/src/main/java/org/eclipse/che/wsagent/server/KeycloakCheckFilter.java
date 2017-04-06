package org.eclipse.che.wsagent.server;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KeycloakCheckFilter extends org.keycloak.adapters.servlet.KeycloakOIDCFilter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String auth = request.getHeader("Authorization");
        if(auth == null ){
            System.out.println("No auth header for " + request.getRequestURI());
        }
        if(auth != null && auth.equals("Internal")){
            chain.doFilter(req, res);
        }else
        if (request.getRequestURI().endsWith("/ws") || request.getRequestURI().endsWith("/eventbus")
                || request.getScheme().equals("ws") || req.getScheme().equals("wss")) {
            System.out.println("Skipping " + request.getRequestURI());
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
            System.out.println(request.getRequestURL() + " status : " + ((HttpServletResponse) res).getStatus());
        }
    }

}