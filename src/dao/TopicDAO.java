package dao;

import java.sql.PreparedStatement;
import java.util.*;

import dao.operate.Modify;
import dao.operate.Select;
import dao.operate.SetParameter;
import entity.TopicEntity;
import entity.UserEntity;
import util.*;

public class TopicDAO {
    private final static String table_topic = "topic";

    private final static String col_id = "id";
    private final static String col_username = "username";
    private final static String col_content = "content";
    private final static String col_address = "address";
    private final static String col_imageUrl = "imageUrl";
    private final static String col_latitude = "latitude";
    private final static String col_longitude = "longitude";
    private final static String col_createTime = "createTime";


    public TopicEntity[] queryAll() {
        TopicEntity[] topicEntitys = null;
        try {

            String sql = "select " + col_id + "," + col_username + ","
                    + col_content + "," + col_imageUrl + "," + col_address + ","
                    + col_latitude + "," + col_longitude + "," + col_createTime
                    + " from topic order by id desc";

            Select select = new Select();
            List list = select.selectRS(sql);
            topicEntitys = new TopicEntity[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String id = String.valueOf(((Map) list.get(i)).get(col_id));
                String username = String.valueOf(((Map) list.get(i))
                        .get(col_username));
                String content = String.valueOf(((Map) list.get(i))
                        .get(col_content));
                String imageUrl = String.valueOf(((Map) list.get(i))
                        .get(col_imageUrl));
                String address = String.valueOf(((Map) list.get(i))
                        .get(col_address));
                String latitude = String.valueOf(((Map) list.get(i))
                        .get(col_latitude));
                String longitude = String.valueOf(((Map) list.get(i))
                        .get(col_longitude));
                String createTime = String.valueOf(((Map) list.get(i))
                        .get(col_createTime));

                TopicEntity topicEntity = new TopicEntity();
                topicEntity.setId(Integer.parseInt(id));
                topicEntity.setUsername(username);
                topicEntity.setContent(content);
                topicEntity.setImageUrl(imageUrl);
                topicEntity.setAddress(address);
                topicEntity.setLatitude(Double.parseDouble(latitude));
                topicEntity.setLongitude(Double.parseDouble(longitude));

                topicEntitys[i] = topicEntity;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Tools.writeException(e);
        }

        return topicEntitys;
    }

    public int addTopic(final TopicEntity topicEntity) {
        int statusCode = Const.STATUS_SERVER_ERROR;
        try {
            String sql = "insert into topic" + "(" + col_username + ","
                    + col_content + "," + col_imageUrl + "," + col_address
                    + "," + col_latitude + "," + col_longitude + ","
                    + col_createTime + ") values(?,?,?,?,?,?,?)";
            class SetParam implements SetParameter {
                @Override
                public void set(PreparedStatement preparedStatement)
                        throws Exception {
                    preparedStatement.setString(1, topicEntity.getUsername());
                    preparedStatement.setString(2, topicEntity.getContent());
                    preparedStatement.setString(3, topicEntity.getImageUrl());
                    preparedStatement.setString(4, topicEntity.getAddress());
                    preparedStatement.setDouble(5, topicEntity.getLatitude());
                    preparedStatement.setDouble(6, topicEntity.getLongitude());
                    preparedStatement.setLong(7, topicEntity.getCreateTime());

                }
            }
            Modify modify = new Modify();

            int id = modify.exec(sql, new SetParam());
            if (id >= 1) {
                statusCode = Const.STATUS_OK;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            statusCode = Const.STATUS_SERVER_ERROR;
            e.printStackTrace();
            Tools.writeException(e);
        }

        return statusCode;
    }
}
