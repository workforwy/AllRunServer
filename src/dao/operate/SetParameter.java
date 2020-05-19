package dao.operate;

import java.sql.PreparedStatement;

public interface SetParameter {
	public void set(PreparedStatement preparedStatement) throws Exception;

}
