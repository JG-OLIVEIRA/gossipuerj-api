package dev.jorge.projects.gossipuerj.dto.response;

import java.util.Date;

public record ExceptionResponse(String message, String details, Date timeStamp) {}
