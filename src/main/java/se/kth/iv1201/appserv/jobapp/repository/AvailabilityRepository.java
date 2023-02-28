package se.kth.iv1201.appserv.jobapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.iv1201.appserv.jobapp.domain.Availability;

/**
 * JPA Repository interface used to preform CRUD operations on the availability table.
 */
@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
}
