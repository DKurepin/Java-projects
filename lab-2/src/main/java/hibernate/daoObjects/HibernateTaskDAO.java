package hibernate.daoObjects;

import hibernate.entities.HibernateEmployee;
import hibernate.entities.HibernateTask;

import connection.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class HibernateTaskDAO {

    public HibernateTask save(HibernateTask task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public void deleteById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(getById(id));
        session.getTransaction().commit();
        session.close();
    }

    public void deleteByEntity(HibernateTask task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(task);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM HibernateTask").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public HibernateTask update(HibernateTask task) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.merge(task);
        session.getTransaction().commit();
        session.close();
        return task;
    }

    public HibernateTask getById(long id) {
        HibernateTask task = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        task = session.get(HibernateTask.class, id);
        session.close();
        return task;
    }

    public List<HibernateTask> getAll() {
        List<HibernateTask> tasks = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        tasks = session.createQuery("FROM HibernateTask").list();
        session.close();
        return tasks;
    }

    public List<HibernateTask> getAllByEmployeeId(long id) {
        List<HibernateTask> tasks = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        HibernateEmployee employee = session.get(HibernateEmployee.class, id);
        session.beginTransaction();
        tasks = session.createQuery("FROM HibernateTask WHERE employee = :id").setParameter("id", employee).getResultList();
        session.close();
        return tasks;
    }
}
