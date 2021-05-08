//package fmi.pchmi.project.mySchedule.model.database.schedule;
//
//import lombok.*;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//import java.util.List;
//import java.util.Set;
//// TODO: DELETE SCHEDULE
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//@Table(name = "schedules")
//public class Schedule {
//    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(name="system-uuid", strategy = "uuid")
//    private String id;
//
//    private String name;
//    @ElementCollection(targetClass=String.class)
//    private Set<String> events;
//    private String groupId;
//    private String creatorId;
//    //related (userId is used only when we have private schedule)
//    private Boolean isPrivate;
//    private String userId;
//}
