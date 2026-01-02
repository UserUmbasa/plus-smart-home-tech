package ru.yandex.practicum.model;

/**
 * ACTIVE(«активный») и DEACTIVATE(«неактивный»). Товар переходит в неактивное состояние,
 * если администратор удаляет его. Это значит, что реального удаления товаров из базы данных
 * никогда не происходит
 */
public enum ProductState {
    ACTIVE, DEACTIVATE
}
