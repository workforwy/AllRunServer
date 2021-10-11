package dao;

import java.sql.PreparedStatement;
import java.util.*;

import dao.operate.Modify;
import dao.operate.PaginationWeb;
import dao.operate.Select;
import dao.operate.SetParameter;
import entity.UserEntity;
import util.*;

public class UserDAO {
    private final static String table_user = "user";

    private final static String col_id = "id";
    private final static String col_username = "username";
    private final static String col_md5password = "md5password";
    private final static String col_nickname = "nickname";
    private final static String col_gender = "gender";
    private final static String col_iconUrl = "iconUrl";
    private final static String col_latitude = "latitude";
    private final static String col_longitude = "longitude";
    private final static String col_intro = "intro";

    private final static String col_regTime = "regTime";

    public boolean checkUserIsExist(final String username, final String md5Password) {
        try {
            class SetParam implements SetParameter {
                @Override
                public void set(PreparedStatement preparedStatement) throws Exception {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, md5Password);
                }
            }
            String sql = "select * from user " + "where " + col_username + "=? and " + col_md5password + "=? ";

            Select select = new Select();
            List list = select.selectRS(sql, new SetParam());

            if (list.size() >= 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return false;
    }


    public static void main(String[] args) {
//        new UserDAO().queryAll();
        System.out.println(new UserDAO().queryAll());
    }
    /**
     * 根据姓名和密码查询
     *
     * @param username
     * @param md5Password
     * @return
     */
    public UserEntity queryDetailByUsername(final String username, final String md5Password) {
        UserEntity userEntity = new UserEntity();
        try {
            String sql = "select * from user " + "where " + col_username + "=? and " + col_md5password + "=? ";
            List list = new Select().selectRS(sql, new SetParam(username, md5Password));
            userEntity = setIntoEntity(list, userEntity);
        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return userEntity;
    }

    static class SetParam implements SetParameter {

        private String username, md5Password;




        public SetParam(String username, String md5Password) {
            this.username = username;
            this.md5Password = md5Password;
        }

        @Override
        public void set(PreparedStatement preparedStatement) throws Exception {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, md5Password);
        }
    }

    private UserEntity setIntoEntity(Object entity, UserEntity userEntity) {
        String id = getValue(entity, col_id);
        String username = getValue(entity, col_username);
        String md5password = getValue(entity, col_md5password);
        String nickname = getValue(entity, col_nickname);
        String gender = getValue(entity, col_gender);
        String iconUrl = getValue(entity, col_iconUrl);
        String latitude = getValue(entity, col_latitude);
        String longitude = getValue(entity, col_longitude);
        String intro = getValue(entity, col_intro);
        String regTime = getValue(entity, col_regTime);

        if (Tools.isNull(latitude)) {
            latitude = "0";
        }
        if (Tools.isNull(longitude)) {
            longitude = "0";
        }
        if (Tools.isNull(regTime)) {
            regTime = "0";
        }

        userEntity.setId(Integer.parseInt(id));
        userEntity.setUsername(username);
        userEntity.setMd5password(md5password);
        userEntity.setNickname(nickname);
        userEntity.setGender(gender);
        userEntity.setIconUrl(iconUrl);
        userEntity.setLatitude(Double.parseDouble(latitude));
        userEntity.setLongitude(Double.parseDouble(longitude));
        userEntity.setIntro(intro);
        userEntity.setRegTime(Double.parseDouble(regTime));
        return userEntity;
    }

    private String getValue(Object entity, String key) {
        return String.valueOf(((Map) entity).get(key));
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public UserEntity[] queryAll() {
        UserEntity[] userEntitys = null;
        try {

            String sql = "select " + col_id + "," + col_username + ","
                    + col_md5password + "," + col_nickname + "," + col_gender
                    + "," + col_iconUrl + "," + col_latitude + ","
                    + col_longitude + "," + col_intro + "," + col_regTime
                    + " from user order by id desc";

            Select select = new Select();
            List list = select.selectRS(sql);
            userEntitys = new UserEntity[list.size()];
            for (int i = 0; i < list.size(); i++) {
                UserEntity userEntity = new UserEntity();
                userEntitys[i] = setIntoEntity(list.get(i), userEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return userEntitys;
    }

    /**
     * 分页查询
     *
     * @param pageIndex
     * @param rowNum
     * @return
     */
    public UserEntity[] queryUserByPage(int pageIndex, int rowNum) {
        UserEntity[] userEntitys = null;
        try {

            String sql = "select " + col_id + "," + col_username + ","
                    + col_md5password + "," + col_nickname + "," + col_gender
                    + "," + col_iconUrl + "," + col_latitude + ","
                    + col_longitude + "," + col_intro + "," + col_regTime
                    + " from user order by id desc";

            PaginationWeb select = new PaginationWeb();
            List list = select.selectRS(sql, pageIndex, rowNum);
            userEntitys = new UserEntity[list.size()];
            for (int i = 0; i < list.size(); i++) {
                UserEntity userEntity = new UserEntity();
                userEntitys[i] = setIntoEntity(list.get(i), userEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return userEntitys;
    }

    /**
     * 注册
     *
     * @param userEntity
     * @return
     */
    public int register(final UserEntity userEntity) {
        int statusCode = Const.STATUS_SERVER_ERROR;
        class SetUsername implements SetParameter {
            @Override
            public void set(PreparedStatement preparedStatement) throws Exception {
                preparedStatement.setString(1, userEntity.getUsername());
            }
        }

        class SetParam implements SetParameter {
            @Override
            public void set(PreparedStatement preparedStatement) throws Exception {
                preparedStatement.setString(1, userEntity.getUsername());
                preparedStatement.setString(2, userEntity.getMd5password());
                preparedStatement.setString(3, userEntity.getNickname());
                preparedStatement.setString(4, userEntity.getGender());
                preparedStatement.setString(5, userEntity.getIconUrl());
                preparedStatement.setDouble(6, userEntity.getLatitude());
                preparedStatement.setDouble(7, userEntity.getLongitude());
                preparedStatement.setString(8, userEntity.getIntro());
                preparedStatement.setDouble(9, userEntity.getRegTime());
            }
        }

        try {
            String sql = "select * from user where username=?";
            Select select = new Select();

            List list = select.selectRS(sql, new SetUsername());

            if (list.size() >= 1) {
                statusCode = Const.STATUS_REGISTER_ERROR;
                return statusCode;
            }

            sql = "insert into user" + "(" + col_username + ","
                    + col_md5password + "," + col_nickname + "," + col_gender
                    + "," + col_iconUrl + "," + col_latitude + ","
                    + col_longitude + "," + col_intro + "," + col_regTime
                    + ") values(?,?,?,?,?,?,?,?,?)";

            Modify modify = new Modify();
            int id = modify.exec(sql, new SetParam());
            if (id >= 1) {
                statusCode = Const.STATUS_OK;
            }
        } catch (Exception e) {
            statusCode = Const.STATUS_SERVER_ERROR;
            e.printStackTrace();
            Tools.writeException(e);
        }
        return statusCode;
    }
}
