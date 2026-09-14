package dev.jorge.projects.gossipuerj.dto.response.crush;

import dev.jorge.projects.gossipuerj.enums.crush.Gender;
import dev.jorge.projects.gossipuerj.enums.crush.Orientation;
import dev.jorge.projects.gossipuerj.model.Crush;

public record CrushMatchResponse(
        String id,
        String photoUrl,
        String username,
        String description,
        Gender gender,
        Orientation orientation
) {
    public static CrushMatchResponse from(Crush crush){
        return new CrushMatchResponse(
                crush.getId(),
                crush.getPhotoUrl(),
                crush.getUser().getUsername(),
                crush.getDescription(),
                crush.getGender(),
                crush.getOrientation()
        );
    }
}
