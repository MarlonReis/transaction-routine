package br.com.pismo.challenge.transaction.infrastructure.config;

import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Configuration
public class CorrelationIdConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            final var correlationalId = req.getHeader("correlation-id");
            if (correlationalId == null) {
                MDC.put("correlation-id", UUID.randomUUID().toString());
            } else {
                MDC.put("correlation-id", correlationalId);
            }
            chain.doFilter(req, res);
        } catch (ServletException | IOException ex) {
            MDC.remove("correlation-id");
        }
    }
}
