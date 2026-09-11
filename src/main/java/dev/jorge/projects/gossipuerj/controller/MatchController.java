package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.response.common.PageResponse;
import dev.jorge.projects.gossipuerj.dto.response.match.MatchResponse;
import dev.jorge.projects.gossipuerj.model.Match;
import dev.jorge.projects.gossipuerj.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/api/v1/matches/sent")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<MatchResponse> sent(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal JWTUserData userData
    ) {
        Page<Match> matches = matchService.findAllCreatedByUserId(userData.userId(), pageable);
        return PageResponse.from(matches.map(MatchResponse::from));
    }

    @GetMapping("/api/v1/matches/received")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<MatchResponse> received(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal JWTUserData userData
    ){
        Page<Match> match = matchService.findAllReceivedByUserId(userData.userId(), pageable);
        return PageResponse.from(match.map(MatchResponse::from));
    }

    @PostMapping("/api/v1/crushes/{crushId}/matches")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MatchResponse> create(
            @PathVariable String crushId,
            @AuthenticationPrincipal JWTUserData userData
    ){
        Match created = matchService.create(crushId, userData.userId());
        return ResponseEntity
                .created(URI.create("/api/v1/crushs/%s/matches".formatted(created.getId())))
                .body(MatchResponse.from(created));
    }

    @PutMapping("/api/v1/crushes/{crushId}/matches/{matchId}/accept")
    @ResponseStatus(HttpStatus.OK)
    public MatchResponse accept(
            @PathVariable String crushId,
            @PathVariable String matchId,
            @AuthenticationPrincipal JWTUserData userData
    ){
        Match match = matchService.accept(crushId, userData.userId(), matchId);
        return MatchResponse.from(match);
    }

    @PutMapping("/api/v1/crushes/{crushId}/matches/{matchId}/reject")
    @ResponseStatus(HttpStatus.OK)
    public MatchResponse reject(
            @PathVariable String crushId,
            @PathVariable String matchId,
            @AuthenticationPrincipal JWTUserData userData
    ){
        Match match = matchService.reject(crushId, userData.userId(), matchId);
        return MatchResponse.from(match);
    }

}
