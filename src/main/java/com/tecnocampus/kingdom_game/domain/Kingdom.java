package com.tecnocampus.kingdom_game.domain;

import com.tecnocampus.kingdom_game.application.dto.KingdomDTO;
import com.tecnocampus.kingdom_game.utilities.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "kingdom")
public class Kingdom implements Comparable<Kingdom>{

    @Id
    @Column
    private String kingdomId;
    @Column
    private int gold;
    @Column
    private int citizens;
    @Column
    private int food;
    @Column
    private LocalDateTime dateOfCreation;

    public Kingdom() {
    }

    public Kingdom(KingdomDTO kingdomDTO) {
        validateKingdomToCreate(kingdomDTO);
        this.kingdomId = UUID.randomUUID().toString();
        this.gold = kingdomDTO.gold();
        this.citizens = kingdomDTO.citizens();
        this.food = kingdomDTO.food();
        this.dateOfCreation = LocalDateTime.now();
    }

    private void validateKingdomToCreate(KingdomDTO kingdomDTO) throws InvalidValuesException {
        if (!checkValue(kingdomDTO.gold()))
            throw new InvalidValuesException("El gold debe tener un valor entre 0 y 60");
        if (!checkValue(kingdomDTO.citizens()))
            throw new InvalidValuesException("El citizens debe tener un valor entre 0 y 60");
        if (!checkValue(kingdomDTO.food()))
            throw new InvalidValuesException("El food debe tener un valor entre 0 y 60");
    }

    private boolean checkValue(int value) {
        return value >= 0 & value <= 60;
    }

    public int getGold() {
        return gold;
    }
    public int getCitizens() {
        return citizens;
    }
    public int getFood() {
        return food;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setCitizens(int citizens) {
        this.citizens = citizens;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public String getKingdomId() {
        return kingdomId;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void productionDay() throws DomainException {
        feedKingdom();
        workingKingdom();
        liveKingdom();
    }

    private void liveKingdom() throws DeathKingdomException {
        if (getCitizens() <= 0)
            throw new DeathKingdomException();
    }

    private void workingKingdom() {
        setGold(getGold() + (getCitizens() * 2));
    }

    private void feedKingdom() {
        if(getFood() >= getCitizens()) {
            setFood(getFood() - getCitizens());
        } else {
            setCitizens(getCitizens() + (getFood() - getCitizens()));
            setFood(0);
        }
    }

    public void invest(String type, int goldToPay) {
        if(!type.equals("food") && !type.equals("citizens"))
            throw new InvalidValuesException("Invalid type");

        if (this.getGold() <= goldToPay)
            throw new InsufficientGoldException();

        if (type.equals("food")) {
            this.setGold(this.getGold() - goldToPay);
            this.setFood(this.getFood() + (2*goldToPay));
        }
        if (type.equals("citizens")) {
            this.setGold(this.getGold() - goldToPay);
            this.setCitizens(this.getCitizens() + goldToPay);
        }
    }

    @Override
    public int compareTo(Kingdom o) {
        return o.getGold() - this.getGold();
    }

    public void attack(Kingdom defender) {
        if (this.getCitizens() > defender.getCitizens())
            winnerAttacker(defender);
        if (this.getCitizens() == defender.getCitizens() || this.getCitizens() < defender.getCitizens())
            winnerDefender(defender);
    }

    private void winnerAttacker(Kingdom defender) {
        this.setGold(this.getGold() + defender.getGold());
        defender.setGold(0);
        double stealCitizens = defender.getCitizens() / 2d;
        this.setCitizens((int) (this.getCitizens() + stealCitizens));
        defender.setCitizens((int) (defender.getCitizens() - stealCitizens));
    }
    private void winnerDefender(Kingdom defender) {
        defender.setGold(this.getGold() + defender.getGold());
        this.setGold(0);
        double stealCitizens = this.getCitizens() / 2d;
        defender.setCitizens((int) (defender.getCitizens() + stealCitizens));
        this.setCitizens((int) (this.getCitizens() - stealCitizens));
    }
}