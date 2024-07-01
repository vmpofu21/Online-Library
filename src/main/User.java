
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Worked on by Gio, Vanessa and Vicky
 */
public class User {

    private int id;
    private String name;
    private String username;
    public String password;
    public boolean frozen;
    public List<Book> checkedOutList = new ArrayList<>();
    public List<Book> reservedList = new ArrayList<>();

    @JsonCreator
    public User(@JsonProperty("id") int id,@JsonProperty("name") String name,
                @JsonProperty("username") String username,@JsonProperty("password") String password){
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        boolean frozen = false;
        Library.userList.add(this);
        Library.usernameList.add(username);
        Library.passwordList.add(password);
    }

    /**
     * Checks to see if an account is frozen
     * return: True if frozen, false if unfrozen
     */
    public boolean isFrozen(){
        return frozen;
    }

    /**
     * gets the id
     * @return id of the user
     */
    public int getID(){
        return id;
    }

    /**
     * gets the name
     * @return name of the user
     */
    public String getName(){
        return name;
    }

    /**
     * gets the username
     * @return username of the user
     */
    public String getUserName(){
        return username;
    }

    /**
     * gets the password
     * @return password of the user
     */
    public String getPassword(){
        return password;
    }

    /**
     * gets User's list of checked out books
     * @return list of checked out books
     */
    public List<Book> getCheckedOutList(){
        return checkedOutList;
    }

    /**
     * sets the password
     * @param newPassword the new password
     */
    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    /**
     * sets the username
     * @param newUsername the new username
     */
    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    /**
     * changes the password
     * @param user the user changing their password
     * @param newPassword the new password
     * @param oldPassword the old password
     * @return A message if the password was changed or not
     */
    public String changePassword(User user, String newPassword, String oldPassword){
        if(user.getPassword().equals(oldPassword)){
            user.setPassword(newPassword);
            if(user.getPassword().equals(newPassword)){
                System.out.println("password changed");
                return newPassword;
            }

            else{
                System.out.println("password change failed!!");
            }
        }


        System.out.println("Old password is wrong!!");
        String password = user.getPassword();
        return password;

    }

    /**
     * changes the username
     * @param user the user changing their username
     * @param newUsername new username
     * @param oldUsername the old username
     * @return A message if the username was changed or not
     */
    public String changeUsername(User user, String newUsername, String oldUsername){
        if(user.getUserName().equals(oldUsername)){
            setUsername(newUsername);
            if(user.getUserName().equals(newUsername)){
                System.out.println("username changed");
                return newUsername;
            }

            else{
                System.out.println("username change failed!!");
            }
        }


        System.out.println("Old username is wrong!!");
        String username = user.getUserName();
        return username;
    }

    /**
     * gets User's list of reserved books
     * @return list of reserved books
     */
    public Book getReservedBooks(){
        return Library.reservedBooks.get(this);
    }
}
