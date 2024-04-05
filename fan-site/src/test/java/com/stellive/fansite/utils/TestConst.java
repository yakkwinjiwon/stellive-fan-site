package com.stellive.fansite.utils;

public class TestConst {

    public static final String OK = "ok";
    public static final String FAIL = "fail";

    public static final String CHANNEL_ID_VALID = "UC7_uO3Vsk323nA3LeQX0_sw";
    public static final String CHANNEL_ID_INVALID = "invalid channel id";
    public static final String PLAYLIST_ID_VALID = "PLsF-WIxstzZmzk70V2aj1e6NawmmquvdR";
    public static final String PLAYLIST_ID_INVALID = "invalid playlist id";
    public static final String VIDEO_EXTERNAL_ID_VALID = "PQufcfQtvLg";
    public static final String VIDEO_EXTERNAL_ID_INVALID = "invalid video external id";

    public static final String CHANNEL_RESPONSE_VALID = "{\n" +
            "  \"kind\": \"youtube#channelListResponse\",\n" +
            "  \"etag\": \"Yf_Fotyi8_k6F3tIzfLKjDaw42k\",\n" +
            "  \"pageInfo\": {\n" +
            "    \"totalResults\": 1,\n" +
            "    \"resultsPerPage\": 5\n" +
            "  },\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"kind\": \"youtube#channel\",\n" +
            "      \"etag\": \"KD5F1Uv50A0bN95Kx5SLVHGD2KM\",\n" +
            "      \"id\": \"UC7_uO3Vsk323nA3LeQX0_sw\",\n" +
            "      \"snippet\": {\n" +
            "        \"title\": \"개십스레기 컵해드\",\n" +
            "        \"description\": \"\",\n" +
            "        \"customUrl\": \"@haedoong009\",\n" +
            "        \"publishedAt\": \"2023-12-14T01:05:38.056971Z\",\n" +
            "        \"thumbnails\": {\n" +
            "          \"default\": {\n" +
            "            \"url\": \"https://yt3.ggpht.com/cjCxyd5fP8e7ozjZK7MmNizTGMV08EmC_Psd-bt6wA-VDPI5FwZ6qv0EgpIzoSpSeTrhl5qy=s88-c-k-c0x00ffffff-no-rj\",\n" +
            "            \"width\": 88,\n" +
            "            \"height\": 88\n" +
            "          },\n" +
            "          \"medium\": {\n" +
            "            \"url\": \"https://yt3.ggpht.com/cjCxyd5fP8e7ozjZK7MmNizTGMV08EmC_Psd-bt6wA-VDPI5FwZ6qv0EgpIzoSpSeTrhl5qy=s240-c-k-c0x00ffffff-no-rj\",\n" +
            "            \"width\": 240,\n" +
            "            \"height\": 240\n" +
            "          },\n" +
            "          \"high\": {\n" +
            "            \"url\": \"https://yt3.ggpht.com/cjCxyd5fP8e7ozjZK7MmNizTGMV08EmC_Psd-bt6wA-VDPI5FwZ6qv0EgpIzoSpSeTrhl5qy=s800-c-k-c0x00ffffff-no-rj\",\n" +
            "            \"width\": 800,\n" +
            "            \"height\": 800\n" +
            "          }\n" +
            "        },\n" +
            "        \"localized\": {\n" +
            "          \"title\": \"개십스레기 컵해드\",\n" +
            "          \"description\": \"\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";
    public static final String CHANNEL_RESPONSE_INVALID = "invalid channel json";


}
