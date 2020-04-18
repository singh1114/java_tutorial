package io.singh1114.springboottut;

import io.singh1114.springboottut.school.School;
import io.singh1114.springboottut.school.SchoolRepository;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class SchoolControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SchoolRepository repository;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetSchoolData () throws JSONException {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        School testSchool = new School(1, "First Location", "Mr. Ranvir", "California");
        School newObj = repository.save(testSchool);
        System.out.println("Saved");
        System.out.println(newObj.getId());
        System.out.println(newObj.getName());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/schools"),
                HttpMethod.GET, entity, String.class);

        ResponseEntity<String> response1 = restTemplate.exchange(
                createURLWithPort("/schools/"),
                HttpMethod.GET, entity, String.class);
        System.out.println("response");
        System.out.println(response);
        System.out.println(response1);
        List<School> schools = new ArrayList<>();
        repository.findAll()
                .forEach(schools::add);
        System.out.println("schools in tests");
        System.out.println(schools);
        String expected = "[{\"id\":1,\"name\":\"First Location\",\"principle\":\"Mr. Ranvir\",\"address\":\"California\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        System.out.println("port");
        System.out.println(port);
        System.out.println(uri);
        return "http://localhost:" + port + uri;
    }
}
