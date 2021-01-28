import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class AccessControl is an important part of this access control system.
 * It makes a new AccessControl object, and it reports whether a given user
 * name/password pair matches any user in the users ArrayList. It allow the
 * current use log out their account, and update their passports. This class
 * also authorize the admin add or remove users and take or give administration
 * to one of the user.
 * 
 * AccessControl: Any non-static field will be initialized here. It also checks
 * whether each class variable has been initialized and, if not, initialize
 * them.
 * 
 * isValidLogin: This method returns true if the user name/password pair matches
 * any user in the users ArrayList and false otherwise. It relies on the static
 * users ArrayList, and its only job is to determine whether a user
 * name/password pair is valid.
 * 
 * setCurrentUser: This method will not be used by any other method in
 * AccessControl, but it will be very useful for writing test methods.
 * 
 * logout: This method is to log out the current user, and set the currentUser
 * to null.
 * 
 * changePassword: This method allow users to update their passport. modify the
 * currentUser’s password to be the given newPassword.
 *
 * addUser: There will be two addUser method. One of them is to create a new
 * user with the default password and another one is to create a new user and
 * specify their administration status.
 * 
 * removeUser: This method remove user from the array list.The user name should
 * be unique.
 * 
 * giveAdmin: This method authorize the user as the administration.
 * 
 * takeAdmin: This method take the user off from the administration place.
 * 
 * resetPassword: It reset the user passport to the default passport.
 * 
 * loginScreen: It help user log in into the access control system. This method
 * consists of a single while(true) loop.
 * 
 * sessionScreen: This method is one of the driver method in this program. It
 * consists mostly of a single while loop. It set the currentUser to the user
 * object matching the user name parameter. The function of this method is to
 * set the currentUser and allow them to changePassword or logout
 */
public class AccessControl {

	private static ArrayList<User> users = new ArrayList<User>(); // An ArrayList of valid users.
	private User currentUser; // Who is currently logged in, if anyone?
	private static final String DEFAULT_PASSWORD = "changeme"; // Default password given to
	// new users or when we reset a user's password.Not be able to modify within the
	// constructor.

	/**
	 * A no-parameter constructor
	 */
	public AccessControl() {
		users.add(new User("admin", "root", true));
		currentUser = null;
	}

	/**
	 * Report whether a given user name/password pair is a valid login
	 */
	public static boolean isValidLogin(String username, String password) {

		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getUsername().equals(username)) { // check if the user name is pair matches
				// with any user in the users ArrayList
				return users.get(i).isValidLogin(password);
			}

