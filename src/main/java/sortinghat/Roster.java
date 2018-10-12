package sortinghat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Roster {
    private Long id;
    private Date submitDate;
    private boolean hasTheHatDecided;
    private List<Student> students;
}
