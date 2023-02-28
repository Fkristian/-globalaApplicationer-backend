package se.kth.iv1201.appserv.jobapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Entity class representing the person table from the database.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="person_id")
    int personId;
    String name;
    String surname;
    String pnr;
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    @Column(name="role_id")
    int roleId;
    String username;

    @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = false, updatable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List <CompetenceProfile> competenceProfiles;

    @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = false, updatable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List <Availability> availabilities;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private ApplicationStatus applicationStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + roleId));
    }

    @Override
    public String getUsername(){
        return this.username;
    }


    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
