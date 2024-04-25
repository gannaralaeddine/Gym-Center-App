package com.example.gymcenterapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailModel
{
    private String subject;
    private String text;
    private String senderName;
    private String senderEmail;
}
