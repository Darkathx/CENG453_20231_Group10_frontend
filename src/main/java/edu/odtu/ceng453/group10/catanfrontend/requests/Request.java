package edu.odtu.ceng453.group10.catanfrontend.requests;


import edu.odtu.ceng453.group10.catanfrontend.config.LeaderboardType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Request {

  private static final String LOGIN_URL = "https://catan-backend-ds1e.onrender.com/userAccount/login";
  private static final String REGISTER_URL = "https://catan-backend-ds1e.onrender.com/userAccount/register";
  private static final String LEADERBOARD_URL = "https://catan-backend-ds1e.onrender.com/leaderboard";

  public LoginResponse sendLoginRequest(String email, String password) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();
    body.add("email", email);
    body.add("password", password);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

    try {
      HttpEntity<LoginResponse> response = restTemplate.exchange(
          LOGIN_URL,
          HttpMethod.POST,
          entity,
          LoginResponse.class
      );
      return response.getBody();
    }
    catch(RestClientException e) {
      return new LoginResponse("", "");
    }

  }

  public RegisterResponse sendRegisterRequest(String username, String email, String password) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();
    body.add("username", username);
    body.add("email", email);
    body.add("password", password);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

    try {
      HttpEntity<RegisterResponse> response = restTemplate.exchange(
          REGISTER_URL,
          HttpMethod.POST,
          entity,
          RegisterResponse.class
      );
      return response.getBody();
    }
    catch(RestClientException e) {
      return new RegisterResponse("", "");
    }
  }

  public String[] sendLeaderboardRequest(LeaderboardType type){
    String fullUrl = LEADERBOARD_URL;
    switch (type) {
      case LeaderboardType.WEEKLY -> fullUrl += "/weekly";
      case LeaderboardType.MONTHLY -> fullUrl += "/monthly";
      case LeaderboardType.OVERALL -> fullUrl += "/overall";
    }

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String[]> response = restTemplate.getForEntity(fullUrl, String[].class);
    HttpStatusCode responseStatus = response.getStatusCode();
    if(responseStatus != HttpStatus.ACCEPTED) {
      return null;
    }
    return response.getBody();
  }

}
