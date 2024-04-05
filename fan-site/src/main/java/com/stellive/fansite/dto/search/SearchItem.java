package com.stellive.fansite.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchItem {

    private SearchId id;
    private SearchSnippet snippet;
}
