/**
 * The class AccessControlTest is used to test the functions of AccessControl
 * class. This class has multiple test methods, each test focuses on a specific
 * method, on a specific field, or on a specific potential bug. If problem
 * occurs, an error message will be printed out. If no problem is found, it will
 * only display a single message stating all tests have passed.
 */
public class AccessControlTest {

	/**
	 * This test tries to log in a user that doesn't exist. Should return false.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testLogin1() {
		String user = "probablyNotInTheSystem1234";
		String pw = "password";
		return !AccessControl.isValidLogin(user, pw); // isValidLogin should return false
	}

	/**
	 * This test tries to log in with initial admin account with the correct
	 * password. Should return true.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testLogin2() {
		String user = "admin";
		String pw = "root";
		return AccessControl.isValidLogin(user, pw); // isValidLogin should return true
	}

	/**
	 * Create a new AccessControl and do not log in an admin. Verify that
	 * addUser(String username) returns false and that the new user is not added.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testAddUser1() {
		AccessControl ac = new AccessControl();
		String user = "alexi";
		boolean addUserReport = ac.addUser(user);
		if (addUserReport)
			return false; // addUserReport should be false
		// Make sure user wasn't added anyway
		return !AccessControl.isValidLogin(user, "changeme");
	}

	/**
	 * Create a new AccessControl and log in the admin by calling setCurrentUser.
	 * Then change the password. Log in with the new password. Should return true.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testChangePassword() {
		AccessControl ac = new AccessControl();
		String user = "admin";
		String pw = "password";
		// Use setCurrentUser method to set admin as logged in. Such that we can change
		// password.
		ac.setCurrentUser(user);
		ac.changePassword(pw);
		return AccessControl.isValidLogin(user, pw); // isValidLogin should return true
	}

	/**
	 * Create a new AccessControl and log in admin by calling setCurrentUser. First,
	 * add a new user. To test the user is successfully added, try to log in the new
	 * user. Should return true. If so, continue testing. If not, report a problem.
	 * Then remove the new user, and try to log in. This time, it should return
	 * false.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testRemoveUser() {
		AccessControl ac = new AccessControl();
		String admin = "admin";
		String user = "user1";
		String pw = "changeme";
		// Use setCurrentUser method to set admin as logged in. Such that we can add and
		// remove user.
		ac.setCurrentUser(admin);
		ac.addUser(user);
		// We should first ensure new user is successfully added, try to log in the new
		// user.
		if (!AccessControl.isValidLogin(user, pw)) // isValidLogin should return true
			return false;
		ac.removeUser(user);
		return !AccessControl.isValidLogin(user, pw); // isValidLogin should return false
	}

	/**
	 * Create a new AccessControl and log in admin by calling setCurrentUser. Reset
	 * the password. Try to log in the admin with the DEFAULT_PASSWORD, which is
	 * "changeme". Should return true.
	 * 
	 * @return boolean test passed
	 */
	public static boolean testResetPassword() {
		AccessControl ac = new AccessControl();
		String user = "admin";
		String pw = "changeme";
		// Use setCurrentUser method to set admin as logged in. Such that we can reset
		// the password.
		ac.setCurrentUser(user);
		ac.resetPassword(user);
		return AccessControl.isValidLogin(user, pw); // isValidLogin should return true
	}

	/**
	 * Testing main. Runs each test and prints which (if any) failed. If no problem
	 * occurs, print a single line showing "All tests passed!".
	 */
	public static void main(String[] args) {
		int fails = 0;
		if (!testLogin1()) {
			System.out.println("testLogin1 [bad username] failed");
			fails++;
		}
		if (!testLogin2()) {
			System.out.println("testLogin2 [good login] failed");
			fails++;
		}
		if (!testAddUser1()) {
			System.out.println("testAddUser1 failed");
			fails++;
		}
		if (!testChangePassword()) {
			System.out.println("testChangePassword failed");
			fails++;
		}
		if (!testRemoveUser()) {
			System.out.println("testRemoveUser failed");
			fails++;
		}
		if (!testResetPassword()) {
			System.out.println("testResetPassword failed");
			fails++;
		}
		// If no problem occurs, print a single line showing "All tests passed!".
		if (fails == 0)
			System.out.println("All tests passed!");
	}

}
