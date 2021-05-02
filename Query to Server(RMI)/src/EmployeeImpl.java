import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

public class EmployeeImpl extends UnicastRemoteObject implements EmployeeInterface, Serializable {
    private transient Statement statement;
    private static final String EMPLOYEE = "Employee";

    public EmployeeImpl() throws RemoteException, SQLException, ClassNotFoundException {
        statement = PostgressConfig.getStatement();
    }

    @Override
    public int insert(Map<String, String> insertValues) throws RemoteException, SQLException {
        String query = new Query.Builder(EMPLOYEE, QueryType.INSERT)
                .addInsertValues(insertValues)
                .build()
                .getQuery();
        return statement.executeUpdate(query);
    }

    @Override
    public int update(Map<String, String> updateAttributes, Map<String, String> whereClauses) throws RemoteException, SQLException {
        String query = new Query.Builder(EMPLOYEE, QueryType.UPDATE)
                .addUpdateAttributes(updateAttributes)
                .addWhereClauses(whereClauses)
                .build()
                .getQuery();
        return statement.executeUpdate(query);
    }

    @Override
    public int delete(Map<String, String> whereClauses) throws RemoteException, SQLException {
        String query = new Query.Builder(EMPLOYEE, QueryType.DELETE)
                .addWhereClauses(whereClauses)
                .build()
                .getQuery();
        return statement.executeUpdate(query);
    }

    @Override
    public String selectAll() throws RemoteException, SQLException {
        String query = new Query.Builder(EMPLOYEE, QueryType.SELECT)
                .build()
                .getQuery();
        return DbPrinter.printResultSet(statement.executeQuery(query));
    }

    @Override
    public String selectAllWithWhere(Set<String> selectColumns, Map<String, String> whereClauses) throws RemoteException, SQLException {
        String query = new Query.Builder(EMPLOYEE, QueryType.SELECT)
                .addSelectAttributes(selectColumns)
                .addWhereClauses(whereClauses)
                .build()
                .getQuery();
        return DbPrinter.printResultSet(statement.executeQuery(query));
    }
}
