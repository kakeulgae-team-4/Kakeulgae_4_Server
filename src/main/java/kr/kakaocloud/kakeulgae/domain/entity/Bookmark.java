package kr.kakaocloud.kakeulgae.domain.entity;


import jakarta.persistence.*;
import kr.kakaocloud.kakeulgae.domain.entity.member.Member;
import kr.kakaocloud.kakeulgae.support.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Getter
@AllArgsConstructor
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "bookmark_member_id_job_posting_id_unique",
            columnNames = {"member_id", "job_posting_id"}
        )
    }
)
public class Bookmark extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_bookmark_member_id"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", foreignKey = @ForeignKey(name = "fk_bookmark_job_posting_id"))
    private JobPosting jobPosting;


    public Bookmark(Member member, JobPosting jobPosting) {
        this.member = member;
        this.jobPosting = jobPosting;
    }
}
