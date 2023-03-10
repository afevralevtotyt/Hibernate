import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;


public class EmployeeDAOImplTest {

    private final EmployeeDAO employeeDAO = new EmployeeDAOImpl();

    @Test
    public void shouldCreateNewEmployee(){
        City bangkok = new City("Bangkok");
        Employee ilya = new Employee("Ilya", "Denisov", "male", 26,
               bangkok);

        employeeDAO.createEmployee(ilya);
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Employee actualEmployee = session.find(Employee.class, ilya.getId());
        assertNotNull(actualEmployee);
        assertNotNull(actualEmployee.getCity());
        assertEquals(actualEmployee.getFirstName(), ilya.getFirstName());
        assertEquals(actualEmployee.getLastName(), ilya.getLastName());
        assertEquals(actualEmployee.getAge(), ilya.getAge());
        assertEquals(actualEmployee.getGender(), ilya.getGender());
        assertEquals(actualEmployee.getCity().getCityId(), ilya.getCity().getCityId());
        session.close();
    }
@Test
    public void shouldReturnCorrectObjectById(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Employee actualEmployee = session.createQuery("FROM Employee where id = 3", Employee.class).getSingleResult();
        Employee expectedEmployee = employeeDAO.readById(3);
        session.close();
        assertNotNull(expectedEmployee);
        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getAge(), actualEmployee.getAge());
        assertEquals(expectedEmployee.getGender(), actualEmployee.getGender());
        assertEquals(expectedEmployee.getCity().getCityId(), actualEmployee.getCity().getCityId());
    }
    @Test
    public void shouldCorrectlyUpdateObject(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        int id = session.createQuery("FROM Employee", Employee.class).list().get(0).getId();
        session.close();
        City city = new City("Petrograd");
        Employee expectedEmployee = new Employee(id,"Ovanes", "Sarigesyan", "male", 67,
                city);
        employeeDAO.updateEmployee(expectedEmployee);
        Session newSession = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Employee actualEmployee = newSession.find(Employee.class, id);
        newSession.close();
        assertNotNull(actualEmployee);
        assertNotNull(actualEmployee.getCity());
        assertEquals(actualEmployee.getFirstName(), expectedEmployee.getFirstName());
        assertEquals(actualEmployee.getLastName(), expectedEmployee.getLastName());
        assertEquals(actualEmployee.getAge(), expectedEmployee.getAge());
        assertEquals(actualEmployee.getGender(), expectedEmployee.getGender());
        assertEquals(actualEmployee.getCity().getCityId(), expectedEmployee.getCity().getCityId());

    }
    @Test
    public void shouldCorrectlyDeleteObject(){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employee> employeeList = session.createQuery("FROM Employee", Employee.class).list();
        Employee employee = employeeList.get(employeeList.size()-1);
        session.close();
        employeeDAO.delete(employee);
        Session newSession = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        assertNull(newSession.find(Employee.class, employee.getId()));
        newSession.close();
    }
    @Test
    public void shouldReturnListOfObjects(){
        List<Employee> expectedEmployeeList = employeeDAO.readAll();
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Employee> actualEmployeeList = session.createQuery("FROM Employee",Employee.class ).list();
        session.close();
        assertTrue(expectedEmployeeList.size()==actualEmployeeList.size());
    }

}
