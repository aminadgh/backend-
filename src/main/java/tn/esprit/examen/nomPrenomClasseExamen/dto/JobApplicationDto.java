package tn.esprit.examen.nomPrenomClasseExamen.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import tn.esprit.examen.nomPrenomClasseExamen.entities.JobApplication;
import tn.esprit.examen.nomPrenomClasseExamen.entities.JobApplicationStatus;
import tn.esprit.examen.nomPrenomClasseExamen.entities.JobOffer;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Subscriber;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {

    private Long idApplication;

    private LocalDateTime applicationDate;
    @NotNull(message = "Job Application Status cannot be null")
    private JobApplicationStatus jobApplicationStatus;
    @JsonManagedReference
    private Long jobOfferId;
    @JsonManagedReference
    private Subscriber applicant;

    // Méthode statique pour convertir une entité en DTO
    public static JobApplicationDto fromEntity(JobApplication jobApplication) {
        return new JobApplicationDto(
                jobApplication.getIdApplication(),
                jobApplication.getApplicationDate(),
                jobApplication.getJobApplicationStatus(),
                jobApplication.getJobOffer() != null ? jobApplication.getJobOffer().getIdJobOffer() : null,
                jobApplication.getApplicant() // Include the full Subscriber object
        );
    }

    // Méthode statique pour convertir un DTO en entité
    public static JobApplication toEntity(JobApplicationDto dto, JobOffer jobOffer, Subscriber applicant) {
        return new JobApplication(
                dto.getIdApplication(),
                dto.getApplicationDate(),
                dto.getJobApplicationStatus(),
                jobOffer,
                dto.getApplicant() // Include the full Subscriber object
        );
    }
}
