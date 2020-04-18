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

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
        System.out.println(newObj);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/schools"),
                HttpMethod.GET, entity, String.class);
        System.out.println("response");
        System.out.println(response);                
        String expected = "[{\"id\":1,\"name\":\"First Location\",\"principle\":\"Mr. Ranvir\",\"address\":\"California\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
