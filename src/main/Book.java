
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Worked on by Gio, Christian, Vanessa and Vicky
 */
public class Book {
    
    private int id;
    private String title;
    private String author;
    public int numCopies;

    @JsonCreator
    public Book(@JsonProperty("id") int id, @JsonProperty("title") String title,
                @JsonProperty("author") String author,
                @JsonProperty("numCopies") int numCopies) {
        this.id=id;
        this.title=title;
        this.author=author;
        this.numCopies=numCopies;
    }

    /**
     * Gets the number of copies of a book
     * @return the number of copies
     */
    public int getNumCopies(){
        return numCopies;
    }

    /**
     * gets the book ID
     * @return the book ID
     */
    public int getID(){
        return id;
    }

    /**
     * gets the book title
     * @return the book title
     */
    public String getTitle(){
        return title;
    }

    /**
     * gets the book author
     * @return the book's author
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Prints the info about a book
     * @return a string with the info about the book
     */
    public String returnInfo(){
        return "ID: "+id+", Title: "+title+", Author: "+author+", Number of Copies: "+numCopies;
    }

    /**
     * Change the number of copies of a book
     * @param book the book that's number of copies is changing
     * @return the new number of copies
     */
    public int setNumCopies(Book book){
        int currNumCopies = book.getNumCopies() - 1;
        numCopies = currNumCopies;
        return currNumCopies;
    }

    
    public void checkOutBook(boolean b) {
    }


}
