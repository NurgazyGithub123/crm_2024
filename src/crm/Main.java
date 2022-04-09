package crm;

import crm.dao.DaoFactory;
import crm.dao.ManagerDao;
import crm.model.Manager;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Manager manager = new Manager();

        manager.setFirst_name("Nurgazy");
        manager.setLast_name("Dykanbaev");
        manager.setEmail("nurgazy_dk@mail.ru");
        manager.setPhone_number("+9967700007111");
        manager.setSalary(50000.0);
        manager.setDob(LocalDate.parse("1984-07-22"));

        System.out.println("Manager " + manager);
        ManagerDao managerDao = DaoFactory.getManagerDaoSQL();
        managerDao.save(manager);

    }
}
