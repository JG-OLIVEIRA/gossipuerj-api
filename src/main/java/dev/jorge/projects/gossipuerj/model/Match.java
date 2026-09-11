package dev.jorge.projects.gossipuerj.model;

import com.github.f4b6a3.uuid.UuidCreator;
import dev.jorge.projects.gossipuerj.enums.match.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_matches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "liker_id", nullable = false)
    private Crush liker;

    @OneToOne
    @JoinColumn(name = "liked_id", nullable = false)
    private Crush liked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime unmatchedAt;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UuidCreator.getTimeOrderedEpoch().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

}
