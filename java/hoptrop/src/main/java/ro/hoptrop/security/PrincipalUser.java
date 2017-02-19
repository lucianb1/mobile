package ro.hoptrop.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PrincipalUser extends User {

    private static final long serialVersionUID = -7022660152199683334L;

    public PrincipalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @JsonIgnore
    private int id;
    private String name;
    private String phone;

    @JsonIgnore
    private Integer companyID;
    @JsonIgnore
    private Integer memberID;

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

    public PrincipalUser setId(int id) {
        this.id = id;
        return this;
    }

    public Integer getCompanyID() {
        return companyID;
    }

    public void setCompanyID(Integer companyID) {
        this.companyID = companyID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }
}
