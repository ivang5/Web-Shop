package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.SaleDTO;
import com.ivang.webshop.entity.Sale;
import com.ivang.webshop.repository.SaleRepository;
import com.ivang.webshop.repository.SellerRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class SaleService implements SaleServiceInterface {
    
    private final SaleRepository saleRepository;
    private final SellerRepository sellerRepository;

    @Override
    public Sale findOne(Long id) {
        log.info("Fetching sale {}", id);
        return saleRepository.getById(id);
    }

    @Override
    public List<SaleDTO> findAll() {
        log.info("Fetching all sales");
        List<SaleDTO> salesDTO = new ArrayList<SaleDTO>();

		for(Sale s : saleRepository.findAll()) {
			salesDTO.add(new SaleDTO(s));
		}
        
		return salesDTO;
    }

    @Override
    public List<SaleDTO> findBySeller(Long id) {
        log.info("Fetching all sales for user {}", id);
        List<SaleDTO> salesDTO = new ArrayList<SaleDTO>();

        for(Sale s : saleRepository.findAll()) {
            if (s.getId().equals(id)) {
			    salesDTO.add(new SaleDTO(s));
            }
		}
        
		return salesDTO;
    }

    @Override
    public List<SaleDTO> findByProduct(Long id) {
        log.info("Fetching all sales with product {}", id);
        List<SaleDTO> salesDTO = new ArrayList<SaleDTO>();

        for(Sale p : saleRepository.findByProduct(id)) {
            salesDTO.add(new SaleDTO(p));
        }

        return salesDTO;
    }

    @Override
    public void save(SaleDTO saleDTO) {
        log.info("Saving new sale {} to the database", saleDTO.getId());
        Sale sale = new Sale();
        populateSale(sale, saleDTO);
    }

    @Override
    public void update(SaleDTO saleDTO) {
        log.info("Updating sale {}", saleDTO.getId());
        Sale sale = saleRepository.getById(saleDTO.getId());

        if (sale != null) {
            populateSale(sale, saleDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing sale {}", id);
        saleRepository.deleteById(id);
    }

    private void populateSale(Sale sale, SaleDTO saleDTO) {
        sale.setPercentage(saleDTO.getPercentage());
        sale.setFromDate(saleDTO.getFromDate());
        sale.setToDate(saleDTO.getToDate());
        sale.setText(saleDTO.getText());
        sale.setSeller(sellerRepository.getById(saleDTO.getSeller().getId()));
        
        saleRepository.save(sale);
    }
}