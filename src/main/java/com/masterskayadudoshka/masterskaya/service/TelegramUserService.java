package com.masterskayadudoshka.masterskaya.service;

import com.masterskayadudoshka.masterskaya.model.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
   void save(TelegramUser user);

   List<TelegramUser> retrieveAllActiveUsers();

   Optional<TelegramUser> findByChatId(String chatId);
}
