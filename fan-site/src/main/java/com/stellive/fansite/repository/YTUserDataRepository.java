package com.stellive.fansite.repository;

import com.stellive.fansite.domain.YTUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YTUserDataRepository extends JpaRepository<YTUser, Long> {

}
