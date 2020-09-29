package servlets;

import entity.QueryEntity;
import entity.WordEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author wangyong
 */
public class ObjectServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(request.getInputStream());
            QueryEntity queryEntity = (QueryEntity) ois.readObject();

            //¥¶¿Ì
            WordEntity wordEntity = new WordEntity();
            wordEntity.setMp3Url("ok.mp3");

            ObjectOutputStream oos = new ObjectOutputStream(response.getOutputStream());
            oos.writeObject(wordEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
