package dao.operate;

import java.sql.PreparedStatement;

public interface SetParameter {
    /**
     * ·â×° preparedStatement ²éÑ¯Óï¾ä
     *
     * @param preparedStatement
     * @throws Exception
     */
    public void set(PreparedStatement preparedStatement) throws Exception;

}
