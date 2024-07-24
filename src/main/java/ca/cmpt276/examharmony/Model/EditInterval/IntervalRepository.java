package ca.cmpt276.examharmony.Model.EditInterval;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntervalRepository extends JpaRepository<EditInterval, Integer> {

        EditInterval findById(int ID);

}
