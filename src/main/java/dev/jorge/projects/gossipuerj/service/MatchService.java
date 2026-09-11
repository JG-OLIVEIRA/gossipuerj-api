package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.enums.match.Status;
import dev.jorge.projects.gossipuerj.exception.match.MatchAlreadyRespondedException;
import dev.jorge.projects.gossipuerj.exception.match.MatchNotAllowedException;
import dev.jorge.projects.gossipuerj.exception.match.MatchNotFoundException;
import dev.jorge.projects.gossipuerj.model.Crush;
import dev.jorge.projects.gossipuerj.model.Match;
import dev.jorge.projects.gossipuerj.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final CrushService crushService;

    public Match create(String crushId, String userId){
        Match match = new Match();

        Crush liker = crushService.findByUserId(userId);

        if(crushId.equals(liker.getId())){
            throw new MatchNotAllowedException("");
        }

        match.setLiker(liker);
        match.setLiked(crushService.findById(crushId));
        match.setStatus(Status.PENDING);
        return matchRepository.save(match);
    }

    public Match accept(String crushId, String userId, String matchId){
        Match match = findAndValidateMatch(crushId, userId, matchId);
        match.setStatus(Status.ACCEPTED);
        return matchRepository.save(match);
    }

    public Match reject(String crushId, String userId, String matchId){
        Match match = findAndValidateMatch(crushId, userId, matchId);
        match.setStatus(Status.REJECTED);
        return matchRepository.save(match);
    }

    public Page<Match> findAllCreatedByUserId(String userId, Pageable pageable) {
        Crush crush = crushService.findByUserId(userId);
        return matchRepository.findAllByLikerId(crush.getId(), pageable);
    }

    public Page<Match> findAllReceivedByUserId(String userId, Pageable pageable){
        Crush crush = crushService.findByUserId(userId);
        return matchRepository.findAllByLikedId(crush.getId(), pageable);
    }

    private Match findAndValidateMatch(String crushId, String userId, String matchId){
        Crush liked = crushService.findByUserId(userId);
        Match match = matchRepository
                .findByLikerIdAndLikedIdAndId(crushId, liked.getId(), matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        if (!match.getLiked().getId().equals(liked.getId())) {
            throw new MatchNotAllowedException("");
        }

        if (match.getStatus() != Status.PENDING) {
            throw new MatchAlreadyRespondedException("");
        }

        return match;
    }


}
