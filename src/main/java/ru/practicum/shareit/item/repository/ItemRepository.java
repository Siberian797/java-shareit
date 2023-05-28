package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerIdOrderByIdAsc(long userId);

    //TODO: уточнить
    @Query("SELECT i FROM Item i WHERE (lower(i.name) like concat('%', lower(:text), '%') or lower(i.description) " +
            "like concat('%', lower(:text), '%')) and i.available = true order by i.id")
    List<Item> findAvailableItemsByText(String text);
}
