package hibernate.entities;

import enums.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tasks")
public class HibernateTask {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "deadline")
    private Date deadline;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TaskType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId")
    private HibernateEmployee employee;
}
