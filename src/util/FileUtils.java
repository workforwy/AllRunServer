package util;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author wangyong
 */
public class FileUtils {

    private String message;
    LogUtils logger = LogUtils.getInstance();

    public FileUtils() {
    }

    public static void main(String[] args) {
        FileUtils op = new FileUtils();
        String content = "�й�����ѧ�޸�";
        // content=Tools.viewToUTF8(content);
        try {

            //String file="c:\\12.txt";
//   		String ChapterDir = "c://1";
//		String zipPath = "c://1//12.zip";;
//		Vector fileNames = ZipUtils.unZip(zipPath, ChapterDir);
//   		String file="c:\\1";
//
//   		FileUtils o=new FileUtils();
//   		//o.delFile(file);
//   		o.delFolder(file);
//   		System.out.println("ok");


            //String file="D:\\JBossWeb\\webapps\\wwwMobiBobCom\\upLoadComicData\\75\\9";
            //String file="c:\\12.zip";//��ɾ��
            String file = "D:\\JBossWeb\\webapps\\wwwMobiBobCom\\upLoadComicData\\75\\16\\12.zip";//��ɾ��
            FileUtils o = new FileUtils();
            //o.delFile(file);
            o.delFolder(file);
            System.out.println("ok");
            System.out.println(File.separator);
        } catch (Exception e) {

        }
    }

    /**
     * ��ȡһ���ļ����µ������ļ���
     *
     * @param Path ������������·�����ļ�����
     * @return ����List �ļ�����
     */
    public void reName(String Path, String lang) {

        try {
            File a = new File(Path);
            String[] file = a.list();
            File temp = null;
            String filename = null;
            for (int i = 0; i < file.length; i++) {
                if (Path.endsWith(File.separator)) {
                    temp = new File(Path + file[i]);
                } else {
                    temp = new File(Path + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    filename = (temp.getName()).toString();
                    String[] fileNames = filename.split("\\.");
                    String basename = fileNames[0];
                    String extName = fileNames[1];

                    String newname = basename + "_" + lang + "." + extName;
                    System.out.println(newname);
                    temp.renameTo(new File(Path + newname));


                }
            }

        } catch (Exception e) {
            ////logger.sysException.info("",e);
        }

    }


    /**
     * ��һ���ļ������ַ���UTF-8д���ݣ�
     *
     * @param filePathAndName �ı��ļ���������·�����ļ���
     * @param fileContent     �ı��ļ�����
     * @return true  �ɹ�  false  ʧ��
     */
    public boolean WriteFileByte(String filePathAndName, String fileContent) {
        boolean r = false;

        //int l=b.l
        try {

            FileOutputStream fs = new FileOutputStream(filePathAndName);
            try {
                byte[] byteBuffer = fileContent.getBytes("UTF-8");
                fs.write(byteBuffer, 0, byteBuffer.length);
                r = true;
                fs.close();
            } catch (Exception e) {
                //	//logger.sysException.info("",e);
            }

        } catch (IOException es) {
            ////logger.sysException.info("",es);
        }

        return r;
    }

    /**
     * ��һ���ļ���ָ���ַ�������д���ݣ�
     *
     * @param filePathAndName �ı��ļ���������·�����ļ���
     * @param fileContent     �ı��ļ�����
     * @param encoding        �ַ���
     * @return
     */
    public boolean WriteFile_bak(String filePathAndName, String fileContent, String encoding) {
        boolean r = false;
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";

        //int l=b.l
        try {

            FileOutputStream fs = new FileOutputStream(filePathAndName);
            OutputStreamWriter osr;
            if (encoding.equals("")) {
                osr = new OutputStreamWriter(fs);
            } else {
                osr = new OutputStreamWriter(fs, encoding);
            }
            BufferedWriter wr = new BufferedWriter(osr);
            try {


                byte[] byteBuffer = fileContent.getBytes("UTF-8");
                //ois.write(byteBuffer, 0, byteBuffer.length);
                fs.write(byteBuffer, 0, byteBuffer.length);
                //wr.wr
                r = true;
                wr.flush();
                wr.close();
            } catch (Exception e) {
                //logger.sysException.info("",e);
            }

        } catch (IOException es) {
            //logger.sysException.info("",es);
        }

        return r;
    }

    /**
     * �������ݣ������ϻ��з���
     *
     * @param filePathAndName
     * @param encoding
     * @return
     * @throws IOException
     */
    public String readTxtLine(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    //str.append(data+"\r\n");
                    str.append(data + "\n");
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        }
        fs.close();
        return st;
    }


