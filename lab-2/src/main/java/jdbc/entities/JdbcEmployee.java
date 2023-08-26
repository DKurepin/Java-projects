package jdbc.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class JdbcEmployee {

    private long id;
    private String name;
    private Date date;

}
