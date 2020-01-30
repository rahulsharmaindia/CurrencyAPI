package com.example.currency.repository;

import com.example.currency.model.CurrencyData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyDataRepo  extends JpaRepository<CurrencyData, Long> {
}
