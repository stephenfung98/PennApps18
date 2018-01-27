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

@DynamoDBTable(tableName = "fairride-mobilehub-1500310723-lyftPrices")

public class LyftPricesDO {
    private String _state;
    private Double _baseLine;
    private Double _baseLux;
    private Double _baseLuxSUV;
    private Double _baseLyft;
    private Double _basePlus;
    private Double _mileLine;
    private Double _mileLux;
    private Double _mileLuxSUV;
    private Double _mileLyft;
    private Double _milePlus;
    private Double _minuteLine;
    private Double _minuteLux;
    private Double _minuteLuxSUV;
    private Double _minuteLyft;
    private Double _minutePlus;

    @DynamoDBHashKey(attributeName = "state")
    @DynamoDBAttribute(attributeName = "state")
    public String getState() {
        return _state;
    }

    public void setState(final String _state) {
        this._state = _state;
    }
    @DynamoDBAttribute(attributeName = "baseLine")
    public Double getBaseLine() {
        return _baseLine;
    }

    public void setBaseLine(final Double _baseLine) {
        this._baseLine = _baseLine;
    }
    @DynamoDBAttribute(attributeName = "baseLux")
    public Double getBaseLux() {
        return _baseLux;
    }

    public void setBaseLux(final Double _baseLux) {
        this._baseLux = _baseLux;
    }
    @DynamoDBAttribute(attributeName = "baseLuxSUV")
    public Double getBaseLuxSUV() {
        return _baseLuxSUV;
    }

    public void setBaseLuxSUV(final Double _baseLuxSUV) {
        this._baseLuxSUV = _baseLuxSUV;
    }
    @DynamoDBAttribute(attributeName = "baseLyft")
    public Double getBaseLyft() {
        return _baseLyft;
    }

    public void setBaseLyft(final Double _baseLyft) {
        this._baseLyft = _baseLyft;
    }
    @DynamoDBAttribute(attributeName = "basePlus")
    public Double getBasePlus() {
        return _basePlus;
    }

    public void setBasePlus(final Double _basePlus) {
        this._basePlus = _basePlus;
    }
    @DynamoDBAttribute(attributeName = "mileLine")
    public Double getMileLine() {
        return _mileLine;
    }

    public void setMileLine(final Double _mileLine) {
        this._mileLine = _mileLine;
    }
    @DynamoDBAttribute(attributeName = "mileLux")
    public Double getMileLux() {
        return _mileLux;
    }

    public void setMileLux(final Double _mileLux) {
        this._mileLux = _mileLux;
    }
    @DynamoDBAttribute(attributeName = "mileLuxSUV")
    public Double getMileLuxSUV() {
        return _mileLuxSUV;
    }

    public void setMileLuxSUV(final Double _mileLuxSUV) {
        this._mileLuxSUV = _mileLuxSUV;
    }
    @DynamoDBAttribute(attributeName = "mileLyft")
    public Double getMileLyft() {
        return _mileLyft;
    }

    public void setMileLyft(final Double _mileLyft) {
        this._mileLyft = _mileLyft;
    }
    @DynamoDBAttribute(attributeName = "milePlus")
    public Double getMilePlus() {
        return _milePlus;
    }

    public void setMilePlus(final Double _milePlus) {
        this._milePlus = _milePlus;
    }
    @DynamoDBAttribute(attributeName = "minuteLine")
    public Double getMinuteLine() {
        return _minuteLine;
    }

    public void setMinuteLine(final Double _minuteLine) {
        this._minuteLine = _minuteLine;
    }
    @DynamoDBAttribute(attributeName = "minuteLux")
    public Double getMinuteLux() {
        return _minuteLux;
    }

    public void setMinuteLux(final Double _minuteLux) {
        this._minuteLux = _minuteLux;
    }
    @DynamoDBAttribute(attributeName = "minuteLuxSUV")
    public Double getMinuteLuxSUV() {
        return _minuteLuxSUV;
    }

    public void setMinuteLuxSUV(final Double _minuteLuxSUV) {
        this._minuteLuxSUV = _minuteLuxSUV;
    }
    @DynamoDBAttribute(attributeName = "minuteLyft")
    public Double getMinuteLyft() {
        return _minuteLyft;
    }

    public void setMinuteLyft(final Double _minuteLyft) {
        this._minuteLyft = _minuteLyft;
    }
    @DynamoDBAttribute(attributeName = "minutePlus")
    public Double getMinutePlus() {
        return _minutePlus;
    }

    public void setMinutePlus(final Double _minutePlus) {
        this._minutePlus = _minutePlus;
    }

}
