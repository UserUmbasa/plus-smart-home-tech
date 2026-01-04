package ru.yandex.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.WarehouseProduct;

import java.util.UUID;
/**
 * Репозиторий для работы со складскими товарами.
 * <p>
 * Интерфейс репозитория предоставляет доступ к данным о товарах на складе
 * и реализует базовые операции CRUD через JPA.
 *
 * Основные возможности:
 * <ul>
 *     <li>Работа с сущностью {@code WarehouseProduct}</li>
 *     <li>Использование UUID в качестве идентификатора</li>
 *     <li>Автоматическое создание стандартных методов доступа к данным</li>
 * </ul>
 *
 * Наследуется от JpaRepository, который предоставляет следующие базовые методы:
 * <ul>
 *     <li>save(T entity) - сохранение объекта</li>
 *     <li>findById(ID id) - поиск по идентификатору</li>
 *     <li>findAll() - получение всех записей</li>
 *     <li>deleteById(ID id) - удаление по идентификатору</li>
 *     <li>existsById(ID id) - проверка существования записи</li>
 * </ul>
 */
public interface WarehouseRepository extends JpaRepository<WarehouseProduct, UUID> {
}
