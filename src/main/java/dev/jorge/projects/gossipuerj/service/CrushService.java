package dev.jorge.projects.gossipuerj.service;

import dev.jorge.projects.gossipuerj.dto.request.crush.CrushRequest;
import dev.jorge.projects.gossipuerj.exception.crush.CrushNotFoundException;
import dev.jorge.projects.gossipuerj.model.Crush;
import dev.jorge.projects.gossipuerj.repository.CrushRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrushService {

    private final CrushRepository crushRepository;

    private final AuthService authService;
    private final CourseService courseService;

    @Transactional
    public Crush create(CrushRequest request, String userId) {
        Crush crush = crushRepository.findByUserId(userId)
                .orElseGet(Crush::new);
        crush.setUser(authService.findById(userId));
        crush.setPhotoUrl(request.photoUrl());
        crush.setDescription(request.description());
        crush.setGender(request.gender());
        crush.setOrientation(request.orientation());
        return crushRepository.save(crush);
    }

    public Page<Crush> findAll(Pageable pageable){
        return crushRepository.findAll(pageable);
    }

    public Crush findById(String crushId){
        return crushRepository.findById(crushId).orElseThrow(() -> new CrushNotFoundException(crushId));
    }

    public Crush findByUserId(String userId){
        return crushRepository.findByUserId(userId).orElseThrow(() -> new CrushNotFoundException(userId));
    }

    public void delete(String crushId, String userId) {
        Crush crush = findByUserId(userId);
        if (!crush.getId().equals(crushId)) {
            throw new CrushNotFoundException(crushId);
        }
        crushRepository.delete(crush);
    }
}
