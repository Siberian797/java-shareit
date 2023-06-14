package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.dto.BookingResponseDto;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.booking.status.BookingState;
import ru.practicum.shareit.booking.utils.BookingMapper;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BookingController.class})
@ExtendWith(SpringExtension.class)
class BookingControllerTest {
    @Autowired
    private BookingController bookingController;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private ItemMapper itemMapper;
    @MockBean
    private UserMapper userMapper;

    /**
     * Method under test: {@link BookingController#createBooking(long, BookingRequestDto)}
     */
    @Test
    void testCreateBooking() {
        BookingService bookingService = mock(BookingService.class);
        BookingResponseDto bookingResponseDto = new BookingResponseDto();
        when(bookingService.createBooking(Mockito.any(), anyLong())).thenReturn(bookingResponseDto);
        BookingController bookingController = new BookingController(bookingService);
        assertSame(bookingResponseDto, bookingController.createBooking(1L, new BookingRequestDto()));
        verify(bookingService).createBooking(Mockito.any(), anyLong());
    }

    /**
     * Method under test: {@link BookingController#readBooking(long, Long)}
     */
    @Test
    void testReadBooking() throws Exception {
        when(bookingService.readBooking(anyLong(), anyLong())).thenReturn(new BookingResponseDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookings/{bookingId}", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"start\":null,\"end\":null,\"item\":null,\"booker\":null,\"status\":null}"));
    }

    /**
     * Method under test: {@link BookingController#readBooking(long, Long)}
     */
    @Test
    void testReadBooking2() throws Exception {
        when(bookingService.getBookings(Mockito.any(), anyLong(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(bookingService.readBooking(anyLong(), anyLong())).thenReturn(new BookingResponseDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/bookings/{bookingId}", "", "Uri Variables")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link BookingController#updateBooking(long, boolean, long)}
     */
    @Test
    void testUpdateBooking() throws Exception {
        when(bookingService.updateBooking(anyLong(), anyBoolean(), anyLong())).thenReturn(new BookingResponseDto());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/bookings/{bookingId}", 1L);
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("approved", String.valueOf(true))
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(bookingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"start\":null,\"end\":null,\"item\":null,\"booker\":null,\"status\":null}"));
    }

    /**
     * Method under test: {@link BookingController#getUserBookings(long, BookingState, Integer, Integer)}
     */
    @Test
    void testGetUserBookings() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));
        assertTrue((new BookingController(
                new BookingServiceImpl(new BookingMapper(), bookingRepository, userRepository, mock(ItemRepository.class), itemMapper, userMapper)))
                .getUserBookings(1L, BookingState.ALL, 1, 3)
                .isEmpty());
        verify(bookingRepository).findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingController#getUserBookings(long, BookingState, Integer, Integer)}
     */
    @Test
    void testGetUserBookings2() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findByBookerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findByBookerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));
        assertTrue((new BookingController(
                new BookingServiceImpl(new BookingMapper(), bookingRepository, userRepository, mock(ItemRepository.class), itemMapper, userMapper)))
                .getUserBookings(1L, BookingState.CURRENT, 1, 3)
                .isEmpty());
        verify(bookingRepository).findByBookerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingController#getUserItems(long, BookingState, Integer, Integer)}
     */
    @Test
    void testGetUserItems() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));
        assertTrue((new BookingController(
                new BookingServiceImpl(new BookingMapper(), bookingRepository, userRepository, mock(ItemRepository.class), itemMapper, userMapper)))
                .getUserItems(1L, BookingState.ALL, 1, 3)
                .isEmpty());
        verify(bookingRepository).findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link BookingController#getUserItems(long, BookingState, Integer, Integer)}
     */
    @Test
    void testGetUserItems2() {
        BookingRepository bookingRepository = mock(BookingRepository.class);
        when(bookingRepository.findByItemOwnerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(bookingRepository.findByItemOwnerIdOrderByStartDesc(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(Optional.of(user));
        assertTrue((new BookingController(
                new BookingServiceImpl(new BookingMapper(), bookingRepository, userRepository, mock(ItemRepository.class), itemMapper, userMapper)))
                .getUserItems(1L, BookingState.CURRENT, 1, 3)
                .isEmpty());
        verify(bookingRepository).findByItemOwnerIdAndStartLessThanAndEndGreaterThanOrderByStartDesc(Mockito.<Long>any(),
                Mockito.any(), Mockito.any(), Mockito.any());
        verify(userRepository).findById(Mockito.<Long>any());
    }
}