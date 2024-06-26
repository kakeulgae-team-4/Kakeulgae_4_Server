package kr.kakaocloud.kakeulgae.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "job_posting_company_name_post_name_uk",
        columnNames = {"company_name", "post_name"}
    )
}
)
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "post_name")
    private String postName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_id", foreignKey = @ForeignKey(name = "fk_job_posting_education_id"))
    private Education education;

    @OneToMany(mappedBy = "jobPosting")
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting")
    private List<JobDetailPostingRelation> jobDetailPostingRelations = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting")
    private List<JobPostingCareer> jobPostingCareers = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting")
    private List<JobPostingWorkType> jobPostingWorkTypes = new ArrayList<>();

    @OneToMany(mappedBy = "jobPosting")
    private List<RegionPostingRelation> regionPostingRelations = new ArrayList<>();

    private String url;

    private LocalDate deadline;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public JobPosting(String companyName, String postName, Education education,
        String url, LocalDate deadline, LocalDate createdAt) {
        this.companyName = companyName;
        this.postName = postName;
        this.education = education;
        this.url = url;
        this.deadline = deadline;
        this.createdAt = createdAt;
    }
}
