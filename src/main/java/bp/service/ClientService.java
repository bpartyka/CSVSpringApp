package bp.service;

import bp.model.Record;
import bp.repository.RecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {

    private RecordRepository recordRepository;

    public ClientService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public Optional<Record> findById(Long id) {

        return recordRepository.findById(id);
    }

    public Page<Record> findAllInGivenPeriod(Long timeFrom, Long timeTo, Pageable page) {
        Date dateFrom= new Date((new Timestamp(timeFrom)).getTime());
        Date dateTo = new Date((new Timestamp(timeTo)).getTime());

        return recordRepository.findAllByUpdatedTimeBetween(page, dateFrom, dateTo);
    }
}
