package kr.kakaocloud.kakeulgae.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;

@Getter
@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            name = "job_posting_work_type_job_posting_id_work_type_id_unique",
            columnNames = {"job_posting_id", "work_type_id"}
        ),
    }
)
public class JobPostingWorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id",
        foreignKey = @ForeignKey(name = "fk_job_posting_work_type_job_posting_id"))
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_type_id",
        foreignKey = @ForeignKey(name = "fk_job_posting_work_type_work_type_id"))
    private WorkType workType;
}
