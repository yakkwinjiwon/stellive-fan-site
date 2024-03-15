package com.stellive.fansite.repository;

import com.stellive.fansite.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffSiteNoticeDataRepo extends JpaRepository<Notice, Long> {
}
