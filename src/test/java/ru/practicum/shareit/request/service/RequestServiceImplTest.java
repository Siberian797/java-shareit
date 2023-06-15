package ru.practicum.shareit.request.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.exception.EntityNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.utils.ItemMapper;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.utils.UserMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RequestServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RequestServiceImplTest {
    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private RequestRepository requestRepository;

    @Autowired
    private RequestServiceImpl requestServiceImpl;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ItemMapper itemMapper;

    /**
     * Method under test: {@link RequestServiceImpl#createRequest(RequestRequestDto, long)}
     */
    @Test
    void testCreateRequest() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);
        when(requestRepository.save(Mockito.any())).thenReturn(request);
        RequestResponseDto actualCreateRequestResult = requestServiceImpl
                .createRequest(new RequestRequestDto("The characteristics of someone or something"), 1L);
        actualCreateRequestResult.setRequester(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        assertEquals("00:00", actualCreateRequestResult.getCreated().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualCreateRequestResult.getDescription());
        assertTrue(actualCreateRequestResult.getItems().isEmpty());
        assertEquals(1L, actualCreateRequestResult.getId().longValue());
        UserDto requester2 = actualCreateRequestResult.getRequester();
        assertEquals("Name", requester2.getName());
        assertEquals(1L, requester2.getId());
        assertEquals("jane.doe@example.org", requester2.getEmail());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(requestRepository).save(Mockito.any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#createRequest(RequestRequestDto, long)}
     */
    @Test
    void testCreateRequest2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(requestRepository.save(Mockito.any())).thenThrow(new EntityNotFoundException("Entity", 1L));
        assertThrows(EntityNotFoundException.class, () -> requestServiceImpl
                .createRequest(new RequestRequestDto("The characteristics of someone or something"), 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(requestRepository).save(Mockito.any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#readRequest(long, long)}
     */
    @Test
    void testReadRequest() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ArrayList<Item> itemList = new ArrayList<>();
        when(itemRepository.findByRequestIdOrderByRequestCreatedTimeAsc(Mockito.<Long>any())).thenReturn(itemList);

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);
        Optional<Request> ofResult2 = Optional.of(request);
        when(requestRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        RequestResponseDto actualReadRequestResult = requestServiceImpl.readRequest(1L, 1L);
        actualReadRequestResult.setRequester(UserDto.builder().id(1L).name("Name").email("jane.doe@example.org").build());
        assertEquals("00:00", actualReadRequestResult.getCreated().toLocalTime().toString());
        assertEquals("The characteristics of someone or something", actualReadRequestResult.getDescription());
        assertEquals(1L, actualReadRequestResult.getId().longValue());
        UserDto requester2 = actualReadRequestResult.getRequester();
        assertEquals("Name", requester2.getName());
        assertEquals(1L, requester2.getId());
        assertEquals("jane.doe@example.org", requester2.getEmail());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findByRequestIdOrderByRequestCreatedTimeAsc(Mockito.<Long>any());
        verify(requestRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#readRequest(long, long)}
     */
    @Test
    void testReadRequest2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findByRequestIdOrderByRequestCreatedTimeAsc(Mockito.<Long>any()))
                .thenThrow(new EntityNotFoundException("Entity", 1L));

        User requester = new User();
        requester.setEmail("jane.doe@example.org");
        requester.setId(1L);
        requester.setName("Name");

        Request request = new Request();
        request.setCreatedTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        request.setDescription("The characteristics of someone or something");
        request.setId(1L);
        request.setRequester(requester);
        Optional<Request> ofResult2 = Optional.of(request);
        when(requestRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(EntityNotFoundException.class, () -> requestServiceImpl.readRequest(1L, 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findByRequestIdOrderByRequestCreatedTimeAsc(Mockito.<Long>any());
        verify(requestRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#getAllRequests(PageRequest, long)}
     */
    @Test
    void testGetAllRequests() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(requestRepository.findByRequesterIdNot(Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        assertTrue(requestServiceImpl.getAllRequests(null, 1L).isEmpty());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any());
        verify(requestRepository).findByRequesterIdNot(Mockito.<Long>any(), Mockito.any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#getAllRequests(PageRequest, long)}
     */
    @Test
    void testGetAllRequests2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(requestRepository.findByRequesterIdNot(Mockito.<Long>any(), Mockito.any()))
                .thenThrow(new EntityNotFoundException("Entity", 1L));
        assertThrows(EntityNotFoundException.class, () -> requestServiceImpl.getAllRequests(null, 1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(requestRepository).findByRequesterIdNot(Mockito.<Long>any(), Mockito.any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#getAllRequestsByUser(long)}
     */
    @Test
    void testGetAllRequestsByUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(requestRepository.findByRequesterIdOrderByCreatedTimeAsc(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        assertTrue(requestServiceImpl.getAllRequestsByUser(1L).isEmpty());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(itemRepository).findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any());
        verify(requestRepository).findByRequesterIdOrderByCreatedTimeAsc(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RequestServiceImpl#getAllRequestsByUser(long)}
     */
    @Test
    void testGetAllRequestsByUser2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setName("Name");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(itemRepository.findByRequestIdInOrderByRequestCreatedTimeAsc(Mockito.any()))
                .thenReturn(new ArrayList<>());
        when(requestRepository.findByRequesterIdOrderByCreatedTimeAsc(Mockito.<Long>any()))
                .thenThrow(new EntityNotFoundException("Entity", 1L));
        assertThrows(EntityNotFoundException.class, () -> requestServiceImpl.getAllRequestsByUser(1L));
        verify(userRepository).findById(Mockito.<Long>any());
        verify(requestRepository).findByRequesterIdOrderByCreatedTimeAsc(Mockito.<Long>any());
    }
}