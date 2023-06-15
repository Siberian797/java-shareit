package ru.practicum.shareit.booking.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.status.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingMapper.class})
@ExtendWith(SpringExtension.class)
class BookingMapperTest {
    @Autowired
    private BookingMapper bookingMapper;

    /**
     * Method under test: {@link BookingMapper#toResponseDto(Booking, UserDto, ItemDto)}
     */
    @Test
    void testToResponseDto() {
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
        ItemDto itemDto = new ItemDto();
        BookingResponseDto actualToResponseDtoResult = bookingMapper.toResponseDto(booking, null, itemDto);
        assertNull(actualToResponseDtoResult.getBooker());
        assertEquals(BookingStatus.WAITING, actualToResponseDtoResult.getStatus());
        assertSame(itemDto, actualToResponseDtoResult.getItem());
        assertEquals("00:00", actualToResponseDtoResult.getStart().toLocalTime().toString());
        assertEquals("00:00", actualToResponseDtoResult.getEnd().toLocalTime().toString());
        assertEquals(1L, actualToResponseDtoResult.getId().longValue());
    }

    /**
     * Method under test: {@link BookingMapper#toResponseDto(Booking, UserDto, ItemDto)}
     */
    @Test
    void testToResponseDto2() {
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
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(1L);
        when(booking.getEnd()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(booking.getStart()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
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
        ItemDto itemDto = new ItemDto();
        BookingResponseDto actualToResponseDtoResult = bookingMapper.toResponseDto(booking, null, itemDto);
        assertNull(actualToResponseDtoResult.getBooker());
        assertEquals(BookingStatus.WAITING, actualToResponseDtoResult.getStatus());
        assertSame(itemDto, actualToResponseDtoResult.getItem());
        assertEquals("00:00", actualToResponseDtoResult.getStart().toLocalTime().toString());
        assertEquals("00:00", actualToResponseDtoResult.getEnd().toLocalTime().toString());
        assertEquals(1L, actualToResponseDtoResult.getId().longValue());
        verify(booking).getId();
        verify(booking).getEnd();
        verify(booking).getStart();
        verify(booking).getStatus();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
    }

    /**
     * Method under test: {@link BookingMapper#toItemResponseDto(Booking, UserDto)}
     */
    @Test
    void testToItemResponseDto() {
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
        Booking booking = mock(Booking.class);
        when(booking.getId()).thenReturn(1L);
        when(booking.getEnd()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(booking.getStart()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
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
        UserDto userDto = mock(UserDto.class);
        when(userDto.getId()).thenReturn(1L);
        BookingDto actualToItemResponseDtoResult = bookingMapper.toItemResponseDto(booking, userDto);
        assertEquals(1L, actualToItemResponseDtoResult.getBookerId());
        assertEquals(BookingStatus.WAITING, actualToItemResponseDtoResult.getStatus());
        assertEquals(1L, actualToItemResponseDtoResult.getId());
        assertEquals("00:00", actualToItemResponseDtoResult.getStart().toLocalTime().toString());
        assertEquals("1970-01-01", actualToItemResponseDtoResult.getEnd().toLocalDate().toString());
        verify(booking).getId();
        verify(booking).getEnd();
        verify(booking).getStart();
        verify(booking).getStatus();
        verify(booking).setBooker(Mockito.any());
        verify(booking).setEnd(Mockito.any());
        verify(booking).setId(Mockito.<Long>any());
        verify(booking).setItem(Mockito.any());
        verify(booking).setStart(Mockito.any());
        verify(booking).setStatus(Mockito.any());
        verify(userDto).getId();
    }

    /**
     * Method under test: {@link BookingMapper#toBooking(BookingRequestDto)}
     */
    @Test
    void testToBooking() {
        Booking actualToBookingResult = bookingMapper.toBooking(new BookingRequestDto());
        assertNull(actualToBookingResult.getBooker());
        assertNull(actualToBookingResult.getStatus());
        assertNull(actualToBookingResult.getStart());
        assertNull(actualToBookingResult.getItem());
        assertNull(actualToBookingResult.getId());
        assertNull(actualToBookingResult.getEnd());
    }

    /**
     * Method under test: {@link BookingMapper#toBooking(BookingRequestDto)}
     */
    @Test
    void testToBooking2() {
        BookingRequestDto bookingRequestDto = mock(BookingRequestDto.class);
        when(bookingRequestDto.getEnd()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        when(bookingRequestDto.getStart()).thenReturn(LocalDate.of(1970, 1, 1).atStartOfDay());
        Booking actualToBookingResult = bookingMapper.toBooking(bookingRequestDto);
        assertNull(actualToBookingResult.getBooker());
        assertNull(actualToBookingResult.getStatus());
        assertNull(actualToBookingResult.getItem());
        assertEquals("00:00", actualToBookingResult.getStart().toLocalTime().toString());
        assertNull(actualToBookingResult.getId());
        assertEquals("1970-01-01", actualToBookingResult.getEnd().toLocalDate().toString());
        verify(bookingRequestDto).getEnd();
        verify(bookingRequestDto).getStart();
    }
}

