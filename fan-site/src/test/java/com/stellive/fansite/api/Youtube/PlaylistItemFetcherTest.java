package com.stellive.fansite.api.Youtube;

import com.stellive.fansite.dto.playlistitem.PlaylistItemList;
import com.stellive.fansite.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.stellive.fansite.utils.ApiConst.*;
import static com.stellive.fansite.utils.TestConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistItemFetcherTest {

    PlaylistItemFetcher playlistItemFetcher;

    @Mock
    PlaylistItemConnector playlistItemConnector;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playlistItemFetcher = new PlaylistItemFetcher(playlistItemConnector);
    }

    @Test
    @DisplayName("MAX_RESULTS_VIDEO만큼 PlaylistItem을 가져온다.")
    void fetchPlaylistItem() {
        // given
        PlaylistItemList playlistItemList = TestUtils.getPlaylistItemListVideo(null);
        List<String> videoIds = List.of(PLAYLIST_ITEM_ID);

        when(playlistItemConnector.callPlaylistItem(anyString(), eq(MAX_RESULTS_VIDEO), eq(null)))
                .thenReturn(playlistItemList);

        // when
        List<String> fetchPlaylistItem = playlistItemFetcher.fetchVideoIds(PLAYLIST_ID_VALID, MAX_RESULTS_VIDEO);

        // then
        verify(playlistItemConnector, times(1)).callPlaylistItem(any(), any(), any());
        assertThat(fetchPlaylistItem).isEqualTo(videoIds);
    }
}