package tn.esprit.examen.nomPrenomClasseExamen.Controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.examen.nomPrenomClasseExamen.dto.JobOfferDto;
import tn.esprit.examen.nomPrenomClasseExamen.entities.JobOffer;
import tn.esprit.examen.nomPrenomClasseExamen.services.JobOfferServices;

import java.util.List;

@RestController
@RequestMapping("/api/jobOffers")
@AllArgsConstructor
public class JobOfferController {

    private final JobOfferServices jobOfferService;
    private static final Logger logger = LoggerFactory.getLogger(JobOfferController.class);

    @GetMapping
    public ResponseEntity<List<JobOffer>> getAllJobOffers() {
        return ResponseEntity.ok(jobOfferService.getAllJobOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOffer> getJobOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(jobOfferService.getJobOfferById(id));
    }

    @PostMapping
    public ResponseEntity<?> createJobOffer(@RequestBody JobOfferDto jobOfferDto) {
        try {
            logger.info("📝 Requête POST reçue pour créer une offre: {}", jobOfferDto);
            JobOffer createdJobOffer = jobOfferService.createJobOffer(jobOfferDto);
            return ResponseEntity.ok(createdJobOffer);
        } catch (Exception e) {
            logger.error("❌ Erreur lors de la création de l'offre: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobOffer(@PathVariable Long id, @RequestBody JobOfferDto jobOfferDto) {
        try {
            logger.info("🔄 Requête PUT reçue pour mettre à jour l'offre avec ID: {}", id);
            JobOffer updatedJobOffer = jobOfferService.updateJobOffer(id, jobOfferDto);
            return ResponseEntity.ok(updatedJobOffer);
        } catch (Exception e) {
            logger.error("❌ Erreur lors de la mise à jour de l'offre: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erreur: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        logger.info("🗑 Requête DELETE reçue pour supprimer l'offre avec ID: {}", id);
        jobOfferService.deleteJobOffer(id);
        return ResponseEntity.noContent().build();
    }
}
