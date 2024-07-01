
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Worked on by Vicky
 */
public class TimeTest {
    @Test
    public void passTimeTest() throws IOException {
        List<Library> jlibraries = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/libraries.json", Library.class);
        List<Book> jbooks = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/books.json", Book.class);
        List<User> jusers = JsonUtil.listFromJsonFile("src/test/java/edu/ithaca/barr/users.json", User.class);

        jlibraries.get(0).allBooks.add(jbooks.get(10));   //Add book
        jlibraries.get(0).allBooks.add(jbooks.get(11));   //Add book
        jlibraries.get(0).allBooks.add(jbooks.get(12));   //Add book

        jlibraries.get(0).checkOutBook(jusers.get(0), jbooks.get(10));    //User checks out a book
        Time t1 = new Time(jusers.get(0), jbooks.get(10));

        assertEquals(7, t1.days_left);    //Test that a newly checked out book has 7 days left

        t1.passTime();    //A day passes
        assertEquals(6, t1.days_left);    //Test that after a day, the book has 6 days left

        t1.passTime();    //A day passes
        assertEquals(5, t1.days_left);    //Test that after another day, the book has 5 days left

        jlibraries.get(0).checkOutBook(jusers.get(1), jbooks.get(11));   //A different user checks out a book
        Time t2 = new Time(jusers.get(1), jbooks.get(11));
        jlibraries.get(0).checkOutBook(jusers.get(1), jbooks.get(12));    //A user who has checked out a book before checks out another
        Time t3 = new Time(jusers.get(1), jbooks.get(10));
        assertEquals(7, t2.days_left);    //Newly checked out book has 7 days left
        assertEquals(7, t3.days_left);    //Newly checked out book has 7 days left

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(4, t1.days_left);    //Tests that after a day a book checked out a bit ago is at its correct time for days left
        assertEquals(6, t2.days_left);    //Tests that after a day, newly checked out book has 6 days left
        assertEquals(6, t3.days_left);    //Tests that after a day, newly checked out book has 6 days left

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(3, t1.days_left);    //Test time decreases after a day
        assertEquals(5, t2.days_left);    //Test time decreases after a day
        assertEquals(5, t3.days_left);    //Test time decreases after a day

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(2, t1.days_left);    //Test time decreases after a day
        assertEquals(4, t2.days_left);    //Test time decreases after a day
        assertEquals(4, t3.days_left);    //Test time decreases after a day

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(1, t1.days_left);    //Test time decreases after a day
        assertEquals(3, t2.days_left);    //Test time decreases after a day
        assertEquals(3, t3.days_left);    //Test time decreases after a day

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(0, t1.days_left);    //Test time decreases after a day
        assertEquals(2, t2.days_left);    //Test time decreases after a day
        assertEquals(2, t3.days_left);    //Test time decreases after a day

        t1.passTime();    //A day passes
        t2.passTime();    //A day passes
        t3.passTime();    //A day passes
        assertEquals(-1, t1.days_left);   //Test that when a book is overdue, time is the negative amount of days overdue
        assertEquals(1, t2.days_left);    //Test time decreases after a day
        assertEquals(1, t3.days_left);    //Test time decreases after a day
    }
}
