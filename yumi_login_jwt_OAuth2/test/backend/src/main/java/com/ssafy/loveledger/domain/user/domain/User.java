package com.ssafy.loveledger.domain.user.domain;

import com.ssafy.loveledger.domain.account.domain.Account;
import com.ssafy.loveledger.domain.library.domain.Library;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @Column(unique = true, nullable = false)
    private String email;

    private Boolean gender;
    private Date birthDay;
    private String provider;
    private String usercode;

    @Length(min = 2, max = 64)
    private String name;

    private Boolean isMarried;

    @OneToOne(mappedBy = "user")
    private Library library;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Account> account;

//    @Column(nullable = false)
//    private Integer coupleId;\
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<DailyStatistics> dailyStatistics;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<MonthlyStatisticsByCategory> monthlyStatisticsByCategories;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private List<WeeklyStatisticsByCategory> weeklyStatisticsByCategories;
//
//    @OneToOne(mappedBy = "owner", fetch = FetchType.LAZY)
//    private Goal goals;
}