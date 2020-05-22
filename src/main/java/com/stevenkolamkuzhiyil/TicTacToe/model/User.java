package com.stevenkolamkuzhiyil.TicTacToe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false, unique = true)
    private long id;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String username;
    @Column(name = "username_nr", nullable = false)
    private int usernameNr;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    private String roles;
    private String permissions;

    public User() {
    }

    public User(@Email String email, String username, int usernameNr, String password, String roles, String permissions) {
        this.username = username;
        this.usernameNr = usernameNr;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.roles = roles;
        this.permissions = permissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsernameNr() {
        return usernameNr;
    }

    public void setUsernameNr(int usernameNr) {
        this.usernameNr = usernameNr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getUserReference() {
        return String.format("%s#%d", this.username, this.usernameNr);
    }

    @JsonIgnore
    public List<String> getRolesAsList() {
        return commaSeparatedStringToListAndRemoveEmptyAndNull(this.roles);
    }

    public void addRole(String role) {
        this.roles = addStringToCommaSeparatedStrings(this.roles, role);
    }

    public void removeRole(String role) {
        this.roles = removeStringFromCommaSeparatedStrings(this.roles, role);
    }

    @JsonIgnore
    public List<String> getPermissionsAsList() {
        return commaSeparatedStringToListAndRemoveEmptyAndNull(this.permissions);
    }

    public void addPermission(String permission) {
        this.permissions = addStringToCommaSeparatedStrings(this.permissions, permission);
    }

    public void removePermission(String permission) {
        this.permissions = removeStringFromCommaSeparatedStrings(this.permissions, permission);
    }

    private List<String> commaSeparatedStringToListAndRemoveEmptyAndNull(String str) {
        List<String> list = new ArrayList<>(Arrays.asList(str.split(",")));
        list.removeAll(Arrays.asList("", null));
        return list;
    }

    private String addStringToCommaSeparatedStrings(String str, String strToAdd) {
        if (!str.contains(strToAdd)) {
            return str.isEmpty() ? strToAdd : str + "," + strToAdd;
        }
        return str;
    }

    private String removeStringFromCommaSeparatedStrings(String str, String strToRemove) {
        if (str.contains(strToRemove)) {
            return str.replaceAll(strToRemove, "")
                    .replaceAll(",,", "")
                    .replaceAll("^,+", "")
                    .replaceAll(",+$", "");
        }
        return str;
    }
}
