package dao.operate;

import java.sql.PreparedStatement;

public interface SetParameter {
    /**
     * 封装 preparedStatement 查询语句
     *
     * @param preparedStatement
     * @throws Exception
     */
     void set(PreparedStatement preparedStatement) throws Exception;

}
