package beans;

public class Manager extends User {

	private static final long serialVersionUID = 588531667742349844L;

	public Manager() {
		super();
		this.setRole("MANAGER");
	}

	@Override
	public String toString() {
		return "Manager [getUsername()=" + getUsername() + ", getPassword()=" + getPassword() + ", getName()="
				+ getName() + ", getSurname()=" + getSurname() + ", getGender()=" + getGender() + ", getDateOfBirth()="
				+ getDateOfBirth() + ", getRole()=" + getRole() + ", isBlocked()=" + isBlocked() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
