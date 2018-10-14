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
    Long netWorth;
    int elvesOwned;
    int dementorsBattled;
    String hairColor;
    String house = "unassigned";
    String[] rulesFired;
    int randomGroup;

    public boolean notAssigned() {
        return house.equalsIgnoreCase("unassigned");
    }
}
