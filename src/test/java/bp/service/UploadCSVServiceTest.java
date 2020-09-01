package bp.service;

import bp.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


class UploadCSVServiceTest {

    @Mock
    private RecordRepository recordRepository;
    private UploadCSVService uploadCSVService;


    @BeforeEach
    public void setUp() {
        uploadCSVService = new UploadCSVService(recordRepository);
    }

    @Test
    void shouldReturnConvertedDateFromTimestamp() throws ParseException {

        String timestamp = "1598902011588";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.ENGLISH);

        Date resultTime = 	formatter.parse("2020-08-31T19:26:51.588+00:00");

        assertEquals(resultTime, uploadCSVService.getDate(timestamp));

    }
    @Test
    void shouldReturnNullValueWhenValueIsEmpty() {

        assertEquals(null, uploadCSVService.getDate(""));

        assertEquals(null, uploadCSVService.getName(""));
    }

    @Test
    void shouldReturnTheSameValueWhenIsNotEmpty() {
        assertEquals("Name", uploadCSVService.getName("Name"));
    }



}