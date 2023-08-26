package myBatis.entities;

import enums.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBatisTask {
    private long id;
    private String name;
    private Date deadline;
    private String description;
    private TaskType type;
    private long employeeId;
}
