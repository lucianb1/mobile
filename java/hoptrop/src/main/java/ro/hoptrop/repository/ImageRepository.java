package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Luci on 13-Dec-16.
 */
@Repository
//TODO cache
public class ImageRepository {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public byte[] getCompanyImage(int companyID) {
        return null;
    }

    public byte[] getMemberImage(int memberID) {
        return null;
    }

    public byte[] getAccountImage(int accountID) {
        return null;
    }



}
