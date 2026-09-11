package dev.jorge.projects.gossipuerj.controller;

import dev.jorge.projects.gossipuerj.config.JWTUserData;
import dev.jorge.projects.gossipuerj.dto.request.crush.CrushRequest;
import dev.jorge.projects.gossipuerj.dto.response.common.PageResponse;
import dev.jorge.projects.gossipuerj.dto.response.crush.CrushResponse;
import dev.jorge.projects.gossipuerj.model.Crush;
import dev.jorge.projects.gossipuerj.service.CrushService;
import jakarta.validation.Valid;
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
public class CrushController {

    private final CrushService crushService;

    @PostMapping("/api/v1/crushes")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CrushResponse> create(
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid CrushRequest request
    ){
        Crush created = crushService.create(request, userData.userId());
        return ResponseEntity
                .created(URI.create("/api/v1/crushs/%s".formatted(created.getId())))
                .body(CrushResponse.from(created));
    }

    @PutMapping("/api/v1/crushes")
    @ResponseStatus(HttpStatus.OK)
    public CrushResponse update(
            @AuthenticationPrincipal JWTUserData userData,
            @RequestBody @Valid CrushRequest request
    ){
        return CrushResponse.from(crushService.create(request, userData.userId()));
    }

    @GetMapping("/api/v1/crushes")
    @ResponseStatus(HttpStatus.OK)
    public PageResponse<CrushResponse> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<Crush> crushes = crushService.findAll(pageable);
        return PageResponse.from(crushes.map(CrushResponse::from));
    }

    @GetMapping("/api/v1/crushes/me")
    @ResponseStatus(HttpStatus.OK)
    public CrushResponse me(
            @AuthenticationPrincipal JWTUserData userData
    ){
        Crush crush = crushService.findByUserId(userData.userId());
        return CrushResponse.from(crush);
    }

    @GetMapping("/api/v1/crushes/{crushId}")
    @ResponseStatus(HttpStatus.OK)
    public CrushResponse getOne(@PathVariable String crushId){
        Crush crush = crushService.findById(crushId);
        return CrushResponse.from(crush);
    }

    @DeleteMapping("/api/v1/crushes/{crushId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String crushId,
            @AuthenticationPrincipal JWTUserData userData
    ){
        crushService.delete(crushId, userData.userId());
    }

}
