package ru.practicum.shareit.booking.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByBookerIdOrderByStartDesc(Long bookerId, Pageable pageable);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status, Pageable pageable);

    List<Booking> findByItemOwnerIdOrderByStartDesc(Long ownerId, Pageable pageable);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status, Pageable pageable);

    Boolean existsByItemIdAndBookerIdAndEndLessThanAndStatus(Long itemId, Long bookerId, LocalDateTime end, BookingStatus status);

    List<Booking> findByBookerIdAndEndLessThanOrderByStartDesc(Long bookerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByBookerIdAndStartGreaterThanOrderByStartDesc(Long bookerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByItemOwnerIdAndEndLessThanOrderByStartDesc(Long ownerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByItemOwnerIdAndStartGreaterThanOrderByStartDesc(Long ownerId, LocalDateTime now, Pageable pageable);

    List<Booking> findByBookerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Long bookerId, LocalDateTime now,
                                                                                  LocalDateTime now1, Pageable pageable);

    List<Booking> findByItemOwnerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Long ownerId, LocalDateTime now,
                                                                                     LocalDateTime now1, Pageable pageable);

    List<Booking> findByItemInAndStatus(List<Item> items, Sort sort, BookingStatus bookingStatus);
}
