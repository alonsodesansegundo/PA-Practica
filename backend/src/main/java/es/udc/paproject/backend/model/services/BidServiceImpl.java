package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.IncorrectBidUserException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.MaxPriceBidException;
import es.udc.paproject.backend.model.exceptions.OutOfBidTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class BidServiceImpl implements BidService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private BidDao bidDao;

    @Override
    public Bid bidding(Long userId, Long productId, BigDecimal maxPrice) throws InstanceNotFoundException,
            OutOfBidTimeException, MaxPriceBidException, IncorrectBidUserException {

        Optional<User> user = userDao.findById(userId);
        Optional<Product> product = productDao.findById(productId);

        if (!user.isPresent()) {
            throw new InstanceNotFoundException("project.entities.user", userId);
        }

        if (!product.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        LocalDateTime dateNow = LocalDateTime.now().withNano(0);
        LocalDateTime bidDate = product.get().getFinishingDate().withNano(0);

        //Comprobar que estamos pujando dentro de tiempo
        if (dateNow.isAfter(bidDate)) {
            throw new OutOfBidTimeException();
        }

        //Comprobamos que el vendedor del producto, no pueda pujar por el.
        if (product.get().getUser().getId().equals(user.get().getId())) {
            throw new IncorrectBidUserException();
        }

        Bid bid;
        //Primera puja, comprobamos si existe alguna puja anterior.
        if (product.get().getBidWinner() == null) {
            //Comprobamos que la puja siempre sea mayor que el precio actual del producto,
            // ó en caso de ser la primera puja que sea igual
            if (maxPrice.floatValue() < product.get().getActualPrice().floatValue()) {
                throw new MaxPriceBidException();
            }

            //Como no hay ninguna puja que va ganando, asumimos que somos la primera puja.
            bid = new Bid(dateNow, maxPrice, product.get(), user.get());
            bidDao.save(bid);

            //Al ser la primera puja, de momento van a ser la puja ganadora
            product.get().setBidWinner(bid);
            productDao.save(product.get());

        } else {
            //Comprobamos que la puja siempre sea mayor que el precio actual del producto.
            if (maxPrice.floatValue() <= product.get().getActualPrice().floatValue()) {
                throw new MaxPriceBidException();
            }

            bid = new Bid(dateNow, maxPrice, product.get(), user.get());
            bidDao.save(bid);

            BigDecimal bidWinner = product.get().getBidWinner().getMaxPrice();
            BigDecimal bidNow = bid.getMaxPrice();

            BigDecimal incremento;
            //Comprobamos si la puja actual pasa a ser la ganadora + algoritmo del precio actual
            if (bidNow.floatValue() > bidWinner.floatValue()){
                //Calculamos el incremento
                incremento = bidWinner.add(BigDecimal.valueOf(0.5));

                //Algoritmo para calcular el precio actual del producto
                if (incremento.floatValue() > bidNow.floatValue()) {
                    product.get().setActualPrice(bidNow);
                    productDao.save(product.get());

                } else {
                    product.get().setActualPrice(incremento);
                    productDao.save(product.get());
                }

                //Modificamos la puja ganadora.
                product.get().setBidWinner(bid);
                productDao.save(product.get());

            } else if (bidWinner.floatValue() >= bidNow.floatValue()) {
                //Calculamos el incremento
                incremento = bidNow.add(BigDecimal.valueOf(0.5));

                //Algoritmo para calcular el precio actual del producto
                if (incremento.floatValue() > bidWinner.floatValue()) {
                    product.get().setActualPrice(bidWinner);
                    productDao.save(product.get());

                } else {
                    product.get().setActualPrice(incremento);
                    productDao.save(product.get());
                }
            }
        }

        return bid;
    }

    @Override
    public Block<Bid> findBidsByUserId(Long id, int page, int size) throws InstanceNotFoundException {

        Optional<User> user = userDao.findById(id);

        if (!user.isPresent()) {
            throw new InstanceNotFoundException("project.entities.user", id);
        }

        Slice<Bid> bid = bidDao.findBidsByUserBidIdOrderByDateBidDesc(user.get().getId(), PageRequest.of(page, size));

        //Slice<Bid> bid = bidDao.findBidByUserId(user.get().getId(),page,size);
        return new Block<>(bid.getContent(), bid.hasNext());

    }
}