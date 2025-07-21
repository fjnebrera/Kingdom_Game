package com.tecnocampus.kingdom_game.api;

import com.tecnocampus.kingdom_game.application.KingdomController;
import com.tecnocampus.kingdom_game.application.dto.GoldToPayDTO;
import com.tecnocampus.kingdom_game.application.dto.KingdomDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping("/kingdoms")
public class KingdomRestController {

    private final KingdomController kingdomController;

    public KingdomRestController(KingdomController kingdomController) {
        this.kingdomController = kingdomController;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public KingdomDTO createKingdom(@RequestBody KingdomDTO kingdomDTO) {
        return kingdomController.createKingdom(kingdomDTO);
    }

    @Transactional(propagation= Propagation.NEVER)
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO startDailyProduction(@PathVariable String id) {
        return kingdomController.startDailyProduction(id);
    }

    @PostMapping("/{id}/invest")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO invest(@PathVariable String id, @PathParam("type") String type, @RequestBody GoldToPayDTO goldToPayDTO) {
        return kingdomController.invest(id, type, goldToPayDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO getKingdom(@PathVariable String id) {
         return kingdomController.getKingdom(id);
    }

    @GetMapping("/richest")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO richest() {
        return kingdomController.richest();
    }

    @PostMapping("/{id}/attack/{targetId}")
    @ResponseStatus(HttpStatus.OK)
    public KingdomDTO attackAnotherKingdom(@PathVariable String id, @PathVariable String targetId) {
        return kingdomController.attackAnotherKingdom(id, targetId);
    }
}