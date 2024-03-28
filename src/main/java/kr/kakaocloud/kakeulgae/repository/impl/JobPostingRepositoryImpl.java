package kr.kakaocloud.kakeulgae.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.core.types.OrderSpecifier;
import java.time.LocalDate;
import java.util.List;
import kr.kakaocloud.kakeulgae.domain.entity.JobPosting;
import kr.kakaocloud.kakeulgae.domain.entity.QBookmark;
import kr.kakaocloud.kakeulgae.domain.entity.QJob;
import kr.kakaocloud.kakeulgae.domain.entity.QJobDetail;
import kr.kakaocloud.kakeulgae.domain.entity.QJobDetailPostingRelation;
import kr.kakaocloud.kakeulgae.domain.entity.QJobPosting;
import kr.kakaocloud.kakeulgae.repository.JobPostingRepositorySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

public class JobPostingRepositoryImpl extends QuerydslRepositorySupport implements
    JobPostingRepositorySearch {

    @Autowired
    private JPAQueryFactory queryFactory;

    public JobPostingRepositoryImpl() {
        super(JobPosting.class);
    }

    public Predicate eqKeyword(String keyword) {
        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(keyword)) {
            QJobPosting jobPosting = QJobPosting.jobPosting;
            QJobDetailPostingRelation relation = QJobDetailPostingRelation.jobDetailPostingRelation;
            QJobDetail jobDetail = QJobDetail.jobDetail;

            builder.or(jobPosting.companyName.containsIgnoreCase(keyword));
            builder.or(jobPosting.postName.containsIgnoreCase(keyword));

            builder.or(jobDetail.type.containsIgnoreCase(keyword)
                .and(relation.jobPosting.id.eq(jobPosting.id))
                .and(relation.jobDetail.id.eq(jobDetail.id)));
        }

        return builder.getValue();
    }

    @Override
    public Slice<JobPosting> findBySearchBookmarkData(Long id, @Param("keyword") String keyword, Pageable pageable){

        String orderString = pageable.getSort().iterator().next().getProperty();
        OrderSpecifier<LocalDate> orderSpecifier = QJobPosting.jobPosting.createdAt.asc();

        if(orderString.equals("deadline")){
            orderSpecifier = QJobPosting.jobPosting.deadline.asc();
        }

        List<JobPosting> content = queryFactory
            .selectFrom(QJobPosting.jobPosting)
            .innerJoin(QJobPosting.jobPosting.bookmarks, QBookmark.bookmark)
            .leftJoin(QJobPosting.jobPosting.jobDetailPostingRelations, QJobDetailPostingRelation.jobDetailPostingRelation)
            .leftJoin(QJobDetailPostingRelation.jobDetailPostingRelation.jobDetail, QJobDetail.jobDetail)
            .where(
                QBookmark.bookmark.member.id.eq(id),
                eqKeyword(keyword)
            )
            .orderBy(orderSpecifier)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize()+1)
            .fetch();


        boolean hasNext = false;
        int pageSize = pageable.getPageSize();
        if (content.size() > pageSize) {
            content.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }
}
