package com.sk.tdd.service;

import com.sk.tdd.domain.CheckoutResult;
import com.sk.tdd.domain.Product;
import com.sk.tdd.domain.ShoppingCart;
import com.sk.tdd.exception.CheckoutIntegrationException;
import com.sk.tdd.service.integration.CheckoutResource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutIntegrationServiceTest {

  private CheckoutIntegrationService checkoutIntegrationService;

  @Mock
  private CheckoutResource checkoutResource;

  @Before
  public void setUp() throws Exception {
    checkoutIntegrationService = new CheckoutIntegrationService(checkoutResource);
  }

  @Test
  public void testIntegrationSuccessful() throws Exception {
    final ShoppingCart cart = new ShoppingCart("cartID");
    final Product product1 = new Product("productId1", "other", 33);
    final Product product2 = new Product("productId2", "other", 100);
    cart.getProductList().add(product1);
    cart.getProductList().add(product2);

    given(checkoutResource.checkout(cart.getId(), 133)).willReturn(new CheckoutResult(true));

    checkoutIntegrationService.checkoutCart(cart);

    verify(checkoutResource, times(1)).checkout(cart.getId(), 133);
  }

  @Test
  public void testIntegrationFailsOnNegativeCartValue() throws Exception {
    //TODO
//    assertThat(false).isTrue();
  }

  @Test(expected = CheckoutIntegrationException.class)
  @Ignore
  public void testIntegrationFailsOn3rdPartyServiceException() throws Exception {
    doThrow(new CheckoutIntegrationException()).when(checkoutResource).checkout(anyString(), anyInt());
    checkoutIntegrationService.checkoutCart(new ShoppingCart());
  }
}
