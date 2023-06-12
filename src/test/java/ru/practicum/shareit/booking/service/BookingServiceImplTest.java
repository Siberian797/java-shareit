package ru.practicum.shareit.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.exception.EntityNotValidException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingServiceImpl.class})
@ExtendWith(SpringExtension.class)
@SuppressWarnings("unused")
class BookingServiceImplTest {
    @MockBean
    private BookingMapper bookingMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findById(Mockito.<Long>any())).thenThrow(new EntityNotValidException("item", "item"));
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl.createBooking(new BookingRequestDto(), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking2() {
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        Optional<Item> ofResult = Optional.of(item);
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.createBooking(new BookingRequestDto(), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#readBooking(long, long)}
     */
    @Test
    void testReadBooking() {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(bookingResponseDto);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(BookingStatus.WAITING);
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(bookingResponseDto, bookingServiceImpl.readBooking(1L, 1L));
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#readBooking(long, long)}
     */
    @Test
    void testReadBooking2() {
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(bookingResponseDto);

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        User owner2 = new User();
        owner2.setEmail("jane.doe@example.org");
        owner2.setId(1L);
        owner2.setName("Name");

        User requester2 = new User();
        requester2.setEmail("jane.doe@example.org");
        requester2.setId(1L);
        requester2.setName("Name");

        Request request2 = new Request();
        request2.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("The characteristics of someone or something");
        request2.setId(1L);
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(true);
        item2.setDescription("The characteristics of someone or something");
        item2.setId(1L);
        item2.setName("Name");
        item2.setOwner(owner2);
        item2.setRequest(request2);
        Booking booking = mock(Booking.class);
        when(booking.getItem()).thenReturn(item2);
        when(booking.getBooker()).thenReturn(user);
        doNothing().when(booking).setBooker(Mockito.any());
        doNothing().when(booking).setEnd(Mockito.any());
        doNothing().when(booking).setId(Mockito.<Long>any());
        doNothing().when(booking).setItem(Mockito.any());
        doNothing().when(booking).setStart(Mockito.any());
        doNothing().when(booking).setStatus(Mockito.any());
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(BookingStatus.WAITING);
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(bookingResponseDto, bookingServiceImpl.readBooking(1L, 1L));
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking, atLeast(1)).getItem();
        verify(booking, atLeast(1)).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking() {
        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(1L);
        booker.setName("Name");

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(1L);
        owner.setName("Name");

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);

        Item item = new Item();
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);

        Booking booking = new Booking();
        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(BookingStatus.WAITING);
        Optional<Booking> ofResult = Optional.of(booking);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult2 = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking2() {
        when(bookingRepository.findById(Mockito.<Long>any()))
                .thenThrow(new EntityNotValidException("booking", "booking"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).isEmpty());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings2() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenThrow(new EntityNotValidException("Entity", "Field"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl.getBookings(BookingState.ALL, 1L, null));
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getItems(BookingState.ALL, 1L, null).isEmpty());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems2() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenThrow(new EntityNotValidException("Entity", "Field"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl.getItems(BookingState.ALL, 1L, null));
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }
}