package se.kth.iv1201.appserv.jobapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing the application_status table from the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "application_status")
public class ApplicationStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="application_status_id")
    int applicationStatusId;
    @Column(name="person_id")
    int personId;
    String status;
    @Version
    private Long version;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = false, updatable = false)
    User user;
}
