package ro.hoptrop.model.member;

/**
 * Created by Luci on 24-Dec-16.
 */
public class MemberFeatureBuilder {

    private int id;
    private int domainID;
    private String name;
    private int duration; // in quarters
    private int memberID;

    MemberFeatureBuilder() {
    }

    public MemberFeature build() {
        return new MemberFeature(id, domainID, name, duration, memberID);
    }

    public MemberFeatureBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public MemberFeatureBuilder setDomainID(int domainID) {
        this.domainID = domainID;
        return this;
    }

    public MemberFeatureBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MemberFeatureBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public MemberFeatureBuilder setMemberID(int memberID) {
        this.memberID = memberID;
        return this;
    }
}
