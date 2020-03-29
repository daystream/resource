package resource.filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import resource.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不起作用
 */
public class AddHeaderFilter extends OncePerRequestFilter {

    @Override
    protected  void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code != null) {
            HttpServletRequest bearerHeaderRequest = addBearerHeader(code, (HttpServletRequest) request);
            //RequestDispatcher rd=request.getRequestDispatcher("/user/my");
            //rd.forward(bearerHeaderRequest, response);
            chain.doFilter(bearerHeaderRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private HttpServletRequest addBearerHeader(String code, HttpServletRequest request) {
        String tokenText = Utils.requestTokenText(code);
        String token = Utils.getAccessToken(tokenText);
        BearerHeaderRequest bearerHeaderRequest = new BearerHeaderRequest(request);
        bearerHeaderRequest.addHeader("Authorization", String.format("Bearer %s", token));
        return bearerHeaderRequest;
    }
}