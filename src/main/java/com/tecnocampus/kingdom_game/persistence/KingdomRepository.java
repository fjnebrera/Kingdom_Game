package com.tecnocampus.kingdom_game.persistence;

import com.tecnocampus.kingdom_game.domain.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KingdomRepository extends JpaRepository<Kingdom, String> {

}
