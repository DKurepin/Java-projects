package myBatis.mappers;

import myBatis.entities.MyBatisTask;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TaskMapper {
    @Insert("INSERT INTO Tasks (id, name, deadline, description, type, employeeId) VALUES (#{id}, #{name}, #{deadline}, #{description}, #{type}, #{employeeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(MyBatisTask task);

    @Delete("DELETE FROM Tasks WHERE id = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM Tasks")
    void deleteAll();

    @Update("UPDATE Tasks SET name = #{name}, deadline = #{deadline}, description = #{description}, type = #{type}, employeeId = #{employeeId} WHERE id = #{id}")
    void update(MyBatisTask task);

    @Select("SELECT * FROM Tasks WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "description", column = "description"),
            @Result(property = "type", column = "type"),
            @Result(property = "employeeId", column = "employeeId")
    })
    MyBatisTask getById(long id);

    @Select("SELECT * FROM Tasks")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "description", column = "description"),
            @Result(property = "type", column = "type"),
            @Result(property = "employeeId", column = "employeeId")
    })
    List<MyBatisTask> getAll();

    @Select("SELECT * FROM Tasks WHERE employeeId = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "description", column = "description"),
            @Result(property = "type", column = "type"),
            @Result(property = "employeeId", column = "employeeId")
    })
    List<MyBatisTask> getAllByEmployeeId(long id);
}
