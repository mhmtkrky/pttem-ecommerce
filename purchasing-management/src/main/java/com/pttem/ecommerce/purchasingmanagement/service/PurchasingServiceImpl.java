package com.pttem.ecommerce.purchasingmanagement.service;

import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingDetailEntity;
import com.pttem.ecommerce.purchasingmanagement.entity.PurchasingEntity;
import com.pttem.ecommerce.purchasingmanagement.model.AddProductToPurchasingModel;
import com.pttem.ecommerce.purchasingmanagement.repository.PurchasingDetailRepository;
import com.pttem.ecommerce.purchasingmanagement.repository.PurchasingRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static exception.ResourceNotFoundException.getNotFoundException;

@Service
public class PurchasingServiceImpl implements PurchasingService {

    private final PurchasingRepository repository;
    private final PurchasingDetailRepository detailRepository;
    private final ProductRestService productRestService;

    private final String RECORD_NOT_FOUND_MESSAGE = "Record not found";
    private final String PURCHASING_NOT_FOUND_MESSAGE = "Purchasing not found";
    private final String PRODUCT_IS_OUT_OF_STOCK_MESSAGE = "Product is out of stock";

    @Autowired
    public PurchasingServiceImpl(PurchasingRepository repository
            , PurchasingDetailRepository detailRepository,
                                 ProductRestService productRestService) {
        this.repository = repository;
        this.detailRepository = detailRepository;
        this.productRestService = productRestService;
    }


    @Override
    public PurchasingEntity addProduct(AddProductToPurchasingModel model) {

        BigDecimal unitPrice = productRestService.getUnitPriceForUUID(model.getProductUUID());

        repository.save(getOrCreatePurchasingEntity(model.getUserUUID())
                .incrementCountOrCreate(model.getProductUUID(), unitPrice));

        return getPurchasingEntityByUserUUID(model.getUserUUID());
    }


    @Override
    public PurchasingEntity removeProduct(AddProductToPurchasingModel model) {

        BigDecimal unitPrice = productRestService.getUnitPriceForUUID(model.getProductUUID());

        PurchasingDetailEntity purchasingDetailEntity = getPurchasingByUserAndProduct(model);

        return removeProductAndDeleteIfEmpty(model, unitPrice, purchasingDetailEntity);
    }


    @Override
    public PurchasingEntity complete(Long id) {
        return getPurchasingEntity(id)
                .map(purchasingEntity -> repository.save(completePurchasing(purchasingEntity)))
                .orElseThrow(getNotFoundException(PURCHASING_NOT_FOUND_MESSAGE));
    }


    @Override
    public List<PurchasingEntity> getList() {
        return repository.findAll();
    }

    @Override
    public List<PurchasingEntity> getUserUUID(Long uuid) {
        return repository.findByUserUUID(uuid);
    }

    private PurchasingDetailEntity getPurchasingByUserAndProduct(AddProductToPurchasingModel model) {
        return getPurchasingEntity(model.getUserUUID())
                .orElseThrow(getNotFoundException(PURCHASING_NOT_FOUND_MESSAGE))
                .getDetailList().stream().filter(x -> x.getProductUUID().equals(model.getProductUUID()))
                .findFirst()
                .orElseThrow(getNotFoundException(RECORD_NOT_FOUND_MESSAGE));
    }


    private PurchasingEntity removeProductAndDeleteIfEmpty(AddProductToPurchasingModel model, BigDecimal unitPrice, PurchasingDetailEntity purchasingDetailEntity) {
        if (purchasingDetailEntity.getCount() == 1) {
            return deletePurchasingDetail(purchasingDetailEntity.getPurchasing(), purchasingDetailEntity);
        } else {
            purchasingDetailEntity.decrementCount();
            repository.save(purchasingDetailEntity.getPurchasing().subtractTotalPrice(unitPrice));
            return getPurchasingEntityByUserUUID(model.getUserUUID());

        }
    }

    private PurchasingEntity completePurchasing(PurchasingEntity purchasingEntity) {
        checkStock(purchasingEntity);
        reduceStock(purchasingEntity);
        purchasingEntity.setCompleted(true);
        return purchasingEntity;
    }

    private void reduceStock(PurchasingEntity purchasingEntity) {
        purchasingEntity.getDetailList()
                .forEach(detail -> productRestService.reduceStock(detail.getProductUUID(), detail.getCount()));
    }

    private void checkStock(PurchasingEntity purchasingEntity) {
        if (purchasingEntity.getDetailList()
                .stream()
                .noneMatch(detail -> productRestService.controlStock(detail.getProductUUID(), detail.getCount()))) {
            throw new ResourceNotFoundException(PRODUCT_IS_OUT_OF_STOCK_MESSAGE);
        }
    }

    private PurchasingEntity getOrCreatePurchasingEntity(Long userUUID) {
        return getPurchasingEntity(userUUID)
                .orElseGet(() -> PurchasingEntity.builder().userUUID(userUUID).build());
    }

    private Optional<PurchasingEntity> getPurchasingEntity(Long userUUID) {
        return repository.findByUserUUID(userUUID)
                .stream()
                .filter(x -> !x.getCompleted())
                .findFirst();
    }

    private PurchasingEntity getPurchasingEntityByUserUUID(Long userUUID) {
        return repository.findByUserUUID(userUUID)
                .stream().findFirst().orElseThrow(getNotFoundException(RECORD_NOT_FOUND_MESSAGE));
    }


    private PurchasingEntity deletePurchasingDetail(PurchasingEntity purchasingEntity, PurchasingDetailEntity purchasingDetailEntity) {
        detailRepository.deleteById(purchasingDetailEntity.getId());
        if (purchasingEntity.getDetailList().size() == 1) {
            repository.deleteById(purchasingEntity.getId());
            purchasingEntity.getDetailList().remove(purchasingDetailEntity);
        }
        return null;
    }
}
