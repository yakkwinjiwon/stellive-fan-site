package com.stellive.fansite.repository.Live;

import com.stellive.fansite.domain.Live;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LiveRepoImpl implements LiveRepo{

    private final LiveDataRepo liveDataRepo;

    @Override
    public List<Live> saveAll(List<Live> lives) {
        return liveDataRepo.saveAll(lives);
    }

    @Override
    public List<Live> findAll() {
        return liveDataRepo.findAll();
    }
}
