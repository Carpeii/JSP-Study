package filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.crypto.Data;

public class LogFilter implements Filter {
	private PrintWriter writer;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String filename = "/Users/kimjiwoong/javaprojects/myJSP/src/main/java/log/monitor.log";

		try {
			writer = new PrintWriter(new FileWriter(filename,true),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ServletException("로그 파일을 열 수 없습니다.");

		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		if(writer!=null) writer.close();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		chain.doFilter(request, response);
		writer.printf("현재시간 : %s %n", new Date().toLocaleString());
		writer.printf("접속 클라이언트 주소 : %s %n", request.getRemoteAddr());

//		chain.doFilter(request, response);

		writer.printf("문서유형 : %s %n", response.getContentType());
		writer.println("-----------------------------------------");
	}

}
