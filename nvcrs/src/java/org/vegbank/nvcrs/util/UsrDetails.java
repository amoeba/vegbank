/*
 * '$Id: UsrDetails.java,v 1.1.1.1 2004-04-21 17:10:06 anderson Exp $'
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


package org.vegbank.nvcrs.util;

public class UsrDetails implements java.io.Serializable {

    private String usrId;
    private String loginName;
    private String password;
    private String role;
    private String lastName;
    private String firstName;
    private String middleInitial;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public UsrDetails()
    {
    	usrId="";
    	loginName="";
    	password="";
    	role="";
    	lastName="";
    	firstName="";
    	middleInitial="";
    	street="";
    	city="";
    	state="";
    	zip="";
    	phone="";
    	email="";
    }
    public UsrDetails (String usrId, String usrLoginName,String usrPassword,String role, String lastName,
        String firstName, String middleInitial, String street,
        String city, String state, String zip, String phone,
        String email) {
        
        this.usrId = usrId;
        this.loginName=usrLoginName;
        this.password=usrPassword;
        this.role=role;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    // getters

    public String getusrId() {
        return usrId;
    }

	public String getLoginName() {
        return loginName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getRole()
    {
    	return this.role;
    }
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // setters

    public void setLoginName(String loginName)
    {
    	this.loginName=loginName;
    }
    
    public void setPassword(String psw)
    {
    	this.password=psw;
    }
    
    public void setRole(String role)
    {
    	this.role=role;
    }
    public void setusrId(String usrId) {
        this.usrId = usrId;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

} // CustomerDetails
