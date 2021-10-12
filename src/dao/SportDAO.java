package dao;

import dao.operate.Modify;
import dao.operate.Select;
import dao.operate.SetParameter;
import entity.SportEntity;
import util.Tools;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SportDAO {
    private final static String table_topic = "sport";

    private final static String col_id = "id";
    private final static String col_sportType = "sportType";
    private final static String col_username = "username";
    private final static String col_uuid = "uuid";

    public SportEntity[] queryNearby(final double latitude, final double longitude) {
        SportEntity[] sportEntitys = null;
        try {

            String sql = "select " + col_id + "," + col_sportType + "," + col_username + " from sport order by id desc";
            Select select = new Select();
            List list = select.selectRS(sql);
            sportEntitys = new SportEntity[list.size()];
            for (int i = 0; i < list.size(); i++) {
                String id = String.valueOf(((Map) list.get(i)).get(col_id));
                String username = String.valueOf(((Map) list.get(i)) .get(col_username));
                String sportType = String.valueOf(((Map) list.get(i)) .get(col_sportType));

                SportEntity sportEntity = new SportEntity();
                sportEntity.setId(Integer.parseInt(id));
                sportEntity.setUsername(username);
                sportEntity.setSportType(sportType);

                sportEntitys[i] = sportEntity;

            }
        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return sportEntitys;
    }

    /**
     * @param sportEntity
     * @return ���� -1��ʾʧ��
     */
    public int addSport(final SportEntity sportEntity) {
        int sportId = -1;
        final String uuid = UUID.randomUUID().toString();
        try {
            String sql = "insert into sport" + "(" + col_username + "," + col_sportType + "," + col_uuid + ") values(?,?,?)";
            class SetParam implements SetParameter {
                @Override
                public void set(PreparedStatement preparedStatement) throws Exception {
                    preparedStatement.setString(1, sportEntity.getUsername());
                    preparedStatement.setString(2, sportEntity.getSportType());
                    preparedStatement.setString(3, uuid);
                }
            }

            Modify modify = new Modify();
            int count = modify.exec(sql, new SetParam());
            if (count >= 1) {
                try {
                    class SetSelectParam implements SetParameter {
                        @Override
                        public void set(PreparedStatement preparedStatement) throws Exception {
                            preparedStatement.setString(1, sportEntity.getUsername());
                            preparedStatement.setString(2, uuid);
                        }
                    }
                    sql = "select id from sport " + "where " + col_username + "=? and " + col_uuid + "=? ";

                    Select select = new Select();
                    List list = select.selectRS(sql, new SetSelectParam());
                    sportId = Integer.parseInt(String.valueOf(((Map) list.get(0)).get(col_id)));

                } catch (Exception e) {
                    e.printStackTrace();
                    Tools.writeException(e);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Tools.writeException(e);
        }
        return sportId;
    }
}
