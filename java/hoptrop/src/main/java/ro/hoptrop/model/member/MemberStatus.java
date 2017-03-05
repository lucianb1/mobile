package ro.hoptrop.model.member;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Luci on 04-Mar-17.
 */
public enum MemberStatus {

    ACTIVE, PENDING_TIMETABLE;

    public static MemberStatus valueFrom(String value) {
        Optional<MemberStatus> first = Arrays.stream(MemberStatus.values()).filter(item -> item.name().equalsIgnoreCase(value)).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new RuntimeException("Invalid enum value: " + value);
        }
    }

}
