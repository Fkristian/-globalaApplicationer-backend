package se.kth.iv1201.appserv.jobapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.iv1201.appserv.jobapp.domain.Competence;

/**
 * JPA Repository interface used to preform CRUD operations on the competence table.
 */
@Repository
public interface CompetenceRepository extends JpaRepository <Competence, Integer> {
    Competence findByName(String name);
}
