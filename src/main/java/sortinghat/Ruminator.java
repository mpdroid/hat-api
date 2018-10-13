package sortinghat;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Ruminator {

    @Autowired
    private KieContainer kieContainer;


    @Async
    public void ruminate(Roster roster) {

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        roster.getStudents().stream().forEach(student -> {

            KieSession kieSession = kieContainer.newKieSession();
            kieSession.insert(student);
            kieSession.fireAllRules();
            kieSession.dispose();
            student.setHowAssigned("rule");
        });
        roster.setHasTheHatDecided(true);
    }

}
