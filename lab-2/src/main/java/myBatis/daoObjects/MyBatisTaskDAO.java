package myBatis.daoObjects;

import connection.MyBatisUtil;
import myBatis.entities.MyBatisTask;
import myBatis.mappers.TaskMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class MyBatisTaskDAO {

    public MyBatisTask save(MyBatisTask task) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(TaskMapper.class).insert(task);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return task;
    }

    public void deleteById(long id) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(TaskMapper.class).deleteById(id);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public void deleteByEntity(MyBatisTask task) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(TaskMapper.class).deleteById(task.getId());
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public void deleteAll() {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(TaskMapper.class).deleteAll();
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public MyBatisTask update(MyBatisTask task) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(TaskMapper.class).update(task);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return task;
    }

    public MyBatisTask getById(long id) {
        MyBatisTask task = null;
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            task = session.getMapper(TaskMapper.class).getById(id);
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return task;
    }

    public List<MyBatisTask> getAll() {
        List<MyBatisTask> tasks = new ArrayList<>();
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            tasks = session.getMapper(TaskMapper.class).getAll();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return tasks;
    }

    public List<MyBatisTask> getAllByEmployeeId(long employeeId) {
        List<MyBatisTask> tasks = new ArrayList<>();
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            tasks = session.getMapper(TaskMapper.class).getAllByEmployeeId(employeeId);
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return tasks;
    }
}
