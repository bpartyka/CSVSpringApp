package bp.controller;

import bp.model.Record;
import bp.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping(value = "/v1/clients/{id}")
    public ResponseEntity<Record> findById(@PathVariable("id") Long id) {
        return ResponseEntity.of(clientService.findById(id));
    }

    @GetMapping(value = "/v1/clients/getByTime")
    public Page<Record> findAllInGivenPeriod(@RequestParam("timeFrom") Long timeFrom, @RequestParam("timeTo") Long timeTo, Pageable page) {
        return clientService.findAllInGivenPeriod(timeFrom, timeTo, page);
    }
}
