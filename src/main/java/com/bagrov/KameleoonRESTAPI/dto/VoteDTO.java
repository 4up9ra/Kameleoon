package com.bagrov.KameleoonRESTAPI.dto;

import java.time.LocalDateTime;

public class VoteDTO {

    private boolean isUpvote;

    private LocalDateTime votedAt;

    public boolean isUpvote() {
        return isUpvote;
    }

    public void setUpvote(boolean upvote) {
        isUpvote = upvote;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt(LocalDateTime votedAt) {
        this.votedAt = votedAt;
    }
}
