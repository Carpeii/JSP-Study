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

public class LoggingFilter implements Filter {
    private PrintWriter writer;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("LoggingFilter initialized");
        String fileName = "./monitor.log";

        try{
            writer = new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            throw new ServletException("로그 파일을 열 수 없음");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        writer.printf("현재시간 : %s %n", new Date().toLocaleString());

    }

    @Override
    public void destroy() {
        if(writer != null){
            writer.close();
        }
    }
}
