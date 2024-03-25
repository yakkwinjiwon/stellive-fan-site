package com.stellive.fansite.repository.News;

import com.stellive.fansite.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsDataRepo extends JpaRepository<News, Long> {
}
