package kr.kakaocloud.kakeulgae.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import lombok.Setter;

@Entity
@Table(
    name = "job_detail",
    uniqueConstraints = @UniqueConstraint(name = "job_detail_uk", columnNames = {"type", "job_id"}),
    indexes = @Index(name = "job_detail_idx_type", columnList = "type")
)
@Setter
public class JobDetail {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", foreignKey = @ForeignKey(name = "fk_job_detail_job_id"))
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_category_id", foreignKey = @ForeignKey(name = "fk_job_detail_job_category_id"))
    private JobCategory jobCategory;

    public JobDetail(long l, String splt, Job job) {
        this.id = l;
        this.type = splt;
        this.job = job;
    }

    public JobDetail() {

    }

    public String getType() {
        return type;
    }
}