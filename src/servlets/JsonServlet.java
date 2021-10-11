package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JsonServlet extends HttpServlet {

    public JsonServlet() {

    }

    @Override
    public void init() {

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//         PrintWriter out = response.getWriter();
//         String word = request.getParameter("word");
//         String jsonString = "";
//         if ("good".equals(word)) {
//             jsonString = "{'mp3url':'1.mp3','acceptation':'ºÃ'}";
//         } else {
//             jsonString = "{'mp3url':'2.mp3','acceptation':'¸üºÃ'}";
//         }

//         out.write(jsonString);
//         out.println();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
