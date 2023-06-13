package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ShareitExceptionHandlerTest {
    /**
     * Method under test: {@link ShareitExceptionHandler#handleNotExistingFilm(MethodArgumentTypeMismatchException)}
     */
    @Test
    void testHandleNotExistingFilm() {
        ShareitExceptionHandler shareitExceptionHandler = new ShareitExceptionHandler();
        Class<Object> requiredType = Object.class;
        Map<String, String> actualHandleNotExistingFilmResult = shareitExceptionHandler.handleNotExistingFilm(
                new MethodArgumentTypeMismatchException("Value", requiredType, "0123456789ABCDEF", null, new Throwable()));
        assertEquals(1, actualHandleNotExistingFilmResult.size());
        assertEquals("Unknown 0123456789ABCDEF: Value", actualHandleNotExistingFilmResult.get("error"));
    }

    /**
     * Method under test: {@link ShareitExceptionHandler#handleNotFoundEntity(EntityNotFoundException)}
     */
    @Test
    void testHandleNotFoundEntity() {
        ShareitExceptionHandler shareitExceptionHandler = new ShareitExceptionHandler();
        Map<String, String> actualHandleNotFoundEntityResult = shareitExceptionHandler
                .handleNotFoundEntity(new EntityNotFoundException("Entity", 1L));
        assertEquals(1, actualHandleNotFoundEntityResult.size());
        assertEquals("Entity Entity with id 1 was not found", actualHandleNotFoundEntityResult.get("error"));
    }

    /**
     * Method under test: {@link ShareitExceptionHandler#handleNotValidEntity(EntityNotValidException)}
     */
    @Test
    void testHandleNotValidEntity() {
        ShareitExceptionHandler shareitExceptionHandler = new ShareitExceptionHandler();
        Map<String, String> actualHandleNotValidEntityResult = shareitExceptionHandler
                .handleNotValidEntity(new EntityNotValidException("Entity", "Field"));
        assertEquals(1, actualHandleNotValidEntityResult.size());
        assertEquals("Entity Entity has invalid field Field", actualHandleNotValidEntityResult.get("error"));
    }

    /**
     * Method under test: {@link ShareitExceptionHandler#handleDuplicatedEntity(EntityDuplicateException)}
     */
    @Test
    void testHandleDuplicatedEntity() {
        ShareitExceptionHandler shareitExceptionHandler = new ShareitExceptionHandler();
        Map<String, String> actualHandleDuplicatedEntityResult = shareitExceptionHandler
                .handleDuplicatedEntity(new EntityDuplicateException("Entity", "jane.doe@example.org"));
        assertEquals(1, actualHandleDuplicatedEntityResult.size());
        assertEquals("Entity Entity has duplicated field jane.doe@example.org",
                actualHandleDuplicatedEntityResult.get("error"));
    }

    /**
     * Method under test: {@link ShareitExceptionHandler#handleUnprocessedExceptions(Throwable)}
     */
    @Test
    void testHandleUnprocessedExceptions() {
        ShareitExceptionHandler shareitExceptionHandler = new ShareitExceptionHandler();
        Map<String, String> actualHandleUnprocessedExceptionsResult = shareitExceptionHandler
                .handleUnprocessedExceptions(new IOException("Server got bad parameter: {}"));
        assertEquals(1, actualHandleUnprocessedExceptionsResult.size());
        assertEquals("Server got bad parameter: {}", actualHandleUnprocessedExceptionsResult.get("error"));
    }
}

