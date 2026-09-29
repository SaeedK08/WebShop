package com.webshop.dto;

import java.util.List;

public record CartInfo(List<CartItemInfo> items, double totalCartPrice) {
}