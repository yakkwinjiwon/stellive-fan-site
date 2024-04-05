package com.stellive.fansite.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchList {

    private List<SearchItem> items;
}
