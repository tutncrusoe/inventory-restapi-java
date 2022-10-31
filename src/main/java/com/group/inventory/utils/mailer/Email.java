package com.group.inventory.utils.mailer;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    @NotNull
    private Set<String> recipients = new LinkedHashSet<>();

    @NotNull
    private String subject;

    @NotNull
    private String content;

    private Set<String> carbonCopy = new LinkedHashSet<>();

    private Set<String> blindCarbonCopy = new LinkedHashSet<>();

}
