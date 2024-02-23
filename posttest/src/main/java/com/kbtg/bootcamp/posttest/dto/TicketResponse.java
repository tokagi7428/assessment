package com.kbtg.bootcamp.posttest.dto;

import java.util.List;

public record TicketResponse(List<String> tickets, Integer count, Integer cost) {
}
