package com.example.dragonsofmugloar.services;

import com.example.dragonsofmugloar.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class MugloarAPIService {


    private Game game;

   @Autowired
    public MugloarAPIService(Game game) {
        this.game = game;
    }

    public Message[] getMessages() {
        ResponseEntity<Message[]> messages = new RestTemplate()
                .getForEntity("https://dragonsofmugloar.com/api/v2/"+game.getGameId()+"/messages", Message[].class);
        return messages.getBody();
    }

   public MessageResponse solveMessage(String adId) {
       ResponseEntity<MessageResponse> messageResponse = new RestTemplate()
              .postForEntity("https://dragonsofmugloar.com/api/v2/"+game.getGameId()+"/solve/"+adId, null, MessageResponse.class);
       game.setGold(messageResponse.getBody().getGold());
       game.setLives(messageResponse.getBody().getLives());
       game.setScore(messageResponse.getBody().getScore());
      return messageResponse.getBody();
    }

    public Item[] getShop() {
        ResponseEntity<Item[]> items = new RestTemplate()
                .getForEntity("https://dragonsofmugloar.com/api/v2/"+game.getGameId()+"/shop", Item[].class);
        return items.getBody();
    }

    public ShopResponse purchaseItem(String itemId) {
        ResponseEntity<ShopResponse> shopResponse = new RestTemplate()
                .postForEntity("https://dragonsofmugloar.com/api/v2/"+game.getGameId()+"/shop/buy/"+itemId, null, ShopResponse.class);
        game.setGold(shopResponse.getBody().getGold());
        game.setLives(shopResponse.getBody().getLives());
        return shopResponse.getBody();
    }


}