		return false;
	}

	/**
	 * A mutator you can use to write tests without simulating user input
	 */
	public void setCurrentUser(String username) {
		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getUsername().equals(username)) {
				currentUser = users.get(i); // set the user as current user
				break;
			}
	}

	/**
	 * Log out the current user
	 */
	public void logout() {
		currentUser = null; // set current user as null
	}

	/**
	 * Change the current user's password
	 */
	public void changePassword(String newPassword) {
		String username = currentUser.getUsername();
		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getUsername().equals(username)) {
				users.get(i).setPassword(newPassword); // Update user passport
				break;
			}
	}

	/**
	 * Create a new user with the default password and isAdmin==false Create a new
	 * user (non-admin)
	 */
	public boolean addUser(String username) {
		if (currentUser == null || !currentUser.getIsAdmin())
			return false;
		// return false if the current user is not an admin or there isn't a current
		// user
		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getUsername().equals(username))
				return false;
		users.add(new User(username, DEFAULT_PASSWORD, false));
		return true;
	}

	/**
	 * Create a new user and specify their admin status
	 */
	public boolean addUser(String username, boolean isAdmin) {
		if (!currentUser.getIsAdmin())
			return false;

		for (int i = 0; i < users.size(); i++)
			if (users.get(i).getUsername().equals(username))
				return false;
		users.add(new User(username, DEFAULT_PASSWORD, isAdmin));
		return true;
	}

	/**
	 * Remove a user (names should be unique)
	 */
	public boolean removeUser(String username) {
		if (currentUser == null || !currentUser.getIsAdmin())
			return false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.remove(i); // remove the user if it match with the user name
				// that be wanted to remove
				break;
			}
			if (i == users.size() - 1)
				return false;
		}

		return true;
	}

	/**
	 * Give a user admin power
	 */
	public boolean giveAdmin(String username) {
		if (currentUser == null || !currentUser.getIsAdmin())
			return false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.get(i).setIsAdmin(true);
				// set this user as administration user
				break;
			}
			if (i == users.size() - 1)
				return false;
		}

		return true;
	}

	/**
	 * Remove a user's admin power
	 */
	public boolean takeAdmin(String username) {
		if (currentUser == null || !currentUser.getIsAdmin())
			return false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.get(i).setIsAdmin(false);
				// take this user off from the administration status
				break;
			}
			if (i == users.size() - 1)
				return false;
		}

		return true;
	}

	/**
	 * Reset a user's password
	 */
	public boolean resetPassword(String username) {
		if (!currentUser.getIsAdmin())
			return false;

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				users.get(i).setPassword(DEFAULT_PASSWORD);
				// reset the user passport as the default password
				break;
			}
			if (i == users.size() - 1)
				return false;
		}

		return true;
	}

	/*
	 * Main driver loop. Prompt the user for login information calls the
	 * isValidLogin method If the login is valid, call the sessionScreen method This
	 * method is one of the driver methods of this program
	 */
	public void loginScreen(Scanner userInputScanner) {
		while (true) {
			System.out.print("Please enter the username: ");
			String username = userInputScanner.nextLine();
			System.out.print("Please enter the password: ");
			String password = userInputScanner.nextLine();
			if (isValidLogin(username, password)) // check if the input are valid
				sessionScreen(username, userInputScanner); // display the screen
			else
				System.out.println("Invalid username or password");
		}
	}

	/*
	 * Set the currentUser Allows them to changePassword or logout If they are an
	 * admin , gives access to admin methods
	 */
	public void sessionScreen(String username, Scanner userInputScanner) {
		do {
			for (int i = 0; i < users.size(); i++)
				if (users.get(i).getUsername().equals(username)) {
					currentUser = users.get(i); // get the current user
					break;
				}
			// display the screen menu
			System.out.println("Welcome, " + username + "!");
			System.out.println("--------Menu--------");
			System.out.println("(1) logout");
			System.out.println("(2) newpw [newpassword]");
			System.out.println("(3) adduser [username]");
			System.out.println("(4) adduser [username] [true or false]");
			System.out.println("(5) rmuser [username]");
			System.out.println("(6) giveadmin [username]");
			System.out.println("(7) rmadmin [username]");
			System.out.println("(8) resetpw [username]");
			System.out.print("Please enter a command: ");
			String[] cmd = userInputScanner.nextLine().split(" "); // split the user input with a space

			if (cmd.length == 3) {
				boolean isAdmin;
				if (cmd[2].equals("true"))
					isAdmin = true;
				else
					isAdmin = false;
				addUser(cmd[1], isAdmin);
			} else if (cmd[0].equals("logout"))
				logout();
			else if (cmd[0].equals("newpw"))
				changePassword(cmd[1]);
			else if (cmd[0].equals("adduser"))
				addUser(cmd[1]);
			else if (cmd[0].equals("rmuser"))
				removeUser(cmd[1]);
			else if (cmd[0].equals("giveadmin"))
				giveAdmin(cmd[1]);
			else if (cmd[0].equals("rmadmin"))
				takeAdmin(cmd[1]);
			else if (cmd[0].equals("resetpw"))
				resetPassword(cmd[1]);

		} while (currentUser != null);
	}

	/**
	 * A STATIC method that creates a new AccessControl object An implementation for
	 * this method is provided below
	 */
	public static void main(String[] args) {

		AccessControl ac = new AccessControl();
		// If we have any persistent information to lead
		// this is where we load it.
		Scanner userIn = new Scanner(System.in);
		ac.loginScreen(userIn);
	}
}
