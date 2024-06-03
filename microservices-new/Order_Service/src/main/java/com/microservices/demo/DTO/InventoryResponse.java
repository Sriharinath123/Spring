package com.microservices.demo.DTO;

public class InventoryResponse {
    private String skuCode;
    private boolean isInStock;

    // Private constructor to prevent direct instantiation
    private InventoryResponse(String skuCode, boolean isInStock) {
        this.skuCode = skuCode;
        this.isInStock = isInStock;
    }

    // Getters and setters
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setIsInStock(boolean isInStock) {
        this.isInStock = isInStock;
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
