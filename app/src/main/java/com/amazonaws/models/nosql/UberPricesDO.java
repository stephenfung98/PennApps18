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

@DynamoDBTable(tableName = "fairride-mobilehub-1500310723-uberPrices")

public class UberPricesDO {
    private String _state;
    private Double _baseBlack;
    private Double _basePool;
    private Double _baseSUV;
    private Double _baseX;
    private Double _baseXL;
    private Double _mileBlack;
    private Double _milePool;
    private Double _mileSUV;
    private Double _mileX;
    private Double _mileXL;
    private Double _minuteBlack;
    private Double _minutePool;
    private Double _minuteSUV;
    private Double _minuteX;
    private Double _minuteXL;

    @DynamoDBHashKey(attributeName = "state")
    @DynamoDBAttribute(attributeName = "state")
    public String getState() {
        return _state;
    }

    public void setState(final String _state) {
        this._state = _state;
    }
    @DynamoDBAttribute(attributeName = "baseBlack")
    public Double getBaseBlack() {
        return _baseBlack;
    }

    public void setBaseBlack(final Double _baseBlack) {
        this._baseBlack = _baseBlack;
    }
    @DynamoDBAttribute(attributeName = "basePool")
    public Double getBasePool() {
        return _basePool;
    }

    public void setBasePool(final Double _basePool) {
        this._basePool = _basePool;
    }
    @DynamoDBAttribute(attributeName = "baseSUV")
    public Double getBaseSUV() {
        return _baseSUV;
    }

    public void setBaseSUV(final Double _baseSUV) {
        this._baseSUV = _baseSUV;
    }
    @DynamoDBAttribute(attributeName = "baseX")
    public Double getBaseX() {
        return _baseX;
    }

    public void setBaseX(final Double _baseX) {
        this._baseX = _baseX;
    }
    @DynamoDBAttribute(attributeName = "baseXL")
    public Double getBaseXL() {
        return _baseXL;
    }

    public void setBaseXL(final Double _baseXL) {
        this._baseXL = _baseXL;
    }
    @DynamoDBAttribute(attributeName = "mileBlack")
    public Double getMileBlack() {
        return _mileBlack;
    }

    public void setMileBlack(final Double _mileBlack) {
        this._mileBlack = _mileBlack;
    }
    @DynamoDBAttribute(attributeName = "milePool")
    public Double getMilePool() {
        return _milePool;
    }

    public void setMilePool(final Double _milePool) {
        this._milePool = _milePool;
    }
    @DynamoDBAttribute(attributeName = "mileSUV")
    public Double getMileSUV() {
        return _mileSUV;
    }

    public void setMileSUV(final Double _mileSUV) {
        this._mileSUV = _mileSUV;
    }
    @DynamoDBAttribute(attributeName = "mileX")
    public Double getMileX() {
        return _mileX;
    }

    public void setMileX(final Double _mileX) {
        this._mileX = _mileX;
    }
    @DynamoDBAttribute(attributeName = "mileXL")
    public Double getMileXL() {
        return _mileXL;
    }

    public void setMileXL(final Double _mileXL) {
        this._mileXL = _mileXL;
    }
    @DynamoDBAttribute(attributeName = "minuteBlack")
    public Double getMinuteBlack() {
        return _minuteBlack;
    }

    public void setMinuteBlack(final Double _minuteBlack) {
        this._minuteBlack = _minuteBlack;
    }
    @DynamoDBAttribute(attributeName = "minutePool")
    public Double getMinutePool() {
        return _minutePool;
    }

    public void setMinutePool(final Double _minutePool) {
        this._minutePool = _minutePool;
    }
    @DynamoDBAttribute(attributeName = "minuteSUV")
    public Double getMinuteSUV() {
        return _minuteSUV;
    }

    public void setMinuteSUV(final Double _minuteSUV) {
        this._minuteSUV = _minuteSUV;
    }
    @DynamoDBAttribute(attributeName = "minuteX")
    public Double getMinuteX() {
        return _minuteX;
    }

    public void setMinuteX(final Double _minuteX) {
        this._minuteX = _minuteX;
    }
    @DynamoDBAttribute(attributeName = "minuteXL")
    public Double getMinuteXL() {
        return _minuteXL;
    }

    public void setMinuteXL(final Double _minuteXL) {
        this._minuteXL = _minuteXL;
    }

}
