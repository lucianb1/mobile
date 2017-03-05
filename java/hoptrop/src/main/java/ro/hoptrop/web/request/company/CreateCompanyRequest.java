package ro.hoptrop.web.request.company;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import ro.hoptrop.model.company.Location;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Luci on 04-Mar-17.
 */
public class CreateCompanyRequest {

    @NotBlank
    private String name;

    @NotNull
    private Location location;

    @NotEmpty
    private Set<Integer> domains;

    public String getName() {
        return name;
    }

    public CreateCompanyRequest setName(String name) {
        this.name = name;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public CreateCompanyRequest setLocation(Location location) {
        this.location = location;
        return this;
    }

    public Set<Integer> getDomains() {
        return domains;
    }

    public CreateCompanyRequest setDomains(Set<Integer> domains) {
        this.domains = domains;
        return this;
    }
}
