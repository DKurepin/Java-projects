package jdbc.entities;

import enums.TaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class JdbcTask {
    private long id;
    private String name;
    private Date deadline;
    private String description;
    private TaskType type;
    private long employeeId;
}
