package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 ORDER BY b.start DESC")
    List<Booking> findAllBookingsByBookerId(Long bookerId);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 AND b.start < ?2 AND b.end > ?2 ORDER BY b.start DESC")
    List<Booking> findCurrentBookingsByBookerId(Long bookerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 AND b.end < ?2 ORDER BY b.start DESC")
    List<Booking> findPastBookingsByBookerId(Long bookerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 AND b.start > ?2 ORDER BY b.start DESC")
    List<Booking> findFutureBookingsByBookerId(Long bookerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = ?1 AND b.status = ?2 ORDER BY b.start DESC")
    List<Booking> findBookingsByBookerIdAndStatus(Long bookerId, BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = ?1 ORDER BY b.start DESC")
    List<Booking> findAllBookingsByOwnerId(Long ownerId);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = ?1 AND b.start < ?2 AND b.end > ?2 ORDER BY b.start DESC")
    List<Booking> findCurrentBookingsByOwnerId(Long ownerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = ?1 AND b.end < ?2 ORDER BY b.start DESC")
    List<Booking> findPastBookingsByOwnerId(Long ownerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = ?1 AND b.start > ?2 ORDER BY b.start DESC")
    List<Booking> findFutureBookingsByOwnerId(Long ownerId, LocalDateTime currentDate);

    @Query("SELECT b FROM Booking b WHERE b.item.owner.id = ?1 AND b.status = ?2 ORDER BY b.start DESC")
    List<Booking> findBookingsByOwnerIdAndStatus(Long ownerId, BookingStatus status);

    List<Booking> findByItemIdAndBookerIdAndEndLessThanAndStatus(Long id, Long id1, LocalDateTime end, BookingStatus status);

    List<Booking> findByItemIdOrderByIdDesc(Long id);
}
