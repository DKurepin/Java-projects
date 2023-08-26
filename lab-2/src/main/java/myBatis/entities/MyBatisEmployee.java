package myBatis.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBatisEmployee {
    private long id;
    private String name;
    private Date date;
}
