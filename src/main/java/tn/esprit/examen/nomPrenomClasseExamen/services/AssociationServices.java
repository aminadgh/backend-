package tn.esprit.examen.nomPrenomClasseExamen.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.examen.nomPrenomClasseExamen.Repositories.AssociationRepository;
import tn.esprit.examen.nomPrenomClasseExamen.Repositories.SubscriberRepository;
import tn.esprit.examen.nomPrenomClasseExamen.dto.AssociationDto;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Association;
import tn.esprit.examen.nomPrenomClasseExamen.entities.Subscriber;
import tn.esprit.examen.nomPrenomClasseExamen.jwt.JwtUtils; // Correct import statement

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AssociationServices {
    private AssociationRepository associationRepository;
    private SubscriberRepository subscriberRepository;
    private JwtUtils jwtUtils;

    // For getting all associations (token is not needed)
    public List<AssociationDto> getAllAssociations() {
        return associationRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // For getting a single association by ID (token is not needed)
    public Optional<AssociationDto> getAssociationById(Long id) {
        return associationRepository.findById(id)
                .map(this::convertToDto);
    }

    // For creating an association (token is needed)
    public AssociationDto createAssociation(AssociationDto associationDto, String token) {
        Long userId = jwtUtils.getUserIdFromJwtToken(token);

        Association association = convertToEntity(associationDto);
        Subscriber subscriber = subscriberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with id " + userId));

        association.setSubscriber(subscriber);

        return convertToDto(associationRepository.save(association));
    }

    // For updating an association (token is needed)
    public AssociationDto updateAssociation(Long id, AssociationDto associationDto, String token) {
        Long userId = jwtUtils.getUserIdFromJwtToken(token);

        return associationRepository.findById(id).map(existingAssociation -> {
            existingAssociation.setAssociationAddress(associationDto.getAssociationAddress());
            existingAssociation.setAssociationName(associationDto.getAssociationName());
            existingAssociation.setDescription(associationDto.getDescription());
            existingAssociation.setStatus(associationDto.getStatus());

            if (associationDto.getAssociationLogoPath() != null) {
                existingAssociation.setAssociationLogoPath(associationDto.getAssociationLogoPath());
            }
            if (associationDto.getRegistrationDocumentPath() != null) {
                existingAssociation.setRegistrationDocumentPath(associationDto.getRegistrationDocumentPath());
            }
            if (associationDto.getLegalDocumentPath() != null) {
                existingAssociation.setLegalDocumentPath(associationDto.getLegalDocumentPath());
            }

            return convertToDto(associationRepository.save(existingAssociation));
        }).orElseThrow(() -> new RuntimeException("Association not found with id " + id));
    }

    // For deleting an association (token is needed)
    public void deleteAssociation(Long id, String token) {
        associationRepository.deleteById(id);
    }

    private AssociationDto convertToDto(Association association) {
        return new AssociationDto(
                association.getIdAssociation(),
                association.getAssociationAddress(),
                association.getAssociationName(),
                association.getDescription(),
                association.getStatus(),
                association.getAssociationLogoPath(),
                association.getRegistrationDocumentPath(),
                association.getLegalDocumentPath(),
                association.getSubscriber(),
                association.getSubscriptions(),
                association.getMissions(),
                association.getEvents(),
                association.getNotifications()
        );
    }

    private Association convertToEntity(AssociationDto associationDto) {
        Association association = new Association();
        association.setIdAssociation(associationDto.getIdAssociation());
        association.setAssociationAddress(associationDto.getAssociationAddress());
        association.setAssociationName(associationDto.getAssociationName());
        association.setDescription(associationDto.getDescription());
        association.setStatus(associationDto.getStatus());
        association.setAssociationLogoPath(associationDto.getAssociationLogoPath());
        association.setRegistrationDocumentPath(associationDto.getRegistrationDocumentPath());
        association.setLegalDocumentPath(associationDto.getLegalDocumentPath());
        association.setSubscriptions(associationDto.getSubscriptions());
        association.setMissions(associationDto.getMissions());
        association.setEvents(associationDto.getEvents());
        association.setNotifications(associationDto.getNotifications());
        return association;
    }
}
