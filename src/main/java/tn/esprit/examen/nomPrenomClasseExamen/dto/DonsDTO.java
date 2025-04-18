package tn.esprit.examen.nomPrenomClasseExamen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Donation;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Dons;
import tn.esprit.examen.nomPrenomClasseExamen.entities.DonsDeliveryMethod;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Subscriber;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonsDTO implements Serializable {
    private Long idDons;
    private String nomDoneur;
    private String prenomDoneur;
    private int quantite;
    private LocalDateTime donationDate;
    private LocalDateTime scheduledDateTime;
    private boolean associationValidated;
    private String donorNote;
    private DonsDeliveryMethod deliveryMethod;
    private String donorAddress; // Assuming you're keeping this as address for pick-up

    private Long donationId; // Only the ID
    private Long subscriberId;
    // 🔹 Convert DTO to Entity
    public Dons toDons() {
        Dons dons = new Dons();
        dons.setIdDons(this.idDons);
        dons.setNomDoneur(this.nomDoneur);
        dons.setPrenomDoneur(this.prenomDoneur);
        dons.setQuantite(this.quantite);
        dons.setDonationDate(this.donationDate);
        dons.setScheduledDateTime(this.scheduledDateTime);
        dons.setAssociationValidated(this.associationValidated);
        dons.setDonorNote(this.donorNote);
        dons.setDeliveryMethod(this.deliveryMethod);
        dons.setDonorAddress(this.donorAddress);
        if (this.donationId != null) {
            Donation donation = new Donation();
            donation.setIdDonation(this.donationId);
            dons.setDonation(donation);
        }

        if (this.subscriberId != null) {
            Subscriber subscriber = new Subscriber();
            subscriber.setIdUser(this.subscriberId);
            dons.setSubscriberDons(subscriber);
        }

        return dons;
    }

    // 🔹 Convert Entity to DTO
    public static DonsDTO fromDons(Dons dons) {
        return new DonsDTO(
                dons.getIdDons(),
                dons.getNomDoneur(),
                dons.getPrenomDoneur(),
                dons.getQuantite(),
                dons.getDonationDate(),
                dons.getScheduledDateTime(),
                dons.isAssociationValidated(),
                dons.getDonorNote(),
                dons.getDeliveryMethod(),
                dons.getDonorAddress(),
                dons.getDonation() != null ? dons.getDonation().getIdDonation() : null,
                dons.getSubscriberDons() != null ? dons.getSubscriberDons().getIdUser() : null
        );
    }
}
