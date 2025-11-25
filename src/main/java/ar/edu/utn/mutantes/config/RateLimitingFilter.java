package ar.edu.utn.mutantes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_MS = 60_000;

    private final Map<String, RequestData> requestMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Toma la IP desde X-Forwarded-For si existe
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank()) {
            ip = request.getRemoteAddr();
        }

        long now = Instant.now().toEpochMilli();

        requestMap.putIfAbsent(ip, new RequestData(0, now));
        RequestData data = requestMap.get(ip);

        synchronized (data) {

            if (now - data.startTime > WINDOW_MS) {
                data.count = 0;
                data.startTime = now;
            }

            data.count++;

            if (data.count > MAX_REQUESTS) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("""
                    { "error": "Too Many Requests", 
                      "message": "Máximo 10 requests por minuto por IP." }
                """);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static class RequestData {
        int count;
        long startTime;

        RequestData(int count, long startTime) {
            this.count = count;
            this.startTime = startTime;
        }
    }
}
