package dev.jorge.projects.gossipuerj.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record EmailRequest(
        UUID userId,
        @NotBlank String emailFrom,
        @NotBlank String emailTo,
        @NotBlank String subject,
        @NotBlank String text
) {
}
