package com.sk.tdd.controller;

import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.exception.ProductNotFoundException;
import com.sk.tdd.exception.ShopCartNotFoundException;
import com.sk.tdd.service.ShoppingCartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShoppingCartController.class)
@RunWith(SpringRunner.class)
public class ShoppingCartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ShoppingCartService shoppingCartService;

  @Test
  public void getShoppingCart_notFoundNoId() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/shoppingCart"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getShoppingCart_notFoundBadId() throws Exception {
    given(shoppingCartService.getShoppingCart(anyString())).willThrow(new ShopCartNotFoundException());
    mockMvc.perform(MockMvcRequestBuilders.get("/shoppingCart/cart1"))
        .andExpect(status().isNotFound());
  }


  @Test
  public void getShoppingCart_getById() throws Exception {
    final String id = "ID";
    given(shoppingCartService.getShoppingCart(id)).willReturn(new ShoppingCart(id));
    mockMvc.perform(MockMvcRequestBuilders.get("/shoppingCart/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id").value(id));
  }

  @Test
  public void testAddProductToShoppingCart() throws Exception {
    final String cartId = "cartId";
    final String productId = "productId";
    mockMvc.perform(
        MockMvcRequestBuilders.put("/shoppingCart/" + cartId)
            .param("productId", productId)
    ).andExpect(status().isOk());
    verify(shoppingCartService, times(1)).addProductToCart(cartId, productId);
  }

  @Test
  public void testAddProductToShoppingCart_throwsCartNotFound() throws Exception {
    final String cartId = "cartId";
    final String productId = "productId";
    doThrow(ShopCartNotFoundException.class).when(shoppingCartService).addProductToCart(cartId, productId);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/shoppingCart/" + cartId)
            .param("productId", productId)
    ).andExpect(status().isNotFound());
    verify(shoppingCartService, times(1)).addProductToCart(cartId, productId);
  }

  @Test
  public void testAddProductToShoppingCart_throwsProductNotFound() throws Exception {
    final String cartId = "cartId";
    final String productId = "productId";
    doThrow(ProductNotFoundException.class).when(shoppingCartService).addProductToCart(cartId, productId);
    mockMvc.perform(
        MockMvcRequestBuilders.put("/shoppingCart/" + cartId)
            .param("productId", productId)
    )
        .andExpect(status().isNotFound());
    verify(shoppingCartService, times(1)).addProductToCart(cartId, productId);
  }

  @Test
  public void testCartCheckoutCompletesSuccessfully() throws Exception {
    mockMvc.perform(
        MockMvcRequestBuilders.post("/shoppingCart/cartId/checkout")
    ).andExpect(status().isOk());
    verify(shoppingCartService, times(1)).checkout("cartId");
  }

  @Test
  public void testCartCheckoutFailsOnCheckoutServiceFailure() throws Exception {
    //TODO
//    assertThat(false).isTrue();
  }

  @Test
  public void testCartValueCalculation() throws Exception {
//    assertThat(false).isTrue();
  }

  @Test
  public void testRemoveProductFromShoppingCart() throws Exception {
    final String cartId = "cartId";
    final String productId = "productId";
    mockMvc.perform(
        MockMvcRequestBuilders.delete("/shoppingCart/" + cartId + "/productId/" + productId)
    ).andExpect(status().isOk());
    verify(shoppingCartService, times(1)).removeProductFromCart(cartId, productId);
  }

  @Test
  public void testRemovalFailsOnProductNotInTheCart() throws Exception {
    //TODO
//    assertThat(false).isTrue();
  }

  @Test
  public void testRemovalFailsOnCartNotExist() throws Exception {
    //TODO
//    assertThat(false).isTrue();
  }

}
