import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Worked on by Vanessa and Vicky
 */
public class LibrarianTest {

    @Test
    void addBookTest() throws IOException {
        ArrayList<Book> bookList = new ArrayList<>();

        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test list is empty
        bookList.add(jbooks.get(3));
        jlibrarians.get(0).addBook(jbooks.get(3));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test new book added
        bookList.add(jbooks.get(4));
        jlibrarians.get(0).addBook(jbooks.get(4));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test new book added
        bookList.add(jbooks.get(5));
        jlibrarians.get(0).addBook(jbooks.get(5));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test another copy of a book is added
    }

    @Test
    void removeBookTest() throws IOException {
        ArrayList<Book> bookList = new ArrayList<>();

        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);

        jlibrarians.get(0).addBook(jbooks.get(3));
        jlibrarians.get(0).addBook(jbooks.get(4));
        bookList.add(jbooks.get(3));
        bookList.add(jbooks.get(4));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test list is not empty
        bookList.remove(jbooks.get(4));
        jlibrarians.get(0).removeBook(jbooks.get(4));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test list removes the specified book
        bookList.remove(jbooks.get(5));
        jlibrarians.get(0).removeBook(jbooks.get(5));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test nothing happens when book that is not at library is removed
        bookList.remove(jbooks.get(3));
        jlibrarians.get(0).removeBook(jbooks.get(3));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //Test list removes the specified book
        bookList.remove(jbooks.get(5));
        jlibrarians.get(0).removeBook(jbooks.get(5));

        assertEquals(bookList, jlibrarians.get(0).getBookList());  //list remains empty when trying to remove a book from empty list
    }

    @Test
    void freezeAccountTest() throws IOException {
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);

        jusers.get(1).frozen = true;

        assertFalse(jlibrarians.get(0).isFrozen(jusers.get(0)));    //Tests that an unfrozen account is unfrozen
        assertTrue(jlibrarians.get(0).isFrozen(jusers.get(1)));     //Tests that a frozen account is frozen

        assertTrue(jlibrarians.get(0).freezeAccount(jusers.get(0)));    //Tests that an unfrozen account can be frozen
        assertTrue(jlibrarians.get(0).freezeAccount(jusers.get(1)));    //Tests that a frozen account remains frozen
    }

    @Test
    void unfreezeAccountTest() throws IOException {

        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);
        jusers.get(1).frozen = true;

        assertFalse(jlibrarians.get(0).isFrozen(jusers.get(0)));    //Tests that an unfrozen account is unfrozen
        assertTrue(jlibrarians.get(0).isFrozen(jusers.get(1)));     //Tests that a frozen account is frozen

        assertFalse(jlibrarians.get(0).unfreezeAccount(jusers.get(0)));     //Tests that an unfrozen account remains unfrozen
        assertFalse(jlibrarians.get(0).unfreezeAccount(jusers.get(1)));     //Tests that a frozen account can be unfrozen
    }

    @Test
    void removeAccountTest() throws IOException {
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);

        ArrayList<User> userList = new ArrayList<>();

        userList.add(jusers.get(0));
        userList.add(jusers.get(1));
        userList.add(jusers.get(2));
        userList.add(jusers.get(3));
        userList.add(jusers.get(4));
        userList.add(jusers.get(5));
        userList.add(jusers.get(6));
        userList.add(jusers.get(7));

        assertEquals(userList, jlibrarians.get(0).getUserList()); //Tests that accounts are added when created

        jlibrarians.get(0).removeAccount(jusers.get(1));
        userList.remove(jusers.get(1));
        assertEquals(userList, jlibrarians.get(0).getUserList()); //Tests that a specific account can be removed

        jlibrarians.get(0).removeAccount(jusers.get(0));
        userList.remove(jusers.get(0));
        assertEquals(userList, jlibrarians.get(0).getUserList()); //Tests that a specific account (in this case the first account) can be removed
    }


    @Test
    void searchAccountTest() throws IOException {
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);

        assertEquals(jusers.get(0), jlibrarians.get(0).searchAccount(jusers.get(0)));  //Tests that an account that is in the list is found

        jlibrarians.get(0).removeAccount(jusers.get(1));

        assertNull(jlibrarians.get(0).searchAccount(jusers.get(1)));  //Tests that an account that is not in the list is not found

        jlibrarians.get(0).removeAccount(jusers.get(0));

        assertNull(jlibrarians.get(0).searchAccount(jusers.get(0)));  //Tests that an account that has been removed is not found
    }
}



