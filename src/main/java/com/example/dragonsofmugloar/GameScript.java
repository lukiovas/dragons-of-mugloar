package com.example.dragonsofmugloar;

import com.example.dragonsofmugloar.models.Game;
import com.example.dragonsofmugloar.models.Message;
import com.example.dragonsofmugloar.services.MugloarAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@Component
public class GameScript {

    private final Game game;
    private final MugloarAPIService mugloarAPIService;
    private final Logger LOGGER = LoggerFactory.getLogger(GameScript.class);
    private int turnCount;

    @Autowired
    public GameScript(Game game, MugloarAPIService mugloarAPIService) {
        this.game = game;
        this.mugloarAPIService = mugloarAPIService;
    }

    @PostConstruct
    private void startSolvingMessages() {
        try {
            while (game.getLives() > 0) {
                LOGGER.info("Getting new list of messages");
                Message[] messages = mugloarAPIService.getMessages();
                turnCount = 1;
                LOGGER.info("Checking for easy to solve messages");
                if (Arrays.stream(messages).anyMatch(this::isEasyToSolve)) {
                    LOGGER.info("There are some easy messages to solve");
                    for (Message message : messages) {
                        if (isEasyToSolve(message)) {
                            solveMessage(message);
                            buyHealthPotionIfPossible();
                        } else LOGGER.info("Message is not easy, will not solve now: {}, {}", message.getMessage(), message.getProbability());
                    }
                } else {
                    LOGGER.info("There are no easy messages to solve");
                    LOGGER.warn("Trying risky messages");
                    if (Arrays.stream(messages).anyMatch(this::isRiskyToSolve)) {
                        for (Message message : messages) {
                            if (isRiskyToSolve(message)) {
                                solveMessage(message);
                                buyHealthPotionIfPossible();
                            } else
                                LOGGER.info("This is a suicide mission: {}, {}", message.getMessage(), message.getProbability());
                        }
                    } else if (Arrays.stream(messages).anyMatch(this::isImpossibleToSolve)) {
                        LOGGER.warn("Only suicide missions left");
                        for (Message message : messages) {
                            solveMessage(message);
                            buyHealthPotionIfPossible();
                        }
                    } else {
                        LOGGER.warn("Only encrypted messages left, cannot solve them");
                        throw new InterruptedException("Game Over");
                    }
                }
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
            LOGGER.info("SCORE: {}", game.getScore());
            LOGGER.info("GOLD: {}", game.getGold());
        }

    }

    private boolean isEasyToSolve(Message message) {
        return message.getProbability().equals("Piece of cake") ||
                message.getProbability().equals("Walk in the park") ||
                message.getProbability().equals("Sure thing") ||
                message.getProbability().equals("Quite likely");
    }

    private boolean isRiskyToSolve(Message message) {
        return message.getProbability().equals("Risky") ||
                message.getProbability().equals("Playing with fire") ||
                message.getProbability().equals("Gamble");
    }
    private boolean isImpossibleToSolve(Message message) {
        return message.getProbability().equals("Suicide mission") ||
                message.getProbability().equals("Impossible");
    }

    private void buyHealthPotionIfPossible() throws InterruptedException {
        if (game.getLives() < 3) {
            if (game.getGold() >= 50) {
                LOGGER.info("Buying health potion");
                mugloarAPIService.purchaseItem("hpot");
            } else if (game.getLives() > 0) LOGGER.info("Not enough gold to buy a life");
            turnCount++;
            if (game.getLives() < 1) {
                throw new InterruptedException("Game Over");
            }
        }
    }


    private void solveMessage(Message message) {
        LOGGER.info("Checking expiration: turn {}, expiresIn {}",
                turnCount, message.getExpiresIn());
        if (message.getExpiresIn() >= turnCount) {
            LOGGER.info("Message hasn't expired yet");
            LOGGER.info("Starting solving: {}", message.getMessage());
            if (mugloarAPIService.solveMessage(message.getAdId()).isSuccess()) {
                LOGGER.info("Message has been solved : {}", message.getMessage());
            } else {
                LOGGER.info("Failed solving message: {}", message.getMessage());
                LOGGER.info("Lost a life");
                LOGGER.info("Remaining lives: {}", game.getLives());
            }
            turnCount++;
        } else LOGGER.info("Message has expired: {}, turn {}, expiresIn {}",
                message.getMessage(), turnCount, message.getExpiresIn());
    }
}