package ca.cmpt276.examharmony.Model;

import ca.cmpt276.examharmony.Model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

//Used to collect details about a user on the website
public class CustomUserDetails implements UserDetails {

    private User currentUser;

    public CustomUserDetails(User selectedUser) {
        this.currentUser = selectedUser;
    }

    public void setUser(User user){
        this.currentUser = user;
    }

    //Return a list of authorizations a user has
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return currentUser.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getName()))
                .collect(Collectors.toList());
    }

    public boolean hasRole(String roleName) {
        return this.currentUser.hasRole(roleName);
    }

    @Override
    public String getPassword() {
        return currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return currentUser.getUsername();
    }
}
