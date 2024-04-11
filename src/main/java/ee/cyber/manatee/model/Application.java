package ee.cyber.manatee.model;


import java.time.OffsetDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import ee.cyber.manatee.statemachine.ApplicationState;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationState applicationState;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Interview> interviews;

    public void addInterview(Interview interview){
        interviews.add(interview);
    }

    @NotNull
    private OffsetDateTime updatedOn;
}
