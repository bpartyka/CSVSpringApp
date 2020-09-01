package bp.service;

import bp.repository.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceOwnerService {

    private RecordRepository recordRepository;

    public ServiceOwnerService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Transactional
    public Long removeById(Long id) {
        recordRepository.deleteById(id);
        return id;
    }
}
