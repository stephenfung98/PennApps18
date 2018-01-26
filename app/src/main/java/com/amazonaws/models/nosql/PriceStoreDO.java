package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "fairride-mobilehub-1500310723-priceStore")

public class PriceStoreDO {
    private String _city;
    private Double _a;
    private Double _b;

    @DynamoDBHashKey(attributeName = "City")
    @DynamoDBAttribute(attributeName = "City")
    public String getCity() {
        return _city;
    }

    public void setCity(final String _city) {
        this._city = _city;
    }
    @DynamoDBAttribute(attributeName = "A")
    public Double getA() {
        return _a;
    }

    public void setA(final Double _a) {
        this._a = _a;
    }
    @DynamoDBAttribute(attributeName = "B")
    public Double getB() {
        return _b;
    }

    public void setB(final Double _b) {
        this._b = _b;
    }

}
