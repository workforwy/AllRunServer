package dao.operate;

import java.sql.PreparedStatement;

public interface SetParameter {
    /**
     * ��װ preparedStatement ��ѯ���
     *
     * @param preparedStatement
     * @throws Exception
     */
    public void set(PreparedStatement preparedStatement) throws Exception;

}
