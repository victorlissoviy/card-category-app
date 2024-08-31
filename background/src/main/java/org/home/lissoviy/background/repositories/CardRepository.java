package org.home.lissoviy.background.repositories;


import org.home.lissoviy.background.models.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
}
