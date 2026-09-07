package dev.jorge.projects.gossipuerj.dto.response.user;

import java.util.Date;

public record ExceptionResponse(String message, String details, Date timeStamp) {}
