package tn.esprit.examen.nomPrenomClasseExamen.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Permet l'auto-incrémentation

    private Long idEvent;
    private String title;
    private String description;
    private Date dateTime;
    private String location;
    private TypeEvent typeEvent;
    private Date reservationDate;

    @ManyToOne
    @JsonBackReference
    Association association;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="event")
    private Set<Paiement> paiements;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Notification> notifications;

}
