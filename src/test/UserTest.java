
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Worked on by Vanessa and Vicky
 */
public class UserTest {

    @Test
    public void changePasswordUsernameTest() throws IOException {
        //create a bunch of user objects
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        assertEquals("Vanessa99", jusers.get(5).changePassword(jusers.get(5), "Vanessa99", jusers.get(5).getPassword()));   //Test password can be changed
        assertEquals("VictoriaConrad", jusers.get(6).changeUsername(jusers.get(6), "VictoriaConrad", jusers.get(6).getUserName()));    //Test username can be changed
        assertEquals(jusers.get(7).getPassword(), jusers.get(7).changePassword(jusers.get(7), "Cordy2022", jusers.get(6).getPassword()));    //Test password does not change when incorrect old password given
        assertEquals(jusers.get(5).getUserName(), jusers.get(5).changeUsername(jusers.get(5), "VanessaMpofu", jusers.get(4).getUserName()));     //Test username does not change when incorrect old username give
    }


    @Test
    public void getUserListTest() throws IOException {
        List<Librarian> jlibrarians = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/librarians.json", Librarian.class);
        ArrayList<User> userList = new ArrayList<>();

        User user1 = new User(1, "John", "abc", "123"); //Tests when there is a single user
        userList.add(user1);
        assertEquals(userList, jlibrarians.get(0).getUserList());

        User user2 = new User(2, "Barr", "def", "456"); //Tests when there are two users
        userList.add(user2);
        assertEquals(userList, jlibrarians.get(0).getUserList());

        User user3 = new User(3, "jBarr", "ghi", "789"); //Test when there are three user
        userList.add(user3);
        assertEquals(userList, jlibrarians.get(0).getUserList());
    }

    @Test
    public void getCheckedOutListTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);

        List<Book> checkedOut1 = new ArrayList<>();
        List<Book> checkedOut2 = new ArrayList<>();


        jlibraries.get(0).allBooks.add(jbooks.get(10));
        jlibraries.get(0).allBooks.add(jbooks.get(12));
        jlibraries.get(0).allBooks.add(jbooks.get(9));

        assertEquals(checkedOut1, jusers.get(5).getCheckedOutList()); //Test list is empty when no books checked out

        checkedOut1.add(jbooks.get(10));
        jlibraries.get(0).checkOutBook(jusers.get(5), jbooks.get(10));
        assertEquals(checkedOut1, jusers.get(5).getCheckedOutList()); //Test list has the book that was just checked out

        checkedOut1.add(jbooks.get(12));
        jlibraries.get(0).checkOutBook(jusers.get(5), jbooks.get(12));
        assertEquals(checkedOut1, jusers.get(5).getCheckedOutList()); //Test list has the books that have been checked out

        checkedOut2.add(jbooks.get(12));
        jlibraries.get(0).checkOutBook(jusers.get(6), jbooks.get(12));
        assertEquals(checkedOut2, jusers.get(6).getCheckedOutList()); //Test books are checked out only for the specific user

        assertThrows(IllegalArgumentException.class, ()->   jlibraries.get(0).checkOutBook(jusers.get(6), jbooks.get(9)));   //Test list does not change if user tries to check out a book with no copies left
    }

    @Test
    public void getReservedBooksTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);

        HashMap<User,Book> reserved = new HashMap<>();

        jlibraries.get(0).allBooks.add(jbooks.get(13));
        jlibraries.get(0).allBooks.add(jbooks.get(14));
        jlibraries.get(0).allBooks.add(jbooks.get(15));

        assertNull(jusers.get(5).getReservedBooks()); //Test list is empty when no books reserved

        assertThrows(IllegalArgumentException.class, ()->   jlibraries.get(0).reserveBook(jusers.get(5), jbooks.get(13)));    //Tests list is empty when a book cannot be reserved

        jlibraries.get(0).reserveBook(jusers.get(5), jbooks.get(14));
        reserved.put(jusers.get(5), jbooks.get(14));
        assertEquals(reserved.get(jusers.get(5)), jusers.get(5).getReservedBooks());  //Tests list has one book when one book is successfully reserved

        assertThrows(IllegalArgumentException.class, ()->   jlibraries.get(0).reserveBook(jusers.get(6), jbooks.get(13))); //Tests that a book that is already reserved cannot be reserved by another person

        jlibraries.get(0).reserveBook(jusers.get(6), jbooks.get(15));
        reserved.put(jusers.get(6), jbooks.get(15));
        assertEquals(reserved.get(jusers.get(6)), jusers.get(6).getReservedBooks());  //Tests that the reserved list is only for the specified user
    }
}
