import java.rmi.Naming;
import java.util.*;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        String getEmployee = "rmi://localhost:5000/getEmployee";
        Set<String> selectColumns;
        Map<String, String> whereClauses;
        Map<String, String> updateAttrributes;
        Map<String, String> row;
        EmployeeInterface employeeObject = (EmployeeInterface) Naming.lookup(getEmployee);
        while (true) {
            try {
                printMenu();
                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 0:
                        return;
                    case 1:
                        System.out.println(employeeObject.selectAll());
                        break;
                    case 2:
                        System.out.println("Enter Columns to Select");
                        selectColumns = Arrays.stream(sc.nextLine().split(",")).collect(Collectors.toSet());
                        System.out.println("Enter where conditions(key1=value1,key2=value2)..");
                        whereClauses = Arrays.stream(sc.nextLine().split(","))
                                .map(condition -> condition.split("="))
                                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
                        System.out.println(employeeObject.selectAllWithWhere(selectColumns, whereClauses));
                        break;
                    case 3:
                        printInsertDeatils();
                        while (true) {
                            String inprow = sc.nextLine();
                            if (inprow.equals("!")) break;
                            row = prepareRow(inprow.split(","));
                            System.out.println(employeeObject.insert(row) > 0 ? "Row Inserted Sucessfully" : "Row Not Inserted");
                        }
                        break;
                    case 4:
                        System.out.println("Enter Updates (key1=value1,key2=value2)..");
                        updateAttrributes = Arrays.stream(sc.nextLine().split(","))
                                .map(condition -> condition.split("="))
                                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
                        System.out.println("Enter where conditions(key1=value1,key2=value2)..");
                        whereClauses = Arrays.stream(sc.nextLine().split(","))
                                .map(condition -> condition.split("="))
                                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
                        System.out.println(employeeObject.update(updateAttrributes, whereClauses) > 0 ? "Rows Updated Sucessfully" : "Rows Not Updated");
                        break;
                    case 5:
                        System.out.println("Enter where conditions(key1=value1,key2=value2)..");
                        whereClauses = Arrays.stream(sc.nextLine().split(","))
                                .map(condition -> condition.split("="))
                                .collect(Collectors.toMap(str -> str[0], str -> str[1]));
                        System.out.println(employeeObject.delete(whereClauses) > 0 ? "Rows Deleted Sucessfully" : "Rows Not Deleted");
                        break;
                    default:
                        System.out.println("-Invalid Input-");
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }
        }

    }

    private static boolean isEmpty(String s) {
        return s.isEmpty() || s.equals(" ");
    }

    private static void printInsertDeatils() {
        System.out.println("Enter Details in Order(Id,FirstName,LastName,Email,Department,Position)");
        System.out.println("Enter space if you don't want to insert some value");
        System.out.println("Note : Primary Key is Must");
        System.out.println("Enter ! to break..");
        System.out.println("Enter Values....");
    }

    private static Map<String, String> prepareRow(String[] splittedrow) {
        Map<String, String> row = new HashMap<>();
        if (!isEmpty(splittedrow[0])) row.put("Id", splittedrow[0]);
        if (!isEmpty(splittedrow[1])) row.put("firstName", splittedrow[1]);
        if (!isEmpty(splittedrow[2])) row.put("lastName", splittedrow[2]);
        if (!isEmpty(splittedrow[3])) row.put("email", splittedrow[3]);
        if (!isEmpty(splittedrow[4])) row.put("department", splittedrow[4]);
        if (!isEmpty(splittedrow[5])) row.put("position", splittedrow[5]);
        return row;
    }

    private static void printMenu(){
        System.out.println("\n-- MENU --");
        System.out.println("1. SELECT");
        System.out.println("2. SELECT with WHERE");
        System.out.println("3. INSERT");
        System.out.println("4. UPDATE");
        System.out.println("5. DELETE");
        System.out.println("0. EXIT\n");
    }
}
