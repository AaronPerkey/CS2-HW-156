	package com.fmt;
	/**
	 * 
	 * Models the name of the person.
	 * 
	 * @author aaron
	 *
	 */
	public class FullName {
	
		private final String lastName;
		private final String firstName;
		
		public FullName(String lastName, String firstName) {
			super();
			this.lastName = lastName;
			this.firstName = firstName;
		}
	
		public String getLastName() {
			return lastName;
		}
	
	
		public String getFirstName() {
			return firstName;
		}
		
		public String toString() {
			return  lastName + ", " + firstName;
		}
		
	}
