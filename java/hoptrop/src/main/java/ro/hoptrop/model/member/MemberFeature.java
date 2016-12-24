package ro.hoptrop.model.member;

/**
 * Created by Luci on 24-Dec-16.
 */
public class MemberFeature {

    private final int id;
    private final int domainID;
    private final String name;
    private final int duration; // in quarters
    private final int memberID;

    MemberFeature(int id, int domainID, String name, int duration, int memberID) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.memberID = memberID;
        this.domainID = domainID;
    }

    public static MemberFeatureBuilder builder() {
        return new MemberFeatureBuilder();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDomainID() {
        return domainID;
    }

    public int getMemberID() {
        return memberID;
    }

    public int getDuration() {
        return duration;
    }
}
