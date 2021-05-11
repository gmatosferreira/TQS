package pt.tqsua.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findByMaker(String maker);
    public List<Car> findByModel(String maker);

}
