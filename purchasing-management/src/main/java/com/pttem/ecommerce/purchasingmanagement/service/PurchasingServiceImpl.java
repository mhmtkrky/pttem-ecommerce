package com.pttem.ecommerce.purchasingmanagement.service;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingDetailEntity;
import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingEntity;
import com.pttem.ecommerce.purchasingmanagement.model.AddProductToPurchasingModel;
import com.pttem.ecommerce.purchasingmanagement.repository.PurchasingDetailRepository;
import com.pttem.ecommerce.purchasingmanagement.repository.PurchasingRepository;
import exception.ResourceNotFoundException;
import model.ProductStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PurchasingServiceImpl implements PurchasingService {

    private final PurchasingRepository repository;
    private final PurchasingDetailRepository detailRepository;
    private final RestTemplate restTemplate;
    private final String PRODUCT_BASE_URL = "http://product-management/rest/product/";

    @Autowired
    public PurchasingServiceImpl(PurchasingRepository repository
            , PurchasingDetailRepository detailRepository
            , RestTemplate restTemplate) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public PurchasingEntity addProduct(AddProductToPurchasingModel model) {

        ResponseEntity<BigDecimal> unitPrice = restTemplate
                .exchange(PRODUCT_BASE_URL + "unitPrice/"
                        + model.getProductUUID(), HttpMethod.GET, null, BigDecimal.class);

        PurchasingEntity newPurch =
                repository.findByUserUUID(model.getUserUUID())
                        .stream()
                        .filter(x -> !x.getCompleted())
                        .findFirst()
                        .orElseGet(() -> {
                            PurchasingEntity x = new PurchasingEntity();
                            x.setUserUUID(model.getUserUUID());
                            return x;
                        });

        PurchasingDetailEntity purchDetail = newPurch.getDetailList()
                .stream()
                .filter(x -> x.getProductUUID().equals(model.getProductUUID()))
                .findFirst()
                .map(x -> {
                    x.setCount(x.getCount() + 1);
                    return x;
                }).orElseGet(() -> {
                    PurchasingDetailEntity x = new PurchasingDetailEntity();
                    x.setCount(1);
                    x.setProductUUID(model.getProductUUID());
                    x.setPurchasing(newPurch);
                    x.setUnitPrice(unitPrice.getBody());
                    newPurch.addDetail(x);
                    return x;
                });
        newPurch.setTotalPrice(newPurch.getTotalPrice().add(unitPrice.getBody()));
        repository.save(newPurch);
        return repository.findByUserUUID(model.getUserUUID())
                .stream().findFirst().orElseThrow(()
                        -> new ResourceNotFoundException("Record Not Found"));
    }

    @Override
    public PurchasingEntity removeProduct(AddProductToPurchasingModel model) {

        ResponseEntity<BigDecimal> unitPrice = restTemplate
                .exchange(PRODUCT_BASE_URL + "unitPrice/"
                        + model.getProductUUID(), HttpMethod.GET, null, BigDecimal.class);

        PurchasingEntity newPurc =
                repository.findByUserUUID(model.getUserUUID())
                        .stream()
                        .filter(x -> !x.getCompleted())
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Purchasing not found"));


        Optional<PurchasingDetailEntity> purchasingDetailEntity = newPurc
                .getDetailList()
                .stream()
                .filter(x -> x.getProductUUID().equals(model.getProductUUID()))
                .findFirst();


        if (purchasingDetailEntity.isPresent()) {
            PurchasingDetailEntity pde = purchasingDetailEntity.get();
            if (pde.getCount() == 1) {
                detailRepository.deleteById(pde.getId());
                if (newPurc.getDetailList().size() == 1) {
                    repository.deleteById(newPurc.getId());
                    newPurc.getDetailList().remove(pde);
                }
                return null;
            } else {
                pde.setCount(pde.getCount() - 1);
                newPurc.setTotalPrice(newPurc.getTotalPrice().subtract(unitPrice.getBody()));
                repository.save(newPurc);
                return repository.findByUserUUID(model.getUserUUID())
                        .stream().findFirst().orElseThrow(()
                                -> new ResourceNotFoundException("Record Not Found"));

            }
        }
        throw new ResourceNotFoundException("Record Not Found");

    }

    @Override
    public PurchasingEntity complete(Long id) {
        return repository.findByUserUUID(id)
                .stream()
                .filter(x -> !x.getCompleted())
                .findFirst()
                .map(x -> {

                    if (x.getDetailList()
                            .stream()
                            .noneMatch(detail -> restTemplate
                                    .exchange(PRODUCT_BASE_URL + "unitInStock/"
                                            , HttpMethod.POST, new HttpEntity<>(new ProductStock(detail.getProductUUID(), detail.getCount()))
                                            , Boolean.class).getBody())) {
                        throw new ResourceNotFoundException("Product is out of stock");
                    } else {
                        x.getDetailList()
                                .forEach(detail -> restTemplate
                                        .exchange(PRODUCT_BASE_URL + "reduceStock/"
                                                , HttpMethod.POST, new HttpEntity<>(new ProductStock(detail.getProductUUID(), detail.getCount()))
                                                , Integer.class).getBody());
                    }
                    x.setCompleted(true);
                    return repository.save(x);
                }).orElseThrow(() -> new ResourceNotFoundException("Purchasing not found with id " + id));
    }

    @Override
    public List<PurchasingEntity> getList() {
        return repository.findAll();
    }

    @Override
    public List<PurchasingEntity> getUserUUID(Long uuid) {
        return repository.findByUserUUID(uuid);
    }
}
