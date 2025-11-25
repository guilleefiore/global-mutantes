package ar.edu.utn.mutantes.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RateLimitingFilterTest {

    private RateLimitingFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private PrintWriter writer;

    @BeforeEach
    void setUp() throws IOException {
        filter = new RateLimitingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
        when(request.getRemoteAddr()).thenReturn("1.1.1.1");
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/mutant");
    }

    @Test
    void testAllowsRequestsUnderLimit() throws ServletException, IOException {
        for (int i = 0; i < 5; i++) {
            filter.doFilterInternal(request, response, chain);
        }

        verify(chain, times(5)).doFilter(request, response);
        verify(response, never()).setStatus(429);
    }

    @Test
    void testBlocksAfterLimitExceeded() throws ServletException, IOException {
        for (int i = 0; i < 11; i++) {
            filter.doFilterInternal(request, response, chain);
        }

        // verify throttling
        verify(response, atLeastOnce()).setStatus(429);
        verify(writer, atLeastOnce()).write(contains("Too Many Requests"));
    }

    @Test
    void testShouldNotFilterSwagger() {
        when(request.getRequestURI()).thenReturn("/swagger-ui");

        assertTrue(filter.shouldNotFilter(request));
    }

    @Test
    void testShouldNotFilterH2() {
        when(request.getRequestURI()).thenReturn("/h2-console");

        assertTrue(filter.shouldNotFilter(request));
    }
}
