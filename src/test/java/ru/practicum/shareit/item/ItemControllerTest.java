package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.comment.dto.CommentRequestDto;
import ru.practicum.shareit.comment.dto.CommentResponseDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemRequestDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ItemController.class})
@ExtendWith(MockitoExtension.class)
class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    /**
     * Method under test: {@link ItemController#readItem(long, long)}
     */
    @Test
    void testReadItem() throws Exception {
        when(itemService.readItem(anyLong(), anyLong())).thenReturn(new ItemDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items/{itemId}", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"name\":null,\"description\":null,\"available\":null,\"owner\":null,\"requestId\":null,\"lastBooking\""
                                        + ":null,\"nextBooking\":null,\"comments\":null}"));
    }

    /**
     * Method under test: {@link ItemController#createItem(long, ItemRequestDto)}
     */
    @Test
    void testCreateItem() throws Exception {
        when(itemService.getAllItems(anyLong())).thenReturn(new ArrayList<>());

        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setAvailable(true);
        itemRequestDto.setDescription("The characteristics of someone or something");
        itemRequestDto.setName("Name");
        itemRequestDto.setRequestId(1L);
        String content = (new ObjectMapper()).writeValueAsString(itemRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ItemController#getAllAvailableItemsByText(String, long, Integer, Integer)}
     */
    @Test
    void testGetAllAvailableItemsByText() throws Exception {
        when(itemService.getAvailableItemsByText(Mockito.any(), anyLong(), Mockito.any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/items/search");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .param("text", "foo")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ItemController#updateItem(long, ItemDto, long)}
     */
    @Test
    void testUpdateItem() throws Exception {
        when(itemService.updateItem(anyLong(), Mockito.any(), anyLong())).thenReturn(new ItemDto());

        ItemDto itemDto = new ItemDto();
        itemDto.setAvailable(true);
        itemDto.setComments(new ArrayList<>());
        itemDto.setDescription("The characteristics of someone or something");
        itemDto.setId(1L);
        itemDto.setLastBooking(new BookingDto());
        itemDto.setName("Name");
        itemDto.setNextBooking(new BookingDto());
        itemDto.setRequestId(1L);
        String content = (new ObjectMapper()).writeValueAsString(itemDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/items/{itemId}", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":0,\"name\":null,\"description\":null,\"available\":null,\"owner\":null,\"requestId\":null,\"lastBooking\""
                                        + ":null,\"nextBooking\":null,\"comments\":null}"));
    }

    @Test
    void updateItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        ItemDto updated = itemController.updateItem(1L, ItemDto.builder().description("updated").build(), 1L);
        assertEquals(updated, itemController.readItem(1L, 1L));

    }

    @Test
    void deleteItem() {
        ItemDto itemDto = itemController.createItem(1L, ItemRequestDto.builder().build());
        assertEquals(itemDto, itemController.readItem(1L, 1L));

        itemController.deleteItem(1L, 1L);
        assertNull(itemController.readItem(1L, 1L));
    }

    /**
     * Method under test: {@link ItemController#deleteItem(long, long)}
     */
    @Test
    void testDeleteItem() throws Exception {
        doNothing().when(itemService).deleteItem(anyLong(), anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/items/{itemId}", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link ItemController#getAllItems(long)}
     */
    @Test
    void testGetAllItems() throws Exception {
        when(itemService.getAllItems(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/items")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ItemController#addComment(long, long, CommentRequestDto)}
     */
    @Test
    void testAddComment() throws Exception {
        when(itemService.addComment(Mockito.<Long>any(), Mockito.<Long>any(), Mockito.any()))
                .thenReturn(new CommentResponseDto());

        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setText("Text");
        String content = (new ObjectMapper()).writeValueAsString(commentRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/items/{itemId}/comment", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(itemController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"text\":null,\"authorName\":null,\"created\":null}"));
    }
}