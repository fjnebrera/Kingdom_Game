package com.tecnocampus.kingdom_game.application;

import com.tecnocampus.kingdom_game.application.dto.GoldToPayDTO;
import com.tecnocampus.kingdom_game.application.dto.KingdomDTO;
import com.tecnocampus.kingdom_game.domain.Kingdom;
import com.tecnocampus.kingdom_game.persistence.KingdomRepository;
import com.tecnocampus.kingdom_game.utilities.DeathKingdomException;
import com.tecnocampus.kingdom_game.utilities.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class KingdomController {
    @Autowired
    private KingdomRepository kingdomRepository;

    public KingdomDTO createKingdom(KingdomDTO kingdomDTO) {
        Kingdom kingdom = new Kingdom(kingdomDTO);
        kingdomRepository.save(kingdom);
        return new KingdomDTO(kingdom);
    }

    public KingdomDTO startDailyProduction(String id)  {
        Kingdom kingdom = kingdomRepository.findById(id).orElseThrow(() -> new NotFoundException("Kingdom not found"));
        try {
            kingdom.productionDay();
            return new KingdomDTO(kingdom);
        } catch (DeathKingdomException e) {
            kingdomRepository.delete(kingdom);
            throw new DeathKingdomException();
        }
    }

    public KingdomDTO getKingdom(String id) {
        Kingdom kingdom = kingdomRepository.findById(id).orElseThrow(() -> new NotFoundException("Kingdom not found"));
        return new KingdomDTO(kingdom);
    }

    public KingdomDTO invest(String id, String type, GoldToPayDTO goldToPayDTO) {
        Kingdom kingdom = kingdomRepository.findById(id).orElseThrow(() -> new NotFoundException("Kingdom not found"));
        kingdom.invest(type, Integer.parseInt(goldToPayDTO.gold()));
        return new KingdomDTO(kingdom);
    }

    public KingdomDTO richest() {
        List<Kingdom> kingdoms = kingdomRepository.findAll();
        Optional<Kingdom> opKingdom = kingdoms.stream().sorted().findFirst();
        if (opKingdom.isPresent()) {
            return new KingdomDTO(opKingdom.get());
        } else {
            return null;
        }
    }

    public KingdomDTO attackAnotherKingdom(String id, String targetId) {
        Kingdom attacker = kingdomRepository.findById(id).orElseThrow(() -> new NotFoundException("Kingdom not found"));
        Kingdom defender = kingdomRepository.findById(targetId).orElseThrow(() -> new NotFoundException("Kingdom not found"));
        attacker.attack(defender);
        return new KingdomDTO(attacker);
    }
}