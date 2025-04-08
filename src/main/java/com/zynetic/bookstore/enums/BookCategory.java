package com.zynetic.bookstore.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BookCategory {
    FICTION("FICTION"),
    MYSTERY("MYSTERY");

    private final String displayName;
}
