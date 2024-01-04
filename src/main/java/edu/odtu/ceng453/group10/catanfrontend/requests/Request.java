package edu.odtu.ceng453.group10.catanfrontend.requests;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import edu.odtu.ceng453.group10.catanfrontend.config.LeaderboardType;
import edu.odtu.ceng453.group10.catanfrontend.game.GameState;
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
  private static final String RESULT_URL = "https://catan-backend-ds1e.onrender.com/gameRecord/create";
  private static final String JOIN_URL = "https://catan-backend-ds1e.onrender.com/game/join";
  private static final String STATE_URL = "https://catan-backend-ds1e.onrender.com/game/gameState";

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

  public LeaderboardResponse[] sendLeaderboardRequest(LeaderboardType type){
    String fullUrl = LEADERBOARD_URL;
    switch (type) {
      case LeaderboardType.WEEKLY -> fullUrl += "/weekly";
      case LeaderboardType.MONTHLY -> fullUrl += "/monthly";
      case LeaderboardType.OVERALL -> fullUrl += "/overall";
    }

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<LeaderboardResponse[]> response = restTemplate.getForEntity(fullUrl, LeaderboardResponse[].class);
    LeaderboardResponse[] responseBody = response.getBody();
    return responseBody;
  }

  public boolean sendResetRequest(String email, String password) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body= new LinkedMultiValueMap<>();
    body.add("email", email);
    body.add("newPassword", password);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
    try {
      HttpEntity<RegisterResponse> response = restTemplate.exchange(
          REGISTER_URL,
          HttpMethod.PUT,
          entity,
          RegisterResponse.class
      );
      return true;
    }
    catch(RestClientException e) {
      return false;
    }
  }

  public void saveGameResult(String username, int score) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<RecordObject> entity = new HttpEntity<>(new RecordObject(username, null, null, null, score, 0, 0, 0), headers);
    try {
      HttpEntity<?> response = restTemplate.postForEntity(
          RESULT_URL,
          entity,
          RecordObject.class
      );
    }
    catch(RestClientException e) {
      System.out.println("Error while saving game result");
    }
  }

  public void saveMultiGameResult(List<String> players, int[] scores) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<RecordObject> entity = new HttpEntity<>(new RecordObject(players.get(0),players.get(1), players.get(2),players.get(3), scores[0], scores[1],scores[2],scores[3]), headers);
    try {
      HttpEntity<?> response = restTemplate.postForEntity(
          RESULT_URL,
          entity,
          RecordObject.class
      );
    }
    catch(RestClientException e) {
      System.out.println("Error while saving game result");
    }
  }

  public GameResponse sendJoinRequest(String username) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body= new LinkedMultiValueMap<>();
    body.add("username", username);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
    try {
      HttpEntity<GameResponse> response = restTemplate.exchange(
          JOIN_URL,
          HttpMethod.POST,
          entity,
          GameResponse.class
      );
      return response.getBody();
    }
    catch(RestClientException e) {
      return null;
    }
  }

  public GameStateResponse getGameStateRequest(String gameId) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> body= new LinkedMultiValueMap<>();
    body.add("gameId", gameId);
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
    try {
      HttpEntity<GameStateResponse> response = restTemplate.exchange(
          STATE_URL,
          HttpMethod.GET,
          entity,
          GameStateResponse.class
      );
      return response.getBody();
    }
    catch(RestClientException e) {
      return null;
    }
  }

  public GameStateResponse updateGameStateRequest(GameStateResponse gameStateResponse) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    HttpEntity<GameStateResponse> entity = new HttpEntity<>(gameStateResponse, headers);
    try {
      HttpEntity<GameStateResponse> response = restTemplate.exchange(
          STATE_URL,
          HttpMethod.POST,
          entity,
          GameStateResponse.class
      );
      return response.getBody();
    }
    catch(RestClientException e) {
      return new GameStateResponse("", "", 0, 0, 0, null, null);
    }
  }

}
