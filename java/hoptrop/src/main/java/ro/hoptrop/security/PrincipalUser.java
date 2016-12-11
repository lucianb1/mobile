package ro.hoptrop.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PrincipalUser extends User {

    private static final long serialVersionUID = -7022660152199683334L;

    public PrincipalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    private int id;
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public PrincipalUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PrincipalUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
