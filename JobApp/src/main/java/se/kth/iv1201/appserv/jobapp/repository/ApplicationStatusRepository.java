package se.kth.iv1201.appserv.jobapp.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;

/**
 * JPA Repository interface used to preform CRUD operations on the application_status table.
 */
@Repository
public interface ApplicationStatusRepository extends JpaRepository <ApplicationStatus, Integer> {
    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    ApplicationStatus findByPersonId(int id);
}
