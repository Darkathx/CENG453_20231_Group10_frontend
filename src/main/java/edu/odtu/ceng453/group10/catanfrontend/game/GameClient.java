package edu.odtu.ceng453.group10.catanfrontend.game;

import edu.odtu.ceng453.group10.catanfrontend.requests.LoginResponse;

public class GameClient {
  private final String email;
  private final String username;

  GameClient(LoginResponse response) {
    email = response.email();
    username = response.username();
  }


}
