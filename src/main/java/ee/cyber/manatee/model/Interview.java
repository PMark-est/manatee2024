package ee.cyber.manatee.model;

import ee.cyber.manatee.statemachine.ApplicationState;
import ee.cyber.manatee.statemachine.InterviewType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Application application;

    @NotNull
    @Enumerated
    private InterviewType interviewType;

    @NotNull
    private OffsetDateTime interviewTime;

    @NotBlank
    private String interviewerName;
}
