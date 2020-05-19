package dao;

import dao.operate.Modify;
import dao.operate.Select;
import dao.operate.SetParameter;
import entity.TraceEntity;
import util.Const;
import util.Tools;

import java.sql.PreparedStatement;
import java.util.*;


public class TraceDAO {
	private final static String table_topic = "trace";

	private final static String col_id = "id";
	private final static String col_sportId = "sportId";
	private final static String col_sportTime = "sportTime";
	private final static String col_latitude = "latitude";
	private final static String col_longitude = "longitude";

	public TraceEntity[] queryBySportId(final int sportId) {
		TraceEntity[] traceEntities= null;
		try {
			class SetSelectParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setInt(1,
							sportId);
				}
			}
			String sql = "select " + col_id + "," 
					+ col_sportTime + "," + col_latitude+ "," + col_longitude
					
					+ " from trace where "+col_sportId+"=? order by id asc";

			Select select = new Select();
			List list = select.selectRS(sql,new SetSelectParam());
			traceEntities = new TraceEntity[list.size()];
			for (int i = 0; i < list.size(); i++) {
				String id = String.valueOf(((Map) list.get(i)).get(col_id));			
				String sportTime = String.valueOf(((Map) list.get(i))
						.get(col_sportTime));		
				String latitude = String.valueOf(((Map) list.get(i))
						.get(col_latitude));
				String longitude = String.valueOf(((Map) list.get(i))
						.get(col_longitude));

				TraceEntity traceEntity = new TraceEntity();
				traceEntity.setId(Integer.parseInt(id));
				traceEntity.setSportId(sportId);
				traceEntity.setSportTime(Double.parseDouble(sportTime));
				traceEntity.setLatitude(Double.parseDouble(latitude));
				traceEntity.setLongitude(Double.parseDouble(longitude));

				traceEntities[i] = traceEntity;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Tools.writeException(e);
		}
		return traceEntities;
	}

	public int addTrace(final TraceEntity traceEntity,final int sportId) {
		int statusCode = Const.STATUS_SERVER_ERROR;
		try {
			String sql = "insert into trace" + "(" + col_sportId + ","
					+ col_sportTime +","+ col_latitude +","+ col_longitude+ ") values(?,?,?,?)";
			class SetParam implements SetParameter {
				public void set(PreparedStatement preparedStatement)
						throws Exception {
					preparedStatement.setInt(1, sportId);
					preparedStatement.setDouble(2, traceEntity.getSportTime());
					preparedStatement.setDouble(3, traceEntity.getLatitude());
					preparedStatement.setDouble(4, traceEntity.getLongitude());
	
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
