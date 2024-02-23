package com.stellive.fansite.client;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class YoutubeSearchDto {

    /**
     * string
     * part 매개변수는 API 응답이 포함하는 search 리소스 속성 하나 이상의 쉼표로 구분된 목록을 지정합니다. 매개변수 값을 snippet로 설정합니다.
     */
    private String part;
    /**
     * string
     * channelId 매개변수는 API 응답에 채널에서 만든 리소스만 포함해야 함을 나타냅니다.
     *
     * 참고: 요청이 channelId 매개변수의 값을 지정하고 type 매개변수 값을 video로 설정하지만 forContentOwner, forDeveloper 또는 forMine 필터 중 하나를 설정하지 않는 경우 검색결과는 최대 500개의 동영상으로 제한됩니다.
     */
    private String channelId;
    /**
     * unsigned integer
     * maxResults 매개변수는 결과 집합에 반환해야 하는 최대 항목 수를 지정합니다. 사용 가능한 값: 0~50 기본값은 5입니다.
     */
    private Integer maxResults;
    /**
     * string
     * order 매개변수는 API 응답에서 리소스를 정렬하는 데 사용할 메서드를 지정합니다. 기본값은 relevance입니다.
     *
     * 허용되는 값은 다음과 같습니다.
     * date – 리소스를 만든 날짜를 기준으로 최근 항목부터 시간 순서대로 리소스를 정렬합니다.
     * rating – 높은 평가부터 낮은 평가순으로 리소스를 정렬합니다.
     * relevance – 검색어와의 관련성을 기준으로 리소스를 정렬합니다. 이 매개변수의 기본값입니다.
     * title – 리소스가 제목순으로 알파벳순으로 정렬됩니다.
     * videoCount – 채널은 업로드한 동영상 수를 기준으로 내림차순으로 정렬됩니다.
     * viewCount – 리소스가 조회수가 높은 순으로 정렬됩니다. 실시간 방송의 경우 방송이 진행되는 동안 동시 시청자 수를 기준으로 동영상을 정렬합니다.
     */
    private String order;
    /**
     * 	string
     * type 매개변수는 특정 유형의 리소스만 검색하도록 검색어를 제한합니다. 값은 쉼표로 구분된 리소스 유형 목록입니다. 기본값은 video,channel,playlist입니다.
     *
     * 허용되는 값은 다음과 같습니다.
     * channel
     * playlist
     * video
     */
    private String type;


}
