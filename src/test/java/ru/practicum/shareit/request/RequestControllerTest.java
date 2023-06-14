package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.practicum.shareit.request.dto.RequestRequestDto;
import ru.practicum.shareit.request.dto.RequestResponseDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.utils.CommonConstants;

import java.util.ArrayList;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RequestController.class})
@ExtendWith(SpringExtension.class)
class RequestControllerTest {
    @Autowired
    private RequestController requestController;

    @MockBean
    private RequestService requestService;

    /**
     * Method under test: {@link RequestController#createRequest(long, RequestRequestDto)}
     */
    @Test
    void testCreateRequest2() throws Exception {
        when(requestService.getAllRequestsByUser(anyLong())).thenReturn(new ArrayList<>());

        RequestRequestDto requestRequestDto = new RequestRequestDto();
        requestRequestDto.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(requestRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RequestController#readRequest(Long, long)}
     */
    @Test
    void testReadRequest() throws Exception {
        when(requestService.readRequest(anyLong(), anyLong())).thenReturn(new RequestResponseDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests/{requestId}", 1L)
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"created\":null,\"description\":null,\"requester\":null,\"items\":null}"));
    }

    /**
     * Method under test: {@link RequestController#readRequest(Long, long)}
     */
    @Test
    void testReadRequest2() throws Exception {
        when(requestService.getAllRequestsByUser(anyLong())).thenReturn(new ArrayList<>());
        when(requestService.readRequest(anyLong(), anyLong())).thenReturn(new RequestResponseDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/requests/{requestId}", "", "Uri Variables")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RequestController#getAllRequestsByUser(long)}
     */
    @Test
    void testGetAllRequestsByUser() throws Exception {
        when(requestService.getAllRequestsByUser(anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RequestController#createRequest(long, RequestRequestDto)}
     */
    @Test
    void testCreateRequest() throws Exception {
        when(requestService.getAllRequestsByUser(anyLong())).thenReturn(new ArrayList<>());

        RequestRequestDto requestRequestDto = new RequestRequestDto();
        requestRequestDto.setDescription("The characteristics of someone or something");
        String content = (new ObjectMapper()).writeValueAsString(requestRequestDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/requests")
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RequestController#getAllRequests(long, Integer, Integer)}
     */
    @Test
    void testGetAllRequests() throws Exception {
        when(requestService.getAllRequests(Mockito.any(), anyLong())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/requests/all");
        MockHttpServletRequestBuilder paramResult = getResult.param("from", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1))
                .header(CommonConstants.ID_OF_USER_WHO_ADDS_HEADER, 1L);
        MockMvcBuilders.standaloneSetup(requestController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

