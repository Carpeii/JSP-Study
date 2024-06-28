package servlet01;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletEx01 extends HttpServlet {
    private String exampleParam;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        exampleParam = config.getInitParameter("exampleParam");
        System.out.println("init with parameter" + exampleParam);
    }

    @Override
    public void destroy() {
        System.out.println("destroy!!!");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Do Get");
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Do Post");
        doProcess(request, response);
    }

    private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 파라미터에서 텍스트 값을 가져옴
        String inputText = request.getParameter("inputText");

        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='utf-8' />");
        out.println("<title>Servlet Response</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Received Text: " + inputText + "</h1>");
        out.println("<h2>exampleParam Text: " +exampleParam + "</h2>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
