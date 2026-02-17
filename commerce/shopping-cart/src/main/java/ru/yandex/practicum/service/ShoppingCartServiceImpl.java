package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.api.warehouse.WarehouseClient;
import ru.yandex.practicum.dto.shoppingCart.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.shoppingCart.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.InactiveShoppingCartException;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartRepository;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartRepository cartRepository; // WarehouseFeignClientFallback
    private final CartMapper cartMapper;
    private final WarehouseClient warehouseClient;



    @Transactional
    @Override // запрос корзины по имени юзера
    public ShoppingCartDto getShoppingCart(String username) {
        validateUsername(username);
        ShoppingCart cart = getOrCreateShoppingCart(username);;
        return cartMapper.mapToCartDto(cart);
    }

    @Transactional
    @Override
    public ShoppingCartDto addProductToShoppingCart(String username, Map<UUID, Integer> products) {
        validateUsername(username);
        ShoppingCart cart = getOrCreateShoppingCart(username);
        checkCartIsActive(cart);
        Map<UUID, Integer> oldProducts = cart.getProducts();
        oldProducts.putAll(products);
        cart.setProducts(oldProducts);
        log.info("Добавили продукты в корзину");
        //проверить товары на складе
        BookedProductsDto bookedProductsDto = warehouseClient.checkProductQuantityEnoughForShoppingCart(cartMapper.mapToCartDto(cart));
        cartRepository.save(cart);
        log.info("Сохранили обновленную корзину");
        return cartMapper.mapToCartDto(cart);
    }

    @Transactional
    @Override
    public void deactivateCurrentShoppingCart(String username) {
        validateUsername(username);
        ShoppingCart cart = getOrCreateShoppingCart(username);
        checkCartIsActive(cart);
        cart.setActive(false);
        cartRepository.save(cart);
        log.info("Сохранили деактивированную корзину");
    }

    @Transactional
    @Override
    public ShoppingCartDto removeFromShoppingCart(String username, List<UUID> products) {
        validateUsername(username);
        ShoppingCart cart = getOrCreateShoppingCart(username);
        checkCartIsActive(cart);
        Map<UUID, Integer> oldProducts = cart.getProducts();
        for (UUID idToRemove : products) {
            oldProducts.remove(idToRemove);
        }
        cart.setProducts(oldProducts);
        cartRepository.save(cart);
        log.info("Сохранили обновленную корзину");
        return cartMapper.mapToCartDto(cart);
    }

    @Transactional
    @Override
    public ShoppingCartDto changeProductQuantity(String username, ChangeProductQuantityRequest request) {
        validateUsername(username);
        ShoppingCart cart = getOrCreateShoppingCart(username);
        checkCartIsActive(cart);
        Map<UUID, Integer> oldProducts = cart.getProducts();
        if (oldProducts.containsKey(request.getProductId())) {
            oldProducts.put(request.getProductId(), request.getNewQuantity());
        } else {
            throw new NoProductsInShoppingCartException("Такого продукта нет в корзине");
        }
        cart.setProducts(oldProducts);

        //проверить товары на складе
        BookedProductsDto bookedProductsDto = warehouseClient.checkProductQuantityEnoughForShoppingCart(cartMapper.mapToCartDto(cart));

        cartRepository.save(cart);
        log.info("Сохранили обновленную корзину");
        return cartMapper.mapToCartDto(cart);
    }

    @Override
    public String getUsernameById(UUID cartId) {
        ShoppingCart shoppingCart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new NotAuthorizedUserException("Корзина с таким ID не существует: {}" + cartId));
        return shoppingCart.getUsername();
    }

    private void validateUsername(String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedUserException(username);
        }
    }

    private ShoppingCart getOrCreateShoppingCart(String username) {
        return cartRepository.findByUsername(username)
                .orElseGet(() -> {
                    log.debug("Корзина для пользователя {} не найдена, создаем новую", username);
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUsername(username);
                    return cartRepository.save(newCart);
                });
    }

    private void checkCartIsActive(ShoppingCart cart) {
        if(!cart.getActive()) {
            throw new InactiveShoppingCartException("Корзина пользователя " + cart.getUsername() + " не активна");
        }
    }
}
