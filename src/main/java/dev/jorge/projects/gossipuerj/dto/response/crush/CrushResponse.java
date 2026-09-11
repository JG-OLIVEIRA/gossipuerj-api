package dev.jorge.projects.gossipuerj.dto.response.crush;

import dev.jorge.projects.gossipuerj.enums.crush.Gender;
import dev.jorge.projects.gossipuerj.enums.crush.Orientation;
import dev.jorge.projects.gossipuerj.model.Crush;


public record CrushResponse(
        String id,
        String photoUrl,
        String description,
        Gender gender,
        Orientation orientation
){
    public static CrushResponse from(Crush crush){
        return new CrushResponse(
                crush.getId(),
                crush.getPhotoUrl(),
                crush.getDescription(),
                crush.getGender(),
                crush.getOrientation()
        );
    }
}
