package org.home.lissoviy.repositories;


import org.home.lissoviy.models.Card;
import org.home.lissoviy.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {

  List<Card> findAllByCategoriesContains(Category category);
}
