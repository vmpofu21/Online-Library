import com.fasterxml.jackson.core.JsonProcessingException;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Worked on my Gio, Christian, Vanessa and Vicky
 */
public class LibraryTest {
    
    @Test
    public void createAccountTest() throws IOException {
        //create users and passwords, assert they are in the system and have correct values

        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);

        assertTrue(jlibraries.get(0).createAccount("John", "code", "123"));   //Account can be created

        assertFalse(jlibraries.get(0).createAccount("Barr", "code", "456"));  //Account not created when same username

        assertFalse(jlibraries.get(0).createAccount("Barr", "coding", "123"));    //Account not created when same password

        assertTrue(jlibraries.get(0).createAccount("John", "usernamer", "passwordr"));   //Another account can be created

        assertTrue(jlibraries.get(0).createAccount("Barr", "passr", "wordr"));   //Another account can be created
    }

    @Test
    public void searchByTitleTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);

        jlibraries.get(0).allBooks.add(jbooks.get(3));
        jlibraries.get(0).allBooks.add(jbooks.get(3));
        jlibraries.get(0).allBooks.add(jbooks.get(4));
        jlibraries.get(0).allBooks.add(jbooks.get(5));

        // Test search with existing title
        ArrayList<Book> result1 = (ArrayList<Book>) jlibraries.get(0).searchByTitle("Book1");
        assertEquals(2, result1.size());

        // Test search with non-existing title
        ArrayList<Book> result2 = (ArrayList<Book>) jlibraries.get(0).searchByTitle("Book4");
        assertEquals(0, result2.size());
    }

    @Test
    public void searchByAuthorTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);

        jlibraries.get(0).allBooks.add(jbooks.get(3));
        jlibraries.get(0).allBooks.add(jbooks.get(4));
        jlibraries.get(0).allBooks.add(jbooks.get(5));

        // Test search with existing author
        ArrayList<Book> result1 = (ArrayList<Book>) jlibraries.get(0).searchByAuthor("John Barr");
        assertEquals(2, result1.size());

        // Test search with non-existing author
        ArrayList<Book> result2 = (ArrayList<Book>) jlibraries.get(0).searchByAuthor("Author 3");
        assertEquals(0, result2.size());
    }

    @Test
    void checkOutTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        jlibraries.get(0).allBooks.add(jbooks.get(6));
        jlibraries.get(0).allBooks.add(jbooks.get(7));
        jlibraries.get(0).allBooks.add(jbooks.get(8));
        jlibraries.get(0).allBooks.add(jbooks.get(9));

        assertEquals(5, jbooks.get(7).getNumCopies()); //Tests how many copies before book is checked out

        jlibraries.get(0).checkOutBook(jusers.get(1), jbooks.get(7));
        jlibraries.get(0).checkOutBook(jusers.get(2), jbooks.get(8));

        assertEquals(2, jlibraries.get(0).checkedOutBooks.size()); //Tests that when two books are checked out, that two books are listed as checked out
        assertEquals(jbooks.get(8) , jlibraries.get(0).checkedOutBooks.get(jusers.get(2).getID())); //Tests that a user checked out the book they wanted to check out
        assertEquals(4, jbooks.get(7).getNumCopies()); //Tests that the number of copies decreases by one when book is checked out
        assertThrows(IllegalArgumentException.class, ()->   jlibraries.get(0).checkOutBook(jusers.get(2), jbooks.get(9))); //Tests that a book not checked out is not on the list
    }
        
    

    @Test
    void getCheckedOutListTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        jlibraries.get(0).allBooks.add(jbooks.get(6));
        jlibraries.get(0).allBooks.add(jbooks.get(7));
        jlibraries.get(0).allBooks.add(jbooks.get(8));
        jlibraries.get(0).allBooks.add(jbooks.get(9));

        jlibraries.get(0).checkOutBook(jusers.get(0), jbooks.get(7));   //Book checked out
        jlibraries.get(0).checkOutBook(jusers.get(3), jbooks.get(8));   //Book checked out
        jlibraries.get(0).checkOutBook(jusers.get(2), jbooks.get(6));   //Book checked out
        jlibraries.get(0).checkOutBook(jusers.get(4), jbooks.get(7));   //Book checked out
        jlibraries.get(0).checkOutBook(jusers.get(1), jbooks.get(6));   //Book checked out

        assertEquals(5, jlibraries.get(0).checkedOutBooks.size());  //Tests that the list is the size of the number of checked out books
    }

    @Test
    void reserveBookTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        jlibraries.get(0).allBooks.add(jbooks.get(6));
        jlibraries.get(0).allBooks.add(jbooks.get(7));
        jlibraries.get(0).allBooks.add(jbooks.get(9));

        assertEquals(jusers.get(2), jlibraries.get(0).reserveBook(jusers.get(2), jbooks.get(7))); //Tests that a book available to be reserved can be reserved
        assertThrows(IllegalArgumentException.class, () -> jlibraries.get(0).reserveBook(jusers.get(1), jbooks.get(9))); //Tests that a book not available to be reserved throws an error message
    }

    @Test
    public void returnTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        jlibraries.get(0).allBooks.add(jbooks.get(0));
        jlibraries.get(0).allBooks.add(jbooks.get(1));

        // Test return of checked-out book
        assertFalse(jlibraries.get(0).returnBook(jusers.get(0), jbooks.get(1))); //Tests that a book not checked out cannot be returned

        jlibraries.get(0).checkOutBook(jusers.get(1), jbooks.get(1));
        assertFalse(jlibraries.get(0).returnBook(jusers.get(0), jbooks.get(1))); //Tests that a checked out book can be returned

    }

}
