package pt.tqsua.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.tqsua.homework.model.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    public List<Location> findByNameContains(String name);

}
