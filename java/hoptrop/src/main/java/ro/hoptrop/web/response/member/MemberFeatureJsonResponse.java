package ro.hoptrop.web.response.member;

import ro.hoptrop.model.member.MemberFeature;

/**
 * Created by Luci on 24-Dec-16.
 */
public class MemberFeatureJsonResponse {

    private int id;
    private String name;
    private int duration;

    public MemberFeatureJsonResponse(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public static MemberFeatureJsonResponse from(MemberFeature feature) {
        return new MemberFeatureJsonResponse(feature.getId(), feature.getName(), feature.getDuration());
    }

}
