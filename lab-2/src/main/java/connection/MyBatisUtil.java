package connection;



import lombok.Getter;
import myBatis.mappers.EmployeeMapper;
import myBatis.mappers.TaskMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

public class MyBatisUtil {
    @Getter
    private static SqlSessionFactory sessionFactory = buildSessionFactory();

    private static SqlSessionFactory buildSessionFactory() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.cfg.xml");
            sessionFactory = new SqlSessionFactoryBuilder().build(reader);

            sessionFactory.getConfiguration().addMapper(EmployeeMapper.class);
            sessionFactory.getConfiguration().addMapper(TaskMapper.class);
        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed!" + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }

    public static SqlSession createSession() {
        return sessionFactory.openSession();
    }
}
