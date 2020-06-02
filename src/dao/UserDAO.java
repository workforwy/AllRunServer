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
                public void set(PreparedStatement preparedStatement)
                        throws Exception {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, md5Password);

                }
            }
            String sql = "select * from user "
                    + "where " + col_username + "=? and " + col_md5password + "=? ";

            Select select = new Select();
            List list = select.selectRS(sql, new SetParam());

            if (list.size() >= 1) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Tools.writeException(e);
        }
        return false;
    }

    public UserEntity queryDetailByUsername(final String username, final String md5Password) {
        UserEntity userEntity = new UserEntity();
        try {
            class SetParam implements SetParameter {
                public void set(PreparedStatement preparedStatement)
                        throws Exception {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, md5Password);

                }
            }
            String sql = "select * from user "
                    + "where " + col_username + "=? and " + col_md5password + "=? ";

            Select select = new Select();
            List list = select.selectRS(sql, new SetParam());

            int i = 0;
            String id = String.valueOf(((Map) list.get(i)).get(col_id));

            String md5password = String.valueOf(((Map) list.get(i))
                    .get(col_md5password));
            String nickname = String.valueOf(((Map) list.get(i))
                    .get(col_nickname));
            String gender = String.valueOf(((Map) list.get(i))
                    .get(col_gender));
            String iconUrl = String.valueOf(((Map) list.get(i))
                    .get(col_iconUrl));
            String latitude = String.valueOf(((Map) list.get(i))
                    .get(col_latitude));
            String longitude = String.valueOf(((Map) list.get(i))
                    .get(col_longitude));
            String intro = String.valueOf(((Map) list.get(i))
                    .get(col_intro));
            String regTime = String.valueOf(((Map) list.get(i))
                    .get(col_regTime));

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

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Tools.writeException(e);
        }
        return userEntity;
    }

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
                String id = String.valueOf(((Map) list.get(i)).get(col_id));
                String username = String.valueOf(((Map) list.get(i))
                        .get(col_username));
                String md5password = String.valueOf(((Map) list.get(i))
                        .get(col_md5password));
                String nickname = String.valueOf(((Map) list.get(i))
                        .get(col_nickname));
                String gender = String.valueOf(((Map) list.get(i))
                        .get(col_gender));
                String iconUrl = String.valueOf(((Map) list.get(i))
                        .get(col_iconUrl));
                String latitude = String.valueOf(((Map) list.get(i))
                        .get(col_latitude));
                String longitude = String.valueOf(((Map) list.get(i))
                        .get(col_longitude));
                String intro = String.valueOf(((Map) list.get(i))
                        .get(col_intro));
                String regTime = String.valueOf(((Map) list.get(i))
                        .get(col_regTime));

                UserEntity userEntity = new UserEntity();
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

                userEntitys[i] = userEntity;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Tools.writeException(e);
        }

        return userEntitys;
    }

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
                String id = String.valueOf(((Map) list.get(i)).get(col_id));
                String username = String.valueOf(((Map) list.get(i))
                        .get(col_username));
                String md5password = String.valueOf(((Map) list.get(i))
                        .get(col_md5password));
                String nickname = String.valueOf(((Map) list.get(i))
                        .get(col_nickname));
                String gender = String.valueOf(((Map) list.get(i))
                        .get(col_gender));
                String iconUrl = String.valueOf(((Map) list.get(i))
                        .get(col_iconUrl));
                String latitude = String.valueOf(((Map) list.get(i))
                        .get(col_latitude));
                String longitude = String.valueOf(((Map) list.get(i))
                        .get(col_longitude));
                String intro = String.valueOf(((Map) list.get(i))
                        .get(col_intro));
                String regTime = String.valueOf(((Map) list.get(i))
                        .get(col_regTime));
                if (Tools.isNull(latitude)) {
                    latitude = "0";
                }
                if (Tools.isNull(longitude)) {
                    longitude = "0";
                }
                if (Tools.isNull(regTime)) {
                    regTime = "0";
                }

                UserEntity userEntity = new UserEntity();
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

                userEntitys[i] = userEntity;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Tools.writeException(e);
        }

        return userEntitys;
    }

    public int register(final UserEntity userEntity) {
        int statusCode = Const.STATUS_SERVER_ERROR;
        class SetUsername implements SetParameter {
            public void set(PreparedStatement preparedStatement)
                    throws Exception {
                preparedStatement.setString(1, userEntity.getUsername());
            }
        }

        class SetParam implements SetParameter {
            public void set(PreparedStatement preparedStatement)
                    throws Exception {
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
            // TODO Auto-generated catch block
            statusCode = Const.STATUS_SERVER_ERROR;
            e.printStackTrace();
            Tools.writeException(e);
        }

        return statusCode;
    }
}
