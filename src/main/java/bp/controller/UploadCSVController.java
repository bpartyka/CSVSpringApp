package bp.controller;

import bp.model.Record;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import bp.service.UploadCSVService;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class UploadCSVController {

    private UploadCSVService service;

    public UploadCSVController(UploadCSVService service) {
        this.service=service;
    }

    @PostMapping(value = "/v1/import/csv")
    public List<Record> saveAll(@RequestParam("file") MultipartFile file) throws IOException {
        return service.saveAll(file);
    }

}
