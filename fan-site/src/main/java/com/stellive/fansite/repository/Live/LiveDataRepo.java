package com.stellive.fansite.repository.Live;

import com.stellive.fansite.domain.Live;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveDataRepo extends JpaRepository<Live, Long> {
}
