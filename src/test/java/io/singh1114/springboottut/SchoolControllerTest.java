package io.singh1114.springboottut;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SchoolControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void testGetSchoolData () throws JSONException {
        HttpHeaders postHeaders = new HttpHeaders();
        postHeaders.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = "{\"name\":\"School 1\", \"principle\":\"Mr. Charles\", \"address\":\"California\"}";
        HttpEntity<String> schoolPostEntity = new HttpEntity<String>(requestJson, postHeaders);


        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> addSchoolResponse = restTemplate.exchange(
                createURLWithPort("/school"),
                HttpMethod.POST, schoolPostEntity, String.class);

        System.out.println("schools: " + addSchoolResponse.getBody());

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/schools"),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"name\":\"School 1\",\"principle\":\"Mr. Charles\",\"address\":\"California\"}]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
