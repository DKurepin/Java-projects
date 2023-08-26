package myBatis.mappers;

import myBatis.entities.MyBatisEmployee;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface EmployeeMapper {
    @Insert("INSERT INTO Employees (id, name, dob) VALUES (#{id}, #{name}, #{date})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MyBatisEmployee employee);

    @Delete("DELETE FROM Employees WHERE id = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM Employees")
    void deleteAll();


    @Update("UPDATE Employees SET name = #{name}, dob = #{date} WHERE id = #{id}")
    void update(MyBatisEmployee employee);

    @Select("SELECT * FROM Employees WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "dob")
    })
    MyBatisEmployee getById(long id);

    @Select("SELECT * FROM Employees")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "date", column = "dob")
    })
    List<MyBatisEmployee> getAll();
}
