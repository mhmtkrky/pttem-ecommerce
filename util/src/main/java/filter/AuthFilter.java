package filter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class AuthFilter implements Filter {
  private List<HandlerMapping> handlerMappings;
  private String url;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    boolean problem = false;
    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse res = (HttpServletResponse) servletResponse;
    url = req.getRequestURL().toString();
    if (url.contains("user/login")
        || url.contains("user/register")
        || url.contains("/v2/api-docs")) {
      filterChain.doFilter(servletRequest, servletResponse);
    } else {
      String user = req.getHeader("user");
      if (user == null || user.equals("")) {
        res.sendError(403, "not logged");
        problem = true;
      } else if (handlerMappings != null) {
        for (HandlerMapping handlerMapping : handlerMappings) {
          try {
            HandlerExecutionChain handlerExecutionChain =
                handlerMapping.getHandler((HttpServletRequest) servletRequest);
            if (handlerExecutionChain != null
                && handlerExecutionChain.getHandler() instanceof HandlerMethod) {
              HandlerMethod handlerMethod = (HandlerMethod) (handlerExecutionChain.getHandler());
              Method method = handlerMethod.getMethod();
              if (method.getAnnotation(AdminLevelRequest.class) != null) {
                String admin = req.getHeader("admin");
                if (admin == null || admin.equals("")) {
                  res.sendError(402, "admin level required");
                  problem = true;
                  break;
                }
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
    if (!problem) filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {}

  public List<HandlerMapping> getHandlerMappings() {
    return handlerMappings;
  }

  public void setHandlerMappings(List<HandlerMapping> handlerMappings) {
    this.handlerMappings = handlerMappings;
  }
}
