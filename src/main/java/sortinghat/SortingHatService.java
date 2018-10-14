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

    Random random;

    List<Roster> rosters;

    SortingHatService() {
        random = new Random();
        System.out.println("Initializing rosters************");
        List<Student> students = new ArrayList<Student>();

        Student harry = Student
                .builder()
                .id(1L)
                .firstName("Harry")
                .lastName("Potter")
                .nameSuffix("")
                .hairColor("black")
                .netWorth(1000000L)
                .gender("male")
                .dob(getDate(1980, 6, 30))
                .elvesOwned(0)
                .dementorsBattled(5)
                .house("Gryffindor")
                .rulesFired(new String[]{"hair color is black"})
                .randomGroup(random.nextInt(3))
                .build();
        students.add(harry);

        Student draco = Student
                .builder()
                .id(2L)
                .firstName("Draco")
                .lastName("Malfoy")
                .nameSuffix("")
                .hairColor("silver")
                .netWorth(1000000000L)
                .gender("male")
                .dob(getDate(1980, 1, 30))
                .elvesOwned(5)
                .dementorsBattled(0)
                .house("Slytherin")
                .rulesFired(new String[]{"hair color is silver"})
                .randomGroup(random.nextInt(3))
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

    Roster getRoster(Long rosterId) {

        System.out.println("Number of rosters outstanding: " + rosters.size());
        Optional<Roster> found = rosters.stream().filter(roster -> roster.getId().equals(rosterId)).findFirst();
        if (found.isPresent()) {
            Roster ret = found.get();
            if (ret.isHasTheHatDecided()) {
                rosters.removeIf(roster -> roster.getId().equals(rosterId));
                System.out.println("Number of rosters outstanding: " + rosters.size());
            }
            return ret;
        } else {
            throw new NotFoundException("Roster " + rosterId + " does not exist!");
        }

    }

    Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    Roster addRoster(MultipartFile file) {
        try {
            List<Student> pledges = readInPledges(file);
            long nextId = (new Date()).getTime();
            Roster roster = Roster.builder()
                    .id(nextId)
                    .submitDate(new Date())
                    .students(pledges)
                    .build();
            rosters.add(roster);
            ruminator.ruminate(roster);
            return roster;
        } catch (InvalidFileException ef) {
            throw ef;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Internal Hat problem. Try again later...");
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
            if (tokens.length < 9)
                continue;
            try {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dob = sdf.parse(tokens[4].trim());
                Long netWorth = Long.parseLong(tokens[5].trim());
                int elvesOwned = Integer.parseInt(tokens[7].trim());
                int dementorsBattled = Integer.parseInt(tokens[8].trim());
                Student student = Student.builder()
                        .id(new Long(lineNum))
                        .firstName(tokens[0].trim())
                        .lastName(tokens[1].trim())
                        .nameSuffix(tokens[2].trim())
                        .gender(tokens[3].trim())
                        .dob(dob)
                        .netWorth(netWorth)
                        .hairColor(tokens[6].trim())
                        .elvesOwned(elvesOwned)
                        .dementorsBattled(dementorsBattled)
                        .house("Unassigned")
                        .randomGroup(random.nextInt(3))
                        .rulesFired(new String[]{})
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
