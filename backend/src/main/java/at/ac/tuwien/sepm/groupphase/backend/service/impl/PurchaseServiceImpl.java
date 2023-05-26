package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseCreationDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.PurchaseDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.TicketDto;
import at.ac.tuwien.sepm.groupphase.backend.entity.Purchase;
import at.ac.tuwien.sepm.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepm.groupphase.backend.repository.PurchaseRepository;
import at.ac.tuwien.sepm.groupphase.backend.service.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private PurchaseRepository repository;
    private HallPlanSeatService seatService;
    private EventService eventService;
    private TicketService ticketService;
    private CartService cartService;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository repository, HallPlanSeatService seatService, EventService eventService, TicketService ticketService, CartService cartService) {
        this.repository = repository;
        this.seatService = seatService;
        this.eventService = eventService;
        this.cartService = cartService;
        this.ticketService = ticketService;
    }

    @Override
    public PurchaseDto getPurchaseByPurchaseNr(Long purchaseNr, Long userId){
        Purchase purchase = repository.findPurchasesByPurchaseNr(purchaseNr);
        //TODO: check if purchase belong to user cart

        List<Ticket> ticketList = purchase.getTicketList();
        List<TicketDto> ticketDtoList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ticketDtoList.add(ticketService.ticketDtoFromTicket(ticket));
        }
        return new PurchaseDto(ticketDtoList, purchase);
    }

    @Override
    @Transactional
    public void deletePurchase(Long purchaseNr, Long userId){
        Purchase purchase = repository.findPurchasesByPurchaseNr(purchaseNr);

        if (purchase == null){
            return; //Todo: No Content
        }

        if (!purchase.getUserId().equals(userId)){
            return; //TODO: No Content (to not have side channels)
        }

        for (Ticket ticket:purchase.getTicketList()) {
            seatService.freePurchasedSeat(ticket.getSeatId());
        }
        repository.deletePurchaseByPurchaseNr(purchaseNr);
    }

    @Override
    public List<PurchaseDto> getPurchasesOfUser(Long userId) {
        List<Purchase> purchaseList = repository.findPurchasesByUserIdOrderByPurchaseDate(userId);
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();

        //TODO: check if purchase belong to user cart

        for (Purchase purchase : purchaseList) {
            if (purchase.getTicketList().isEmpty()) {
                break; //TODO: actually this shouldnt happen (every purchase should have tickets)
            }

            List<Ticket> ticketList = purchase.getTicketList();
            List<TicketDto> ticketDtoList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                ticketDtoList.add(ticketService.ticketDtoFromTicket(ticket));
            }

            purchaseDtoList.add(new PurchaseDto(ticketDtoList, purchase));
        }
        return purchaseDtoList;
    }

    @Override
    public void purchaseCartOfUser(Long userId, PurchaseCreationDto purchaseCreationDto) {
        //TODO: verify request
        //TODO: check if items belong to user cart

        List<Ticket> ticketList = new ArrayList<>();

        if (purchaseCreationDto.getSeats() == null) {
            return;
            //TODO: some kind of error
        }
        for (SeatDto seatDto : purchaseCreationDto.getSeats()) {
            if (seatService.purchaseReservedSeat(seatDto.getId())) {
                ticketList.add(new Ticket(seatDto.getId()));
                cartService.deleteItem(seatDto.getId(), userId);
            }
        }

        Purchase purchase = new Purchase();
        purchase.setDate(LocalDate.now());
        purchase.setUserId(userId);
        //TODO: Get UserInfo
        //TODO: Check if custom address
        //TODO: create Purchase Object
        purchase.setBillAddress("not implemented (address)");
        purchase.setBillAreaCode(1337L);
        purchase.setBillCityName("not implemented (city)");
        purchase.setTicketList(ticketList);
        repository.save(purchase);
    }

}
