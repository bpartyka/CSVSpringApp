package bp.repository;

import bp.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findById(Long aLong);

    Page<Record> findAllByUpdatedTimeBetween(Pageable pageable, Date startDate, Date endDate);

}
