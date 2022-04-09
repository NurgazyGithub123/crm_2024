package crm.dao;

import crm.dao.ManagerDao;
import crm.dao.impl.ManagerDaoImpl;

public abstract class DaoFactory {

    static {
        try {
            System.out.println("Loading driver.....");
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ManagerDao getManagerDaoSQL(){
        return new ManagerDaoImpl();
    }


}
