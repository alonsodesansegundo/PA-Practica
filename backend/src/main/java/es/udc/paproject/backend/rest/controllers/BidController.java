package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.exceptions.IncorrectBidUserException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.MaxPriceBidException;
import es.udc.paproject.backend.model.exceptions.OutOfBidTimeException;
import es.udc.paproject.backend.model.services.BidService;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.BidDto;
import es.udc.paproject.backend.rest.dtos.BidParamsDto;
import es.udc.paproject.backend.rest.dtos.BidSummaryDto;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidDto;
import static es.udc.paproject.backend.rest.dtos.BidConversor.toBidSummaryDtos;

@RestController
@RequestMapping("/bid")
public class BidController {

    private final static String OUT_OF_BID_TIME_EXCEPTION = "project.exceptions.OutOfBidTimeException";
    private final static String MAX_PRICE_BID_EXCEPTION = "project.exceptions.MaxPriceBidException";
    private final static String INCORRECT_BID_USER_EXCEPTION = "project.exceptions.IncorrectBidUserException";

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private BidService bidService;

    @ExceptionHandler(OutOfBidTimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleOutOfBidTimeException(OutOfBidTimeException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(OUT_OF_BID_TIME_EXCEPTION, null,
                OUT_OF_BID_TIME_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }


    @ExceptionHandler(MaxPriceBidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxPriceBidException(MaxPriceBidException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_PRICE_BID_EXCEPTION, null,
                MAX_PRICE_BID_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(IncorrectBidUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleIncorrectBidUserException(IncorrectBidUserException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INCORRECT_BID_USER_EXCEPTION, null,
                INCORRECT_BID_USER_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("")
    public BidDto bidding(@RequestAttribute Long userId, @Validated @RequestBody BidParamsDto params) throws OutOfBidTimeException,
            InstanceNotFoundException, MaxPriceBidException, IncorrectBidUserException {

        return toBidDto(bidService.bidding(userId, params.getProductId(), params.getPrice()));
    }

    @GetMapping("/user")
    public BlockDto<BidSummaryDto> findBidsByUserId(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException {

        Block<Bid> bidBlock = bidService.findBidsByUserId(userId, page, 2);
        return new BlockDto<>(toBidSummaryDtos(bidBlock.getItems()), bidBlock.getExistMoreItems());
    }

}