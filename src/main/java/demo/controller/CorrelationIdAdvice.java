package demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CorrelationIdAdvice extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    // http://stackoverflow.com/questions/14532976/performance-of-random-uuid-generation-with-java-7-or-java-6
	    // http://www.cowtowncoder.com/blog/archives/2010/10/entry_429.html
	    // https://github.com/cowtowncoder/java-uuid-generator
	    String id = UUID.randomUUID().toString();
	    // https://logging.apache.org/log4j/2.x/manual/thread-context.html
	    ThreadContext.put("ID", id + " - ");
	    ThreadContext.put("ShortID", id.substring(0, 7) + " - ");

	    return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	    ThreadContext.clearAll();

	    super.afterCompletion(request, response, handler, ex);
	}

	public static String getCorrelationId() {
	    return ThreadContext.get("ID");
	}
}
