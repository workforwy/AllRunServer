package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class JsonUtil {
    //读取json文件
    public static String readJsonFile(String fileName) {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String path = JsonUtil.class.getClassLoader().getResource("./data.json").getPath();
        String s = readJsonFile(path);
        JSONObject jobj = JSON.parseObject(s);
        //构建JSONArray数组
        JSONArray movies = jobj.getJSONArray("data");
        for (int i = 0; i < movies.size(); i++) {
            JSONObject key = (JSONObject) movies.get(i);
            String name = (String) key.get("name");
            String director = (String) key.get("director");
            String scenarist = ((String) key.get("scenarist"));
            String actors = ((String) key.get("actors"));
            String type = ((String) key.get("type"));
            String ratingNum = ((String) key.get("ratingNum"));
            String tags = ((String) key.get("tags"));
            System.out.println(name);
            System.out.println(director);
            System.out.println(scenarist);
            System.out.println(actors);
            System.out.println(type);
            System.out.println(director);
            System.out.println(ratingNum);
            System.out.println(tags);
        }
    }
}
