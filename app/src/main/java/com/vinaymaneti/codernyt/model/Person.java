
package com.vinaymaneti.codernyt.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Person implements Parcelable{

    @SerializedName("organization")
    private String organization;
    @SerializedName("role")
    private String role;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("rank")
    private Integer rank;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("middlename")
    private String middleName;

    protected Person(Parcel in) {
        organization = in.readString();
        role = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        middleName = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    /**
     * 
     * @return
     *     The organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 
     * @param organization
     *     The organization
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * 
     * @return
     *     The role
     */
    public String getRole() {
        return role;
    }

    /**
     * 
     * @param role
     *     The role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 
     * @return
     *     The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * 
     * @param firstname
     *     The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * 
     * @return
     *     The rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * 
     * @return
     *     The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * 
     * @param lastname
     *     The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(organization);
        dest.writeString(role);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(middleName);
    }
}
