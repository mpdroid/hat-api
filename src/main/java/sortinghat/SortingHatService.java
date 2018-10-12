package sortinghat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
class SortingHatService {

    @Autowired
    Ruminator ruminator;

    List<Roster> rosters;

    SortingHatService() {
        System.out.println("Initializing rosters************");
        List<Student> students = new ArrayList<Student>();

        Student harry = Student
                .builder()
                .id(1L)
                .firstName("Harry")
                .lastName("Potter")
                .nameSuffix("")
                .hairColor("black")
                .netWorth("1000000")
                .gender("male")
                .dob(getDate(1980, 6, 30))
                .house("Gryffindor")
                .howAssigned("hair color: black")
                .build();
        students.add(harry);

        Student draco = Student
                .builder()
                .id(2L)
                .firstName("Draco")
                .lastName("Malfoy")
                .nameSuffix("")
                .hairColor("silver")
                .netWorth("1 billion")
                .gender("male")
                .dob(getDate(1980, 1, 30))
                .house("Slytherin")
                .howAssigned("hair color: silver")
                .build();
        students.add(draco);

        rosters = new ArrayList<>();
        Roster roster = Roster
                .builder()
                .id(1L)
                .submitDate(new Date())
                .hasTheHatDecided(true)
                .students(students)
                .build();
        rosters.add(roster);

    }

    List<Roster> getRosters() {
        rosters.sort(new Comparator<Roster>() {
            @Override
            public int compare(Roster o1, Roster o2) {
                return o2.getSubmitDate().compareTo(o1.getSubmitDate());
            }
        });
        return rosters;
    }

    Optional<Roster> getRoster(Long rosterId) {
        return rosters.stream().filter(roster -> roster.getId().equals(rosterId)).findFirst();
    }

    Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    String getSampleTxt() {
        StringBuffer buf = new StringBuffer("id," +
                "firstName, " +
                "lastName, " +
                "nameSuffix, " +
                "gender, " +
                "dob, " +
                "netWorth, " +
                "hairColor");
        for (Student student : rosters.get(0).getStudents()) {
            buf.append("\n");
            buf.append(student.getId());
            buf.append(", ");
            buf.append(student.getFirstName());
            buf.append(", ");
            buf.append(student.getLastName());
            buf.append(", ");
            buf.append(student.getNameSuffix());
            buf.append(", ");
            buf.append(student.getGender());
            buf.append(", ");
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dob = simpleDateFormat.format(student.getDob());
            buf.append(dob);
            buf.append(", ");
            buf.append(student.getNetWorth());
            buf.append(", ");
            buf.append(student.getHairColor());
        }
        return buf.toString();
    }

    void addRoster(MultipartFile file) {
        try {

            List<Student> pledges = readInPledges(file);
            long nextId = rosters.size();
            Roster roster = Roster.builder()
                    .id(nextId)
                    .submitDate(new Date())
                    .students(pledges)
                    .build();
            rosters.add(roster);
            ruminator.ruminate(roster);
            System.out.println("Rumination thread started");
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidFileException(e.getMessage());
        }
    }

    List<Student> readInPledges(MultipartFile file) throws IOException {
        InputStream in = file.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int lineNum = 0;
        String str;
        List<Student> pledges = new ArrayList<>();
        while ((str = br.readLine()) != null) {
            if (lineNum == 0) {
                lineNum++;
                continue;
            }

            String[] tokens = str.split(",");
            if (tokens.length < 7)
                continue;
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = sdf.parse(tokens[4]);
                Student student = Student.builder()
                        .id(new Long(lineNum))
                        .firstName(tokens[0].trim())
                        .lastName(tokens[1].trim())
                        .nameSuffix(tokens[2].trim())
                        .gender(tokens[3].trim())
                        .dob(dob)
                        .netWorth(tokens[5].trim())
                        .hairColor(tokens[6].trim())
                        .house("Unassigned")
                        .build();
                pledges.add(student);
            } catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            lineNum++;
        }

        if (pledges.size() == 0) {
            throw new InvalidFileException("No valid student records in roster.");
        }
        return pledges;

    }

    String getRosterAsTxt(Long id) {
        Optional<Roster> rosterOptional = rosters.stream().filter(o -> o.getId().equals(id)).findFirst();
        if (rosterOptional.isPresent()) {
            Roster roster = rosterOptional.get();
            StringBuffer buf = new StringBuffer(
                    "firstName, " +
                            "lastName, " +
                            "nameSuffix, " +
                            "gender, " +
                            "dob, " +
                            "netWorth, " +
                            "hairColor");
            for (Student student : roster.getStudents()) {
                buf.append("\n");
                buf.append(student.getFirstName());
                buf.append(", ");
                buf.append(student.getLastName());
                buf.append(", ");
                buf.append(student.getNameSuffix());
                buf.append(", ");
                buf.append(student.getGender());
                buf.append(", ");
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String dob = simpleDateFormat.format(student.getDob());
                buf.append(dob);
                buf.append(", ");
                buf.append(student.getNetWorth());
                buf.append(", ");
                buf.append(student.getHairColor());
            }
            return buf.toString();
        } else
            throw new NotFoundException("Roster " + id + " does not exist!");
    }

}
