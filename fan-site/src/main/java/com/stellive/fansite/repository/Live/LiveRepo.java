package com.stellive.fansite.repository.Live;

import com.stellive.fansite.domain.Live;

import java.util.List;

public interface LiveRepo {

    List<Live> saveAll(List<Live> lives);
    List<Live> findAll();
}
