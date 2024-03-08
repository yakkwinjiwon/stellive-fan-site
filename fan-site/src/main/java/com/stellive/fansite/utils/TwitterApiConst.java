package com.stellive.fansite.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TwitterApiConst {

    public static final Set<String> USER_FIELDS = new HashSet<>(Arrays.asList(
            "description",
            "entities",
            "id",
            "name",
            "pinned_tweet_id",
            "profile_image_url",
            "url",
            "username"
    ));

    public static final Set<String> EXPANSIONS = new HashSet<>(Arrays.asList(
            "pinned_tweet_id"
    ));

    public static final Set<String> TWEET_FIELDS = new HashSet<>(Arrays.asList(
            "attachments",
            "created_at"
    ));
}
