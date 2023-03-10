import java.util.List;

public class Application {

    public static void main(String[] args) {

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        CityDAO cityDAO = new CityDAOImpl();

        City kaliningrad = new City(4, "Kaliningrad");


        Employee ilya = new Employee("Ilya", "Denisov", "male", 26,
                new City("Bagnkok"));

        employeeDAO.createEmployee(ilya);

        System.out.println(employeeDAO.readById(1));

        Employee elena = new Employee(104, "Elena", "Holodcova", "female", 23,  kaliningrad);

        employeeDAO.updateEmployee(elena);

       /* cityDAO.delete(new City(99, "Velikiy Novgorod"));*/

        List<Employee> employeeList = employeeDAO.readAll();

        /*employeeList.stream().forEach(System.out::println);
        employeeDAO.delete(fedor);*/

        List<Employee> employeeListAfterDelete = employeeDAO.readAll();

        employeeListAfterDelete.stream().forEach(System.out::println);

    }

}
