package bp.service;

import bp.model.CSVHeaders;
import bp.model.Record;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import bp.repository.RecordRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UploadCSVService {

    private RecordRepository recordRepository;

    public UploadCSVService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<Record> saveAll(MultipartFile file) throws IOException {

        List<Record> recordList = new LinkedList<>(parseFileToModel(file));
        return recordRepository.saveAll(recordList);
    }

    public List<Record> parseFileToModel(MultipartFile file) throws IOException {

        List<Record> recordsList = new LinkedList<>();

        Reader in = multipartToFile(file);
        CSVParser records = CSVFormat.DEFAULT
                .withHeader(Arrays.toString(CSVHeaders.values()))
                .withFirstRecordAsHeader()
                .parse(in);

        records.getRecords()
                .forEach(x -> {
                    recordsList.add(
                            new Record(Long.valueOf(x.get(CSVHeaders.PRIMARY_KEY)),
                                    getName(x.get(CSVHeaders.NAME)),
                                    getName(x.get(CSVHeaders.DESCRIPTION)),
                                    getDate(x.get(CSVHeaders.UPDATED_TIMESTAMP))));
                });

        return recordsList;
    }

    public Date getDate(String name) {
        return !name.equals("") ? new Date((new Timestamp(Long.valueOf(name))).getTime()) : null;
    }

    public String getName(String name) {
        return !name.equals("") ? name : null;
    }
    public BufferedReader multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        return new BufferedReader(new InputStreamReader(multipart.getInputStream(), StandardCharsets.UTF_8));
    }
}
