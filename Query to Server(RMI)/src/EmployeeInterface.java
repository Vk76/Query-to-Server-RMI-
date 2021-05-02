import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

interface EmployeeInterface extends Remote {

    // DML
    int insert(Map<String, String> insertValues) throws RemoteException, SQLException;

    int update(Map<String, String> updateAttributes, Map<String, String> whereClauses) throws RemoteException, SQLException;

    int delete(Map<String, String> whereClauses) throws RemoteException, SQLException;

    // DQL
    String selectAll() throws RemoteException, SQLException;

    String selectAllWithWhere(Set<String> selectColumns, Map<String, String> whereClauses) throws RemoteException, SQLException;
}