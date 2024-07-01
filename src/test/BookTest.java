
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Worked on by Gio, Vanessa and Vicky
 */
public class BookTest {
    
    @Test
    void returnInfoTest() throws IOException {

        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);

        assertEquals("ID: 0, Title: A, Author: B, Number of Copies: 0", jbooks.get(0).returnInfo());    //Tests that the information for a specific book is printed out
        assertEquals("ID: 1, Title: C, Author: D, Number of Copies: 1", jbooks.get(1).returnInfo());    //Tests that the information for a different specific book is printed out
        assertEquals("ID: 2, Title: E, Author: F, Number of Copies: 5", jbooks.get(2).returnInfo());    //Tests that the information for another specific book is printed out

    }
}
