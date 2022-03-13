package com.masterskayadudoshka.masterskaya.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table
public class TelegramUser {
    @Id
    @Column
    private String chatId;
    @Column
    private boolean active;
}
