package sortinghat;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    Long id;
    String firstName;
    String lastName;
    String nameSuffix;
    String gender;
    Date dob;
    String netWorth;
    String hairColor;
    String house = "unassigned";
    String howAssigned;
}
