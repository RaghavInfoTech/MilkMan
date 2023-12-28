package com.app.milkman.service;

import com.app.milkman.model.ProductRegRequest;
import com.app.milkman.model.ProductRegResponse;

public interface ProductService {

    ProductRegResponse registerProduct(ProductRegRequest productReg);
}
