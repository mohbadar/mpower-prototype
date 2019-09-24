//package af.dfi.lang.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//
//@Component
//@Slf4j
//public class LoggingAccessDeniedHandler implements AccessDeniedHandler{
//
//    @Override
//    public void handle(HttpServletRequest request,
//                       javax.servlet.http.HttpServletResponse response,
//                       AccessDeniedException ex) throws IOException, ServletException {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth != null) {
//            log.info(auth.getName()
//                    + " was trying to access protected resource: "
//                    + request.getRequestURI());
//        }
//
//        response.sendRedirect(request.getContextPath() + "/access-denied");
//
//    }
//
//}
