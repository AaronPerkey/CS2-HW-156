	package com.fmt;
	/**
	 * 
	 * Models the name of the person.
	 * 
	 * @author aaron, emma
	 *
	 */
	public class FullName {
	
		private String lastName;
		private String firstName;
		
		public FullName(String lastName, String firstName) {
			super();
			this.lastName = lastName;
			this.firstName = firstName;
		}
	
		public String getLastName() {
			return lastName;
		}
	
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	
		public String getFirstName() {
			return firstName;
		}
	
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
	
		
	}
