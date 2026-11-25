package com.learn.spring.l08_externalized_config;

import java.time.Duration;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DurationFormat;
import org.springframework.stereotype.Component;

/**
 * TYPE CONVERSION beyond int and String[]: every value below arrives as a String from app.properties.
 * The "conversionService" bean (ConfigConfig) turns it into the declared type.
 */
@Component
public class ShippingSettings {
    private final Money fee;
    private final LocalDate saleEnds;
    private final Duration deliveryWindow;

    public ShippingSettings(
            // our own Converter<String, Money>
            @Value("${app.shipping.fee}") Money fee,
            // a built-in FORMATTER, chosen by the annotation: "31/12/2026" -> LocalDate
            @Value("${app.sale.ends}") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate saleEnds,
            // another built-in formatter: "48h" -> Duration
            @Value("${app.shipping.delivery-window}") @DurationFormat(style = DurationFormat.Style.SIMPLE) Duration deliveryWindow) {
        this.fee = fee;
        this.saleEnds = saleEnds;
        this.deliveryWindow = deliveryWindow;
    }

    public Money getFee() { return fee; }
    public LocalDate getSaleEnds() { return saleEnds; }
    public Duration getDeliveryWindow() { return deliveryWindow; }
}
