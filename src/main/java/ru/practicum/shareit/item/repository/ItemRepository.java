package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerIdOrderByIdAsc(long userId);

    @Query("SELECT i FROM Item i WHERE (LOWER(i.name) LIKE CONCAT('%', LOWER(?1), '%') OR LOWER(i.description) " +
            "LIKE CONCAT('%', LOWER(?1), '%')) AND i.available = ?2 ORDER BY i.id")
    List<Item> findByNameLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndAvailableOrderByIdDesc(String text, boolean available);
}
