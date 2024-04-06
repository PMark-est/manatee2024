package ee.cyber.manatee;

import ee.cyber.manatee.model.Application;
import ee.cyber.manatee.model.Candidate;
import ee.cyber.manatee.model.Interview;
import ee.cyber.manatee.service.ApplicationService;
import ee.cyber.manatee.service.InterviewService;
import ee.cyber.manatee.statemachine.ApplicationState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import static ee.cyber.manatee.statemachine.InterviewType.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Transactional
@Component
public class DatabaseSeedingCommandLineRunner implements CommandLineRunner {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    InterviewService interviewService;

    private OffsetDateTime generateRandomDate(int hour, int minute){
        Random random = new Random();
        int year = 2024; // The desired year
        int month = random.nextInt(3)+1;
        int day = random.nextInt(31)+1;
        if(month == 2 && day > 29 && year%4==0){
            day = 29;
        } else if (month == 2 && day > 28){
            day = 28;
        }
        return OffsetDateTime.of(year, month, day, hour, minute, 0, 0, ZoneOffset.UTC);
    }

    @Override
    public void run(String... args) throws Exception {
        val firstNames = List.of("Mari", "Jüri", "Martin", "Tarmo", "Taavi", "Jaan");
        val lastNames = List.of("Maasikas", "Krüger", "Tamm", "Oja", "Libe", "Pohl");
        val interviewTypes = List.of(INFORMAL, INFORMAL, BEHAVIOURAL, FINAL, TECHNICAL, BEHAVIOURAL);
        val interviewTimes = List.of(
                generateRandomDate(12, 30),
                generateRandomDate(15,0),
                generateRandomDate(11, 0),
                generateRandomDate(11, 15),
                generateRandomDate(11, 30),
                generateRandomDate(11, 45)
        );
        val inteviewerNames = List.of(
                "Mark Kuusk", "Piret Sirel"
        );
        for (int i = 0; i < 6; i++) {
            val newCandidate = Candidate.builder().firstName(firstNames.get(i)).lastName(lastNames.get(i)).build();
            val newApplication = Application.builder().candidate(newCandidate).build();
            val savedApplication = applicationService.insertApplication(newApplication);
            if (i % 3 == 0) interviewService.scheduleInterview(savedApplication, interviewTypes.get(i),
                    interviewTimes.get(i), inteviewerNames.get(i%2));
        }
    }
}