    /**
     * ��ȡһ���ļ����µ������ļ���
     *
     * @param path ������������·�����ļ�����
     * @return ����List �ļ�����
     */
    public ArrayList getfiles(String Path) {
        ArrayList list = new ArrayList();
        try {

            File a = new File(Path);
            String[] file = a.list();
            // System.out.println(file.length);
            File temp = null;
            String filename = null;
            for (int i = 0; i < file.length; i++) {

                if (Path.endsWith(File.separator)) {
                    temp = new File(Path + file[i]);
                } else {
                    temp = new File(Path + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    filename = (temp.getName()).toString();
                    System.out.println(filename);
                    list.add(filename);

                }
            }

        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
        return list;
    }

    /**
     * ��ȡ�ı��ļ�����
     *
     * @param filePathAndName ������������·�����ļ���
     * @param encoding        �ı��ļ��򿪵ı��뷽ʽ
     * @return �����ı��ļ�������
     */
    public String readTxt(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st = "";
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    str.append(data + " ");
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
        } catch (IOException es) {
            st = "";
        }
        return st;
    }

    /**
     * �½�Ŀ¼
     *
     * @param folderPath Ŀ¼
     * @return ����Ŀ¼�������·��
     */
    public String createFolder(String folderPath) {
        String txt = folderPath;
        try {
            File myFilePath = new File(txt);
            txt = folderPath;
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
        return txt;
    }

    /**
     * �༶Ŀ¼����
     *
     * @param folderPath ׼��Ҫ�ڱ���Ŀ¼�´�����Ŀ¼��Ŀ¼·�� ���� c:myf
     * @param paths      ���޼�Ŀ¼����������Ŀ¼�Ե��������� ���� a|b|c
     * @return ���ش����ļ����·�� ���� c:myfac
     */
    public String createFolders(String folderPath, String paths) {
        String txts = folderPath;
        try {
            String txt;
            txts = folderPath;
            StringTokenizer st = new StringTokenizer(paths, "|");
            for (int i = 0; st.hasMoreTokens(); i++) {
                txt = st.nextToken().trim();
                if (txts.lastIndexOf("/") != -1) {
                    txts = createFolder(txts + txt);
                } else {
                    txts = createFolder(txts + txt + "/");
                }
            }
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
        return txts;
    }


    /**
     * �½��ļ�
     *
     * @param filePathAndName �ı��ļ���������·�����ļ���
     * @param fileContent     �ı��ļ�����
     * @return
     */
    public void createFile(String filePathAndName, String fileContent) {

        try {
            String filePath = filePathAndName;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.createNewFile();
            }
            FileWriter resultFile = new FileWriter(myFilePath);
            PrintWriter myFile = new PrintWriter(resultFile);
            String strContent = fileContent;
            myFile.println(strContent);
            myFile.close();
            resultFile.close();
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
    }


    /**
     * �б��뷽ʽ���ļ�����
     * @param filePathAndName �ı��ļ���������·�����ļ���
     * @param fileContent �ı��ļ�����
     * @param encoding ���뷽ʽ ���� GBK ���� UTF-8
     * @return
     */
  /* public void createFile(String filePathAndName, String fileContent, String encoding) {
    
       try {
           String filePath = filePathAndName;
           filePath = filePath.toString();
           File myFilePath = new File(filePath);
           if (!myFilePath.exists()) {
               myFilePath.createNewFile();
           }
           PrintWriter myFile = new PrintWriter(myFilePath,encoding);
           String strContent = fileContent;
           myFile.println(strContent);
           myFile.close();
       }
       catch (Exception e) {
           message = "�����ļ���������";
       }
   } 
*/

    /**
     * ɾ���ļ�
     *
     * @param filePathAndName �ı��ļ���������·�����ļ���
     * @return Boolean �ɹ�ɾ������true�����쳣����false
     */
    public boolean delFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
                message = (filePathAndName + "<br>ɾ���ļ���������");
            }
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
        return bea;
    }


    /**
     * ɾ���ļ��� ������c:\\1 ������c:\
     *
     * @param folderPath �ļ�����������·��
     * @return
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //ɾ����������������
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //ɾ�����ļ���
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
    }


    /**
     * ɾ��ָ���ļ����������ļ�
     *
     * @param path �ļ�����������·��
     * @return
     */
    public boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//��ɾ���ļ���������ļ�
                delFolder(path + "/" + tempList[i]);//��ɾ�����ļ���
                bea = true;
            }
        }
        return bea;
    }


    /**
     * ���Ƶ����ļ�
     *
     * @param oldPathFile ׼�����Ƶ��ļ�Դ
     * @param newPathFile �������¾���·�����ļ���
     * @return
     */
    public void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) { //�ļ�����ʱ
                InputStream inStream = new FileInputStream(oldPathFile); //����ԭ�ļ�
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //�ֽ��� �ļ���С
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
    }


    /**
     * ���������ļ��е�����
     *
     * @param oldPath ׼��������Ŀ¼
     * @param newPath ָ������·������Ŀ¼
     * @return
     */
    public void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs(); //����ļ��в����� �������ļ���
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//��������ļ���
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            //logger.sysException.info("",e);
        }
    }


    /**
     * �ƶ��ļ�
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }


    /**
     * �ƶ�Ŀ¼
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public String getMessage() {
        return this.message;
    }
}

