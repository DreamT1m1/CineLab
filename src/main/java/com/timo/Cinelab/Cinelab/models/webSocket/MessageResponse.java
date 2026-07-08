package com.timo.Cinelab.Cinelab.models.webSocket;

import java.time.LocalDateTime;

public record MessageResponse(Long senderId,
                              Long conversationId,
                              String text,
                              LocalDateTime sentAt) {
}
