package myBatis.daoObjects;

import connection.MyBatisUtil;
import myBatis.mappers.EmployeeMapper;
import myBatis.entities.MyBatisEmployee;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;

public class MyBatisEmployeeDAO {

    public MyBatisEmployee save(MyBatisEmployee employee) {
         try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
                session.getMapper(EmployeeMapper.class).insert(employee);
                session.commit();
            } catch (Throwable exception) {
                System.err.println(exception);
         }
         return employee;
    }

    public void deleteById(long id) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(EmployeeMapper.class).deleteById(id);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public void deleteByEntity(MyBatisEmployee employee) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(EmployeeMapper.class).deleteById(employee.getId());
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public void deleteAll() {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(EmployeeMapper.class).deleteAll();
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
    }

    public MyBatisEmployee update(MyBatisEmployee employee) {
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            session.getMapper(EmployeeMapper.class).update(employee);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return employee;
    }

    public MyBatisEmployee getById(long id) {
        MyBatisEmployee result = new MyBatisEmployee();
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            result = session.getMapper(EmployeeMapper.class).getById(id);
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return result;
    }

    public List<MyBatisEmployee> getAll() {
        List<MyBatisEmployee> result = new ArrayList<>();
        try (SqlSession session = MyBatisUtil.getSessionFactory().openSession()) {
            result = session.getMapper(EmployeeMapper.class).getAll();
            session.commit();
        } catch (Throwable exception) {
            System.err.println(exception);
        }
        return result;
    }
}
