package com.stellive.fansite.repository;

import com.stellive.fansite.domain.YTUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YTUserDataRepo extends JpaRepository<YTUser, Long> {

}
