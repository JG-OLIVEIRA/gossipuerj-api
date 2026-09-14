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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    private final CrushService crushService;

    public Match create(String crushId, String userId, Status status){
        Crush liker = findCrushByUserId(userId);
        String likerId = liker.getId();

        Optional<Match> existingMatch = matchRepository.findMatchBetweenCrushes(crushId, likerId, Status.PENDING);

        if(existingMatch.isPresent()){
            if(status.equals(Status.ACCEPTED)){
                return accept(crushId, userId, existingMatch.get().getId());
            }
           return reject(crushId, userId, existingMatch.get().getId());
        }

        if(crushId.equals(likerId)){
            throw new MatchNotAllowedException("");
        }

        Match match = new Match();
        match.setLiker(liker);
        match.setLiked(crushService.findById(crushId));
        match.setStatus(status);
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
        Crush crush = findCrushByUserId(userId);
        return matchRepository.findAllByLikerId(crush.getId(), pageable);
    }

    public Page<Match> findAllReceivedByUserId(String userId, Pageable pageable){
        Crush crush = findCrushByUserId(userId);
        return matchRepository.findAllByLikedId(crush.getId(), pageable);
    }

    public Page<Match> findAllRejectedByUserId(String userId, Pageable pageable){
        Crush crush = findCrushByUserId(userId);
        return matchRepository.findAllByCrushId(crush.getId(), Status.REJECTED, pageable);
    }

    private Match findAndValidateMatch(String crushId, String userId, String matchId){
        Crush liked = findCrushByUserId(userId);
        String likedId = liked.getId();

        Match match = matchRepository
                .findByLikerIdAndLikedIdAndId(crushId, likedId, matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));

        if (!match.getLiked().getId().equals(likedId)) {
            throw new MatchNotAllowedException("");
        }

        if (match.getStatus() != Status.PENDING) {
            throw new MatchAlreadyRespondedException("");
        }

        return match;
    }

    public Match findByCrushIdAndUserIdAndStatus(String crushId, String userId, Status status) {
        Crush crush = findCrushByUserId(userId);
        return matchRepository.findMatchBetweenCrushes(crushId, crush.getId(), status)
                .orElseThrow(() -> new MatchNotFoundException("No match found between crushId: " + crushId + " and userId: " + userId));
    }

    private Crush findCrushByUserId(String userId) {
        return crushService.findByUserId(userId);
    }

}
