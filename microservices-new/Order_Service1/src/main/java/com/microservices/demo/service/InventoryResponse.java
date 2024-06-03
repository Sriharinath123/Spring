package com.microservices.demo.service;

public class InventoryResponse {
    private String skuCode;
    private boolean inStock;
    // Private constructor to prevent direct instantiation
    private InventoryResponse(String skuCode, boolean isInStock) {
        this.skuCode = skuCode;
        this.inStock = isInStock;
    }

    // Getters and setters
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setIsInStock(boolean isInStock) {
        this.inStock = isInStock;
    }

    // Builder pattern implementation
    public static class Builder {
        private String skuCode;
        private boolean isInStock;

        public Builder skuCode(String skuCode) {
            this.skuCode = skuCode;
            return this;
        }

        public Builder isInStock(boolean isInStock) {
            this.isInStock = isInStock;
            return this;
        }

        public InventoryResponse build() {
            return new InventoryResponse(skuCode, isInStock);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
