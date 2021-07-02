package beans;

public class Administrator extends User {

	private static final long serialVersionUID = 3499842602493088965L;

	public Administrator() {
		super();
	}

	@Override
	public String toString() {
		return "Administrator [getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getName()="
				+ getName() + ", getSurname()=" + getSurname() + ", getGender()=" + getGender() + ", getDateOfBirth()="
				+ getDateOfBirth() + ", getRole()=" + getRole() + ", isBlocked()=" + isBlocked() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
