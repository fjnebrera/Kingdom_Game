package com.tecnocampus.kingdom_game.application.dto;

import com.tecnocampus.kingdom_game.domain.Kingdom;

import java.time.LocalDateTime;

public record KingdomDTO (String id, LocalDateTime dateOfCreation, int gold, int citizens, int food){
    public KingdomDTO {  }
    public KingdomDTO(Kingdom kingdom) {
        this(kingdom.getKingdomId(), kingdom.getDateOfCreation(), kingdom.getGold(), kingdom.getCitizens(), kingdom.getFood());
    }
}
