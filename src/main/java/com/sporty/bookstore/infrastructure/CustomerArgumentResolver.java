package com.sporty.bookstore.infrastructure;

import com.sporty.bookstore.domain.model.purchase.Customer;
import com.sporty.bookstore.usecase.identity.UserNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomerArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(final MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(Customer.class);
  }

  @Override
  public Object resolveArgument(final MethodParameter methodParameter,
                                final ModelAndViewContainer modelAndViewContainer,
                                final NativeWebRequest nativeWebRequest,
                                final WebDataBinderFactory webDataBinderFactory) {
    final String userId = nativeWebRequest.getHeader("userId");
    if(userId == null) {
      throw new UserNotFoundException();
    }
    return Customer.identifiedBy(userId);
  }
}
