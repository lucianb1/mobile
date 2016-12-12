package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Luci on 13-Dec-16.
 */
@Repository
public class CompanyReviewRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void addComment() {
        //TODO implement me
    }

}
