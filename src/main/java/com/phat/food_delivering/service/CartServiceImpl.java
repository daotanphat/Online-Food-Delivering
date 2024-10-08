package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.CartDTO;
import com.phat.food_delivering.dto.CartItemDTO;
import com.phat.food_delivering.dto.Mapper.CartDTOMapper;
import com.phat.food_delivering.dto.Mapper.CartItemDTOMapper;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.Cart;
import com.phat.food_delivering.model.CartItem;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.repository.CartItemRepository;
import com.phat.food_delivering.repository.CartRepository;
import com.phat.food_delivering.request.CartItemRequest;
import com.phat.food_delivering.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    FoodService foodService;

    @Autowired
    UserService userService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    CartDTOMapper cartDTOMapper;

    @Autowired
    CartItemDTOMapper cartItemDTOMapper;

    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public CartItemDTO addCartItemIntoCart(CartItemRequest request, String token) {
        Food food = foodService.getFoodById(request.getFoodId());
        CartDTO cartDTO = findCartByUserId(token);
        Cart cart = findCartById(cartDTO.id());

        CartItem cartItem = new CartItem();
        cartItem.setFood(food);
        cartItem.setQuantity(request.getQuantity());
        cartItem.setIngredients(request.getIngredients());
        cartItem.setCart(cart);
        cartItem.setTotalPrice(request.getQuantity() * food.getPrice());

        CartItem savedCartItem = new CartItem();
        if (cart.getItems().isEmpty()) {
            savedCartItem = cartItemRepository.save(cartItem);
            cart.getItems().add(cartItem);
        } else {
            int count = 0;
            for (CartItem item : cart.getItems()) {
                if (item.getFood().getId() == food.getId()) {
                    int newQuantity = item.getQuantity() + request.getQuantity();
                    savedCartItem = updateCartItemQuantity(item.getId(), newQuantity);
                    count++;
                    break;
                }
            }

            if (count == 0) {
                savedCartItem = cartItemRepository.save(cartItem);
                cart.getItems().add(cartItem);
            }
        }

        Cart savedCart = cartRepository.save(cart);
        cart.setTotal(calculateTotal(savedCart));
        cartRepository.save(cart);
        return cartItemDTOMapper.apply(savedCartItem);
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = findCartItemById(cartItemId);
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * cartItem.getFood().getPrice());

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        Cart cart = cartItem.getCart();
        cart.setTotal(calculateTotal(cart));
        cartRepository.save(cart);

        return savedCartItem;
    }

    @Override
    public CartDTO removeItemFromCart(Long cartItemId, String token) {
        CartItem cartItem = findCartItemById(cartItemId);
        CartDTO cartDTO = findCartByUserId(token);
        Cart cart = findCartById(cartDTO.id());
        if (cart.getItems().contains(cartItem)) cart.getItems().remove(cartItem);
        cart.setTotal(calculateTotal(cart));
        return cartDTOMapper.apply(cartRepository.save(cart));
    }

    @Override
    public Long calculateTotal(Cart cart) {
        Long total = 0L;
        for (CartItem item : cart.getItems()) {
            total += item.getTotalPrice();
        }
        cart.setTotal(total);
        cartRepository.save(cart);
        return total;
    }

    @Override
    public Cart findCartById(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return unwrapCart(cart, cartId);
    }

    @Override
    public CartDTO findCartByUserId(String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            return CartDTO.noArgConstructor();
        }
        return cartDTOMapper.apply(cartRepository.findByCustomerId(user.getId()));
    }

    @Override
    public CartItem findCartItemById(Long id) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
        return unwrapCartItem(cartItemOptional, id);
    }

    @Override
    public CartDTO clearCartItem(String token) {
        CartDTO cartDTO = findCartByUserId(token);
        Cart cart = findCartById(cartDTO.id());
        cart.getItems().clear();
        cart.setTotal(calculateTotal(cart));
        return cartDTOMapper.apply(cartRepository.save(cart));
    }

    static CartItem unwrapCartItem(Optional<CartItem> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(CartItem.class, id);
        }
    }

    static Cart unwrapCart(Optional<Cart> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Cart.class, id);
        }
    }
}
