package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "currency_data")
public class CurrencyData {
    @Id
    @GeneratedValue
    @Column
    @JsonIgnore
    private Long dataId;
    @Column
    private String base;
    @Column
    private String date;
    @ElementCollection
    @CollectionTable(name = "currency_rates",
            joinColumns = {@JoinColumn(name = "dataId", referencedColumnName = "dataId")})
    @MapKeyColumn(name = "currency_name")
    @Column(name = "exchange_rate")
    private Map<String, Double> rates;
}
