package se.kth.iv1201.appserv.jobapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity class representing the competence_profile table from the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "competence_profile")
public class CompetenceProfile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int competenceProfileId;
    @Column(name = "person_id")
    int personId;
    @Column(name = "competence_id")
    int competenceId;
    double yearsOfExperience;

    @JoinColumn(name = "competence_id", referencedColumnName = "competence_id", insertable = false, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    Competence competenceName;


}
