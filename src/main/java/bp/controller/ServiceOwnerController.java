package bp.controller;


import bp.service.ServiceOwnerService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceOwnerController {

    private ServiceOwnerService serviceOwnerService;

    public ServiceOwnerController(ServiceOwnerService serviceOwnerService) {
        this.serviceOwnerService = serviceOwnerService;
    }

    @DeleteMapping(value = "/v1/owners/{id}")
    public ResponseEntity<Long> removeById(@PathVariable("id") Long id) {
        try {
            serviceOwnerService.removeById(id);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
