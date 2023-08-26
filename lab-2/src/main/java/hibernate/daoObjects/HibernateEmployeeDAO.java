package hibernate.daoObjects;

import connection.HibernateUtil;
import hibernate.entities.HibernateEmployee;
import org.hibernate.Session;

import java.util.List;

public class HibernateEmployeeDAO {

    public HibernateEmployee save(HibernateEmployee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(employee);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    public void deleteById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(getById(id));
        session.getTransaction().commit();
        session.close();
    }

    public void deleteByEntity(HibernateEmployee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(employee);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM HibernateEmployee").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public HibernateEmployee update(HibernateEmployee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(employee);
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    public HibernateEmployee getById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        HibernateEmployee result = session.get(HibernateEmployee.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<HibernateEmployee> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List result = session.createQuery("FROM HibernateEmployee").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
