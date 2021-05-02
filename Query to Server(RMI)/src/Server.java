import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String args[]) throws Exception {
        Registry registry = LocateRegistry.createRegistry(5000);
        EmployeeImpl employee = new EmployeeImpl();
        registry.bind("getEmployee", employee);
        System.out.println("Objects registered...");
    }
}
