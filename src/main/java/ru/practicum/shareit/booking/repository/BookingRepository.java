package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    List<Booking> findByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status);

    Boolean existsByItemIdAndBookerIdAndEndLessThanAndStatus(Long itemId, Long bookerId, LocalDateTime end, BookingStatus status);

    List<Booking> findByBookerIdAndEndLessThanOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findByBookerIdAndStartGreaterThanOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findByItemOwnerIdAndEndLessThanOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findByItemOwnerIdAndStartGreaterThanOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findByBookerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Long bookerId, LocalDateTime now, LocalDateTime now1);

    List<Booking> findByItemOwnerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Long ownerId, LocalDateTime now, LocalDateTime now1);

    List<Booking> findByItemInAndStatus(List<Item> items, Sort sort, BookingStatus bookingStatus);
}
