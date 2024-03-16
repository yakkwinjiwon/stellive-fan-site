package com.stellive.fansite.repository.Notice;

import com.stellive.fansite.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeDataRepo extends JpaRepository<Notice, Long> {
}
