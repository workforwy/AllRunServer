package dao.conn;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

/**
 * ���ӳع�����,���Թ��������ݿ����ӳ�
 */
public class DBConnManager {
    //���ӳ����б�
    private Vector poolnames = new Vector();
    //�����������б�
    private Vector drivernames = new Vector();
    //���ݿ��ʶ�б�
    private Vector dbids = new Vector();
    //�û����б�
    private Vector usernames = new Vector();
    //�����б�
    private Vector passwds = new Vector();
    //����������б�
    private Vector maxconns = new Vector();
    //���ӳض���
    private static Hashtable connPools = new Hashtable();

    static DBConnManager instance;

    public DBConnManager() {
        try {
            InputStream is = DBConnManager.class.getClassLoader().getResourceAsStream("db.properties");
            Properties pro = new Properties();
            pro.load(is);
            //���mysql���ݿ��������Ϣ
            poolnames.addElement("mssql");
            drivernames.addElement(pro.getProperty("drivers"));
            dbids.addElement(pro.getProperty("dbids"));
            usernames.addElement(pro.getProperty("user"));
            passwds.addElement(pro.getProperty("password"));
            maxconns.addElement(pro.getProperty("maxconns"));
        } catch (Exception e) {
            System.out.print(e);
        }
        //�������ӳ�
        createPools();
    }

    /**
     * �õ�һ��ָ�����ӳ��е�����
     */
    public Connection getConnection(String name) {
        DBConnPool pool = (DBConnPool) connPools.get(name);
        if (pool != null)
            return pool.getConnection();
        return null;
    }

    /**
     * �����ӷ��ظ���ָ�������ӳ�
     */
    public void releaseConnection(String name, Connection con) {
        DBConnPool pool = (DBConnPool) connPools.get(name);
        if (pool != null) {
            pool.releaseConnection(con);
        }
    }

    /**
     * �ر���������
     */
    public synchronized void closeConns() {
        Enumeration allPools = connPools.elements();
        while (allPools.hasMoreElements()) {
            DBConnPool pool = (DBConnPool) allPools.nextElement();
            pool.closeConn();
        }
    }

    /**
     * �������ӳ�
     */
    private void createPools() {
        for (int i = 0; i < poolnames.size(); i++) {
            String poolname = poolnames.elementAt(i).toString();
            String drivername = drivernames.elementAt(i).toString();
            String dbid = dbids.elementAt(i).toString();
            String username = usernames.elementAt(i).toString();
            String passwd = passwds.elementAt(i).toString();
            int maxconn = 0;
            try {
                maxconn = Integer.parseInt(maxconns.elementAt(i).toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            DBConnPool pool = new DBConnPool(poolname, drivername, dbid, username, passwd, maxconn);
            connPools.put(poolname, pool);
        }
    }

    /**
     * ��ȡ�������������
     *
     * @param name
     * @return
     */
    public static int getLinkNum(String name) {
        DBConnPool pool = (DBConnPool) connPools.get(name);
        return pool.getInUse();
    }

    /**
     * ��ȡ����ʵ��
     *
     * @return
     */
    public static synchronized DBConnManager getInstance() {
        if (instance == null) {
            instance = new DBConnManager();
        }
        return instance;
    }
}

