package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author wangyong
 */
public class StringServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //�Զ����ʽ/�Զ���Э��:Ӧ�ò�Э��http ,xmpp 1#a|2#k
            //http: username=1&pwd=a ,�ϴ��ļ�
            //<user>
            DataInputStream dis = new DataInputStream(request.getInputStream());
            String str = dis.readUTF();
            //str=1#a
            str = str + "server";
            DataOutputStream dos = new DataOutputStream(response.getOutputStream());
            dos.writeUTF(str);

        } catch (Exception e) {
            System.out.println("������" + e);
        }
    }
}
