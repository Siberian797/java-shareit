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
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.createBooking(new BookingRequestDto(), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.any());
        doNothing().when(item).setId(Mockito.<Long>any());
        doNothing().when(item).setName(Mockito.any());
        doNothing().when(item).setOwner(Mockito.any());
        doNothing().when(item).setRequest(Mockito.any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        Optional<Item> ofResult2 = Optional.of(item);
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl
                .createBooking(new BookingRequestDto(start, LocalDate.of(1970, 1, 1).atStartOfDay(), 1L), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
        verify(item).getAvailable();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.any());
        verify(item).setId(Mockito.<Long>any());
        verify(item).setName(Mockito.any());
        verify(item).setOwner(Mockito.any());
        verify(item).setRequest(Mockito.any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking5() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        doNothing().when(item).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item).setDescription(Mockito.any());
        doNothing().when(item).setId(Mockito.<Long>any());
        doNothing().when(item).setName(Mockito.any());
        doNothing().when(item).setOwner(Mockito.any());
        doNothing().when(item).setRequest(Mockito.any());
        item.setAvailable(true);
        item.setDescription("The characteristics of someone or something");
        item.setId(1L);
        item.setName("Name");
        item.setOwner(owner);
        item.setRequest(request);
        Optional<Item> ofResult2 = Optional.of(item);
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl
                .createBooking(new BookingRequestDto(start, LocalDate.of(1970, 1, 1).atStartOfDay(), 1L), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
        verify(item).getAvailable();
        verify(item).setAvailable(Mockito.<Boolean>any());
        verify(item).setDescription(Mockito.any());
        verify(item).setId(Mockito.<Long>any());
        verify(item).setName(Mockito.any());
        verify(item).setOwner(Mockito.any());
        verify(item).setRequest(Mockito.any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking6() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        Optional<Item> ofResult2 = Optional.of(Item.builder().id(1L).available(false).request(request).name("name").description("description").build());
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl
                .createBooking(BookingRequestDto.builder().start(start).end(start.plusDays(30)).itemId(1L).build(), 2L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking7() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        Optional<Item> ofResult2 = Optional.of(Item.builder().id(1L).available(true).request(request).name("name").description("description").owner(user).build());
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl
                .createBooking(BookingRequestDto.builder().start(start).end(start.plusDays(30)).itemId(1L).build(), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#createBooking(BookingRequestDto, long)}
     */
    @Test
    void testCreateBooking8() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");

        UserDto userDto = UserDto.builder().id(1L).email("jane.doe@example.org").name("Name").build();

        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User owner = new User();
        owner.setEmail("jane.doe@example.org");
        owner.setId(2L);
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
        Item item = mock(Item.class);
        when(item.getAvailable()).thenReturn(true);
        when(item.getOwner()).thenReturn(user);
        Optional<Item> ofResult2 = Optional.of(Item.builder().id(1L).available(true).request(request).name("name").description("description").owner(owner).build());
        when(itemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        when(bookingMapper.toBooking(Mockito.any())).thenReturn(new Booking());
        when(bookingRepository.save(Mockito.any())).thenReturn(Booking.builder().id(1L).status(BookingStatus.WAITING).start(LocalDateTime.MIN).end(LocalDateTime.MAX).booker(user).item(item).build());
        LocalDateTime start = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertDoesNotThrow(() -> bookingServiceImpl.createBooking(BookingRequestDto.builder().start(start).end(start.plusDays(30)).itemId(1L).build(), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findById(Mockito.<Long>any());
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
     * Method under test: {@link BookingServiceImpl#readBooking(long, long)}
     */
    @Test
    void testReadBooking3() {
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
    void testReadBooking4() {
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
     * Method under test: {@link BookingServiceImpl#readBooking(long, long)}
     */
    @Test
    void testReadBooking5() {
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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#readBooking(long, long)}
     */
    @Test
    void testReadBooking6() {
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
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getName()).thenReturn("Name");
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(user2).getId();
        verify(user2).getEmail();
        verify(user2).getName();
        verify(user2).setEmail(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.any());
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
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking3() {
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
        Booking booking = mock(Booking.class);
        when(booking.getBooker()).thenReturn(user);
        when(booking.getStatus()).thenReturn(BookingStatus.WAITING);
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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.ofNullable(User.builder().email("email@mail.com").id(1L).name("name").build()));
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking).getStatus();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking4() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setName(Mockito.any());
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
        when(booking.getStatus()).thenReturn(BookingStatus.WAITING);
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

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        item3.setRequest(request3);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item3);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.WAITING);
        when(bookingRepository.save(Mockito.any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking).getStatus();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(user).getId();
        verify(user).setEmail(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking5() {
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
        Booking booking = mock(Booking.class);
        when(booking.getBooker()).thenReturn(user);
        when(booking.getStatus()).thenReturn(BookingStatus.WAITING);
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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking).getStatus();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking6() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setName(Mockito.any());
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
        when(booking.getStatus()).thenReturn(BookingStatus.WAITING);
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

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        item3.setRequest(request3);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item3);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.WAITING);
        when(bookingRepository.save(Mockito.any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotFoundException.class, () -> bookingServiceImpl.updateBooking(1L, true, 1L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking).getStatus();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(user).getId();
        verify(user).setEmail(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking7() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

        User booker = new User();
        booker.setEmail("jane.doe@example.org");
        booker.setId(10L);
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
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        doNothing().when(user).setEmail(Mockito.any());
        doNothing().when(user).setId(Mockito.<Long>any());
        doNothing().when(user).setName(Mockito.any());
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
        when(booking.getStatus()).thenReturn(BookingStatus.WAITING);

        booking.setBooker(booker);
        booking.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setId(1L);
        booking.setItem(item);
        booking.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking.setStatus(BookingStatus.WAITING);

        User booker2 = new User();
        booker2.setEmail("jane.doe@example.org");
        booker2.setId(1L);
        booker2.setName("Name");

        User owner3 = new User();
        owner3.setEmail("jane.doe@example.org");
        owner3.setId(1L);
        owner3.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);

        Item item3 = new Item();
        item3.setAvailable(true);
        item3.setDescription("The characteristics of someone or something");
        item3.setId(1L);
        item3.setName("Name");
        item3.setOwner(owner3);
        item3.setRequest(request3);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(1L);
        booking2.setItem(item3);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.WAITING);
        when(bookingRepository.save(Mockito.any())).thenReturn(booking2);
        when(bookingRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(booking));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(5L);
        user2.setName("Name");
        Optional<User> ofResult2 = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotValidException.class, () -> bookingServiceImpl.updateBooking(1L, true, 5L));
        verify(bookingRepository).findById(Mockito.<Long>any());
        verify(booking).getStatus();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(user).getId();
        verify(user).setEmail(Mockito.any());
        verify(user).setId(Mockito.<Long>any());
        verify(user).setName(Mockito.any());
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
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings3() {
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

    @Test
    void testGetBookings3_1() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getBookings(BookingState.PAST, 1L, null).isEmpty());
    }

    @Test
    void testGetBookings3_2() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getBookings(BookingState.FUTURE, 1L, null).isEmpty());
    }

    @Test
    void testGetBookings3_3() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getBookings(BookingState.WAITING, 1L, null).isEmpty());
    }

    @Test
    void testGetBookings3_4() {
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getBookings(BookingState.REJECTED, 1L, null).isEmpty());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings4() {
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
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings5() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings6() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User booker2 = new User();
        booker2.setEmail("john.smith@example.org");
        booker2.setId(2L);
        booker2.setName("42");

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("42");

        Request request2 = new Request();
        request2.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("Description");
        request2.setId(2L);
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(2L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.APPROVED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking2);
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper, atLeast(1)).toResponseDto(Mockito.any(), Mockito.any(),
                Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings7() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings8() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings9() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getName()).thenReturn("Name");
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(user2).getId();
        verify(user2).getEmail();
        verify(user2).getName();
        verify(user2).setEmail(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings10() {
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
    void testGetBookings11() {
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
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings12() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings13() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User booker2 = new User();
        booker2.setEmail("john.smith@example.org");
        booker2.setId(2L);
        booker2.setName("42");

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("42");

        Request request2 = new Request();
        request2.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("Description");
        request2.setId(2L);
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(2L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.APPROVED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking2);
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper, atLeast(1)).toResponseDto(Mockito.any(), Mockito.any(),
                Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings14() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings15() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings16() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getName()).thenReturn("Name");
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(user2).getId();
        verify(user2).getEmail();
        verify(user2).getName();
        verify(user2).setEmail(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings17() {
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
    void testGetBookings18() {
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
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings19() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings20() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User booker2 = new User();
        booker2.setEmail("john.smith@example.org");
        booker2.setId(2L);
        booker2.setName("42");

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("42");

        Request request2 = new Request();
        request2.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("Description");
        request2.setId(2L);
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(2L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.APPROVED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking2);
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper, atLeast(1)).toResponseDto(Mockito.any(), Mockito.any(),
                Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings21() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings22() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings23() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getName()).thenReturn("Name");
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(user2).getId();
        verify(user2).getEmail();
        verify(user2).getName();
        verify(user2).setEmail(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings24() {
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
    void testGetBookings25() {
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
     * Method under test: {@link BookingServiceImpl#getBookings(BookingState, long, PageRequest)}
     */
    @Test
    void testGetBookings26() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getBookings(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
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

    @Test
    void testGetItems_1() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getItems(BookingState.PAST, 1L, null).isEmpty());
    }

    @Test
    void testGetItems_2() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getItems(BookingState.FUTURE, 1L, null).isEmpty());
    }

    @Test
    void testGetItems_3() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getItems(BookingState.WAITING, 1L, null).isEmpty());
    }

    @Test
    void testGetItems_4() {
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(bookingServiceImpl.getItems(BookingState.REJECTED, 1L, null).isEmpty());
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

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems3() {
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
    void testGetItems4() {
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

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems5() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getItems(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems6() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User booker2 = new User();
        booker2.setEmail("john.smith@example.org");
        booker2.setId(2L);
        booker2.setName("42");

        User owner2 = new User();
        owner2.setEmail("john.smith@example.org");
        owner2.setId(2L);
        owner2.setName("42");

        User requester2 = new User();
        requester2.setEmail("john.smith@example.org");
        requester2.setId(2L);
        requester2.setName("42");

        Request request2 = new Request();
        request2.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request2.setDescription("Description");
        request2.setId(2L);
        request2.setRequester(requester2);

        Item item2 = new Item();
        item2.setAvailable(false);
        item2.setDescription("Description");
        item2.setId(2L);
        item2.setName("42");
        item2.setOwner(owner2);
        item2.setRequest(request2);

        Booking booking2 = new Booking();
        booking2.setBooker(booker2);
        booking2.setEnd(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setId(2L);
        booking2.setItem(item2);
        booking2.setStart(LocalDate.of(1970, 1, 1).atStartOfDay());
        booking2.setStatus(BookingStatus.APPROVED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking2);
        bookingList.add(booking);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(2, bookingServiceImpl.getItems(BookingState.ALL, 1L, null).size());
        verify(bookingMapper, atLeast(1)).toResponseDto(Mockito.any(), Mockito.any(),
                Mockito.any());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems7() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getItems(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems8() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getItems(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingServiceImpl#getItems(BookingState, long, PageRequest)}
     */
    @Test
    void testGetItems9() {
        when(bookingMapper.toResponseDto(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new BookingResponseDto());

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
        User user2 = mock(User.class);
        when(user2.getId()).thenReturn(1L);
        when(user2.getEmail()).thenReturn("jane.doe@example.org");
        when(user2.getName()).thenReturn("Name");
        doNothing().when(user2).setEmail(Mockito.any());
        doNothing().when(user2).setId(Mockito.<Long>any());
        doNothing().when(user2).setName(Mockito.any());
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setName("Name");

        User requester3 = new User();
        requester3.setEmail("jane.doe@example.org");
        requester3.setId(1L);
        requester3.setName("Name");

        Request request3 = new Request();
        request3.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request3.setDescription("The characteristics of someone or something");
        request3.setId(1L);
        request3.setRequester(requester3);
        Item item2 = mock(Item.class);
        when(item2.getAvailable()).thenReturn(true);
        when(item2.getId()).thenReturn(1L);
        when(item2.getDescription()).thenReturn("The characteristics of someone or something");
        when(item2.getName()).thenReturn("Name");
        when(item2.getRequest()).thenReturn(request3);
        when(item2.getOwner()).thenReturn(user2);
        doNothing().when(item2).setAvailable(Mockito.<Boolean>any());
        doNothing().when(item2).setDescription(Mockito.any());
        doNothing().when(item2).setId(Mockito.<Long>any());
        doNothing().when(item2).setName(Mockito.any());
        doNothing().when(item2).setOwner(Mockito.any());
        doNothing().when(item2).setRequest(Mockito.any());
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

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(bookingList);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setName("Name");
        Optional<User> ofResult = Optional.of(user3);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertEquals(1, bookingServiceImpl.getItems(BookingState.ALL, 1L, null).size());
        verify(bookingMapper).toResponseDto(Mockito.any(), Mockito.any(), Mockito.any());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(booking, atLeast(1)).getItem();
        verify(booking).getBooker();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(item2).getAvailable();
        verify(item2).getId();
        verify(item2).getDescription();
        verify(item2).getName();
        verify(item2, atLeast(1)).getRequest();
        verify(item2).getOwner();
        verify(item2).setAvailable(Mockito.<Boolean>any());
        verify(item2).setDescription(Mockito.any());
        verify(item2).setId(Mockito.<Long>any());
        verify(item2).setName(Mockito.any());
        verify(item2).setOwner(Mockito.any());
        verify(item2).setRequest(Mockito.any());
        verify(user2).getId();
        verify(user2).getEmail();
        verify(user2).getName();
        verify(user2).setEmail(Mockito.any());
        verify(user2).setId(Mockito.<Long>any());
        verify(user2).setName(Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }
}